/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ups.negocio;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.management.remote.NotificationResult;
import javax.persistence.NoResultException;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import ec.edu.ups.dao.ClienteDAO;
import ec.edu.ups.dao.CuentaDeAhorroDAO;
import ec.edu.ups.dao.EmpleadoDAO;
import ec.edu.ups.dao.SesionClienteDAO;
import ec.edu.ups.dao.SolicitudPolizaDAO;
import ec.edu.ups.dao.TransaccionDAO;
import ec.edu.ups.dao.TransferenciaDAO;
import ec.edu.ups.modelo.Cliente;
import ec.edu.ups.modelo.CuentaDeAhorro;
import ec.edu.ups.modelo.Empleado;
import ec.edu.ups.modelo.SesionCliente;
import ec.edu.ups.modelo.SolicitudPoliza;
import ec.edu.ups.modelo.Transaccion;
import ec.edu.ups.modelo.Transferencia;
import java.util.List;
import java.util.Properties;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Starman
 */
@Stateless
public class GestionUsuarios implements GestionUsuarioLocal {

    @Inject
    private ClienteDAO clienteDAO;
    @Inject
    private CuentaDeAhorroDAO cuentaDeAhorroDAO;
    @Inject
    private SesionClienteDAO sesionClienteDAO;
    @Inject
    private TransaccionDAO transaccionDAO;
    @Inject
    private EmpleadoDAO empleadoDAO;
    @Inject
    private TransferenciaDAO transferenciaLocalDAO;
    @Inject
    private SolicitudPolizaDAO solicitudPolizaDAO;

    public String generarNumeroDeCuenta() {
        int numeroInicio = 4040;
        List<CuentaDeAhorro> listaCuentas = listaCuentaDeAhorros();
        int numero = listaCuentas.size() + 1;
        String resultado = String.format("%08d", numero);
        String resultadoFinal = String.valueOf(numeroInicio) + resultado;
        return resultadoFinal;
    }

    public String getUsuario(String cedula, String nombre, String apellido) {
        System.out.println(cedula);
        System.out.println(nombre);
        System.out.println(apellido);
        String ud = cedula.substring(cedula.length() - 1);
        String pln = nombre.substring(0, 1);
        int it = 0;
        for (int i = 0; i < apellido.length(); i++) {
            if (apellido.charAt(i) == 32) {
                it = i;
            }
        }
        String a = "";
        if (it == 0) {
            a = apellido.substring(0, apellido.length());
        } else {
            a = apellido.substring(0, it);
        }
        return pln.toLowerCase() + a.toLowerCase() + ud;
    }

    public String getContraseña() {
        String simbolos = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefjhijklmnopqrstuvwxyz0123456789!#$%&()*+,-./:;<=>?@_";

        int tam = simbolos.length() - 1;
        System.out.println(tam);
        String clave = "";
        for (int i = 0; i < 10; i++) {
            int v = (int) Math.floor(Math.random() * tam + 1);
            clave += simbolos.charAt(v);
        }

        return clave;
    }

    public void enviarCorreo(String destinatario, String asunto, String cuerpo) {
        Properties propiedad = new Properties();
        propiedad.setProperty("mail.smtp.host", "smtp.gmail.com");
        propiedad.setProperty("mail.smtp.starttls.enable", "true");
        propiedad.setProperty("mail.smtp.port", "587");

        Session sesion = Session.getDefaultInstance(propiedad);
        String correoEnvia = "starbankoficial@gmail.com";
        String contrasena = "Covid1991X";

        MimeMessage mail = new MimeMessage(sesion);
        try {
            mail.setFrom("STARBANK <" + correoEnvia + ">");
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
            mail.setSubject(asunto);
            mail.setText(cuerpo);

            Transport transportar = sesion.getTransport("smtp");
            transportar.connect(correoEnvia, contrasena);
            transportar.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));
        } catch (AddressException ex) {
            System.out.println(ex.getMessage());
        } catch (MessagingException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public String fecha() {
        Date date = new Date();
        DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return hourdateFormat.format(date);
    }

    public String obtenerFecha(Date fecha) {
        DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return hourdateFormat.format(fecha);
    }

    public void guardarCliente(Cliente c) {
        clienteDAO.insert(c);
    }

    public Cliente buscarCliente(String cedulaCliente) {
        Cliente cliente = clienteDAO.read(cedulaCliente);
        return cliente;
    }

    public Cliente buscarClienteUsuarioContraseña(String usuario, String contraseña) {
        try {
            return clienteDAO.obtenerClienteUsuarioContraseña(usuario, contraseña);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public void eliminarCliente(String cedulaCliente) {
        clienteDAO.delete(cedulaCliente);
    }

    public void actualizarCliente(Cliente cliente) {
        clienteDAO.update(cliente);
    }

    public List<Cliente> listaClientes() {
        List<Cliente> clientes = clienteDAO.getClientes();
        return clientes;
    }

    public void guardarCuentaDeAhorros(CuentaDeAhorro c) {
        Cliente cliente = clienteDAO.read(c.getCliente().getCedula());
        if (cliente == null) {
            Cliente cli = c.getCliente();
            String usuario = getUsuario(cli.getCedula(), cli.getNombre(), cli.getApellido());
            String contraseña = getContraseña();
            cli.setUsuario(usuario);
            cli.setClave(contraseña);
            c.setCliente(cli);
            String destinatario = cli.getCorreo(); // A quien le quieres escribir.

            String asunto = "CREACION DE USUARIO";
            String cuerpo = "STARBANK                                               SISTEMA TRANSACCIONAL\n"
                    + "------------------------------------------------------------------------------\n"
                    + "              Estimado(a): " + cli.getNombre().toUpperCase() + " "
                    + cli.getApellido().toUpperCase() + "\n"
                    + "------------------------------------------------------------------------------\n"
                    + "STARBANK informa que su cuenta ha sido creada correctamente                   \n"
                    + "                                                                              \n"
                    + "                       Usuario: " + usuario + "                               \n"
                    + "                       Password: " + contraseña + "                           \n"
                    + "                       Fecha: " + fecha() + "                                 \n"
                    + "                                                                              \n"
                    + "------------------------------------------------------------------------------\n";
            CompletableFuture.runAsync(() -> {
                try {
                    enviarCorreo(destinatario, asunto, cuerpo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            cuentaDeAhorroDAO.insert(c);
        }

    }

    public CuentaDeAhorro buscarCuentaDeAhorro(String numeroCuentaDeAhorro) {
        CuentaDeAhorro cuentaDeAhorro = cuentaDeAhorroDAO.read(numeroCuentaDeAhorro);
        return cuentaDeAhorro;
    }

    public CuentaDeAhorro buscarCuentaDeAhorroCliente(String cedulaCliente) {
        CuentaDeAhorro cuentaDeAhorro = cuentaDeAhorroDAO.getCuentaCedulaCliente(cedulaCliente);
        return cuentaDeAhorro;

    }

    public void eliminarCuentaDeAhorro(String numeroCuentaDeAhorro) {
        cuentaDeAhorroDAO.delete(numeroCuentaDeAhorro);
    }

    public void actualizarCuentaDeAhorro(CuentaDeAhorro cuentaDeAhorro) {
        cuentaDeAhorroDAO.update(cuentaDeAhorro);
    }

    public List<CuentaDeAhorro> listaCuentaDeAhorros() {
        List<CuentaDeAhorro> clientes = cuentaDeAhorroDAO.getCuentaDeAhorros();
        return clientes;
    }

    public void guardarSesion(SesionCliente sesionCliente) {
        Cliente cli = sesionCliente.getCliente();
        String destinatario = cli.getCorreo();
        if (sesionCliente.getEstado().equalsIgnoreCase("Incorrecto")) {
            // A quien le quieres escribir.

            String asunto = "INICIO DE SESION FALLIDA";
            String cuerpo = "STARBANK SISTEMA TRANSACCIONAL\n"
                    + "------------------------------------------------------------------------------\n"
                    + "              Estimado(a): " + cli.getNombre().toUpperCase() + " "
                    + cli.getApellido().toUpperCase() + "\n"
                    + "------------------------------------------------------------------------------\n"
                    + "STARBANK le informa que el acceso a su cuenta ha sido fallida.    \n"
                    + "                       Fecha: " + obtenerFecha(sesionCliente.getFechaSesion())
                    + "                                     \n"
                    + "                                                                              \n"
                    + "------------------------------------------------------------------------------\n";
            CompletableFuture.runAsync(() -> {
                try {
                    enviarCorreo(destinatario, asunto, cuerpo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        } else {
            // A quien le quieres escribir.

            String asunto = "INICIO DE SESION CORRECTA";
            String cuerpo = "STARBANK SISTEMA TRANSACCIONAL\n"
                    + "------------------------------------------------------------------------------\n"
                    + "              Estimado(a): " + cli.getNombre().toUpperCase() + " "
                    + cli.getApellido().toUpperCase() + "\n"
                    + "------------------------------------------------------------------------------\n"
                    + "STARBANK le informa que el acceso a su cuenta ha sido correcta.    \n"
                    + "                       Fecha: " + obtenerFecha(sesionCliente.getFechaSesion())
                    + "                                     \n"
                    + "                                                                              \n"
                    + "------------------------------------------------------------------------------\n";

            CompletableFuture.runAsync(() -> {
                try {
                    enviarCorreo(destinatario, asunto, cuerpo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        sesionClienteDAO.insert(sesionCliente);

    }

    public SesionCliente buscarSesionCliente(int codigoSesionCliente) {
        return sesionClienteDAO.read(codigoSesionCliente);
    }

    public List<SesionCliente> obtenerSesionesCliente(String cedulaCliente) {
        try {
            return sesionClienteDAO.obtenerSesionCliente(cedulaCliente);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean validadorDeCedula(String cedula) throws Exception {
        System.out.println(cedula + "    En Metodo ");
        boolean cedulaCorrecta = false;
        try {
            if (cedula.length() == 10) // ConstantesApp.LongitudCedula
            {
                int tercerDigito = Integer.parseInt(cedula.substring(2, 3));
                if (tercerDigito < 6) {
                    int[] coefValCedula = {2, 1, 2, 1, 2, 1, 2, 1, 2};
                    int verificador = Integer.parseInt(cedula.substring(9, 10));
                    int suma = 0;
                    int digito = 0;
                    for (int i = 0; i < (cedula.length() - 1); i++) {
                        digito = Integer.parseInt(cedula.substring(i, i + 1)) * coefValCedula[i];
                        suma += ((digito % 10) + (digito / 10));
                    }
                    if ((suma % 10 == 0) && (suma % 10 == verificador)) {
                        cedulaCorrecta = true;
                    } else if ((10 - (suma % 10)) == verificador) {
                        cedulaCorrecta = true;
                    } else {
                        cedulaCorrecta = false;
                    }
                } else {
                    cedulaCorrecta = false;
                }
            } else {
                cedulaCorrecta = false;
            }
        } catch (NumberFormatException nfe) {
            cedulaCorrecta = false;
        } catch (Exception err) {
            cedulaCorrecta = false;
            throw new Exception("Error cedula");
        }
        if (!cedulaCorrecta) {
            return cedulaCorrecta;
            // throw new Exception("Cedula Incorrecta");

        }
        return cedulaCorrecta;
    }

    public void guardarEmpleado(Empleado empleado) throws SQLException, Exception {

        if (!validadorDeCedula(empleado.getCedula())) {
            throw new Exception("Cedula Incorrecta");
        } else {

            try {
                empleadoDAO.insertarEmpleado(empleado);
            } catch (Exception e) {
                throw new Exception(e.toString());
            }
        }
    }

    public Empleado usuarioRegistrado(String cedula) {
        return empleadoDAO.obtenerEmpleado(cedula);
    }

    public List<Empleado> listadoEmpleados() {
        return empleadoDAO.obtener();
    }

    public Empleado usuario(String usuario, String contra) throws Exception {
        try {
            Empleado em = empleadoDAO.obtenerUsuario(usuario, contra);
            if (em != null) {
                return em;
            }
        } catch (NoResultException e) {
            throw new Exception("Credenciales Incorrectas");
        }
        return null;

    }

    public List<Transaccion> listadeTransacciones(String cedula) {
        try {
            return transaccionDAO.getListaTransacciones(cedula);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }

    public void guardarTransaccion(Transaccion t) throws Exception {

        try {
            transaccionDAO.insert(t);
        } catch (Exception e) {
            throw new Exception(e.toString());
        }
    }

    public List<Transaccion> obtenerTransaccionesFechaHora(String cedula, String fechaI, String fechaF) {
        String fechaInicio = fechaI + " 00:00:00.000000";
        String fechaFinal = fechaF + " 23:59:59.000000";
        try {
            return transaccionDAO.getListaTransaccionesFechas(cedula, fechaInicio, fechaFinal);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public void guardarTransferenciaLocal(Transferencia transfereciaLocal) {
        transferenciaLocalDAO.insert(transfereciaLocal);
    }

    public byte[] toByteArray(InputStream in) throws IOException {

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];
        int len;

        // read bytes from the input stream and store them in buffer
        while ((len = in.read(buffer)) != -1) {
            // write bytes from the buffer into output stream
            os.write(buffer, 0, len);
        }

        return os.toByteArray();
    }

    public String obtenerFecha2(Date fecha) {
        DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return hourdateFormat.format(fecha);
    }

    public void cambioContrasena(Cliente cliente) {
        String destinatario = cliente.getCorreo();
        String asunto = "CAMBIO DE CONTRASEÑA";
        String cuerpo = "STARBANK                                               SISTEMA TRANSACCIONAL\n"
                + "------------------------------------------------------------------------------\n"
                + "              Estimado(a): " + cliente.getNombre().toUpperCase() + "          "
                + cliente.getApellido().toUpperCase() + "\n"
                + "------------------------------------------------------------------------------\n"
                + "STARBANK le informa que su contraseña ha sido cambiada exitosamente.   \n"
                + "                                                                              \n"
                + "                   Su nueva password es:   " + cliente.getClave() + "       \n"
                + "                       Fecha: " + fecha() + "                                 \n"
                + "                                                                              \n"
                + "------------------------------------------------------------------------------\n";
        CompletableFuture.runAsync(() -> {
            try {
                enviarCorreo(destinatario, asunto, cuerpo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

//			} 
    }

    public int obtenerEdad(Date fechaNacimiento) {
        Calendar a = Calendar.getInstance();
        Calendar b = Calendar.getInstance();
        a.setTime(fechaNacimiento);
        b.setTime(new Date());
        int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
        if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH)
                || (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
            diff--;
        }
        return diff;
    }

    public double valorDecimalCr(double valor) {
        String num = String.format(Locale.ROOT, "%.2f", valor);
        return Double.parseDouble(num);
    }

    public boolean verificarSolicitudSolicitando(String cedulaCliente) {
        List<SolicitudPoliza> solicitudes = solicitudPolizaDAO.getSolicitudes();
        for (SolicitudPoliza solicitud : solicitudes) {
            if (solicitud.getEstado().equalsIgnoreCase("Solicitando")
                    && solicitud.getClientePoliza().getCedula().equalsIgnoreCase(cedulaCliente)) {
                return false;
            }
        }
        return true;
    }

}
