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
import ec.edu.ups.dao.DetallePolizaDAO;
import ec.edu.ups.dao.EmpleadoDAO;
import ec.edu.ups.dao.PolizaDAO;
import ec.edu.ups.dao.SesionClienteDAO;
import ec.edu.ups.dao.SolicitudPolizaDAO;
import ec.edu.ups.dao.TranferenciaExternaDAO;
import ec.edu.ups.dao.TransaccionDAO;
import ec.edu.ups.dao.TransferenciaDAO;
import ec.edu.ups.dao.servicios.PolizaRespuesta;
import ec.edu.ups.dao.servicios.Respuesta;
import ec.edu.ups.dao.servicios.RespuestaTransferenciaExterna;
import ec.edu.ups.modelo.Cliente;
import ec.edu.ups.modelo.CuentaDeAhorro;
import ec.edu.ups.modelo.DetallePoliza;
import ec.edu.ups.modelo.Empleado;
import ec.edu.ups.modelo.Poliza;
import ec.edu.ups.modelo.SesionCliente;
import ec.edu.ups.modelo.SolicitudPoliza;
import ec.edu.ups.modelo.Transaccion;
import ec.edu.ups.modelo.Transferencia;
import ec.edu.ups.modelo.TransferenciaExterna;
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
    @Inject
    private PolizaDAO polizaDAO;
    @Inject
    private DetallePolizaDAO detallepolizaDAO;
    @Inject
    private TranferenciaExternaDAO transferenciaExternaDAO;

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

        try {
            empleadoDAO.insertarEmpleado(empleado);
        } catch (Exception e) {
            throw new Exception(e.toString());
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

    public Respuesta obtenerClienteCuentaAhorro(String numeroCuenta) {
        Respuesta respuesta = new Respuesta();
        CuentaDeAhorro cuentaDeAhorro = cuentaDeAhorroDAO.read(numeroCuenta);
        try {
            if (cuentaDeAhorro != null) {
                respuesta.setCodigo(1);
                respuesta.setDescripcion("Se ha obtenido la cuenta exitosamente");
                respuesta.setCuentaDeAhorro(cuentaDeAhorro);
            } else {
                respuesta.setCodigo(2);
                respuesta.setDescripcion("La Cuenta de Ahorro no existe");
            }
        } catch (Exception e) {
            respuesta.setCodigo(3);
            respuesta.setDescripcion("Error " + e.getMessage());
        }
        return respuesta;
    }

    public Respuesta loginServicio(String username, String password) {
        Cliente cliente = new Cliente();
        Respuesta respuesta = new Respuesta();
        CuentaDeAhorro cuentaDeAhorro = new CuentaDeAhorro();
        List<Poliza> lstPolizas = new ArrayList<Poliza>();
        try {
            cliente = clienteDAO.obtenerClienteUsuarioContraseña(username, password);
            if (cliente != null) {
                respuesta.setCodigo(1);
                respuesta.setDescripcion("Ha ingresado exitosamente");
                respuesta.setCliente(cliente);
                cuentaDeAhorro = cuentaDeAhorroDAO.getCuentaCedulaCliente(cliente.getCedula());
                respuesta.setCuentaDeAhorro(cuentaDeAhorro);
                lstPolizas = polizasAprovadas(cliente.getCedula());
                List<PolizaRespuesta> lstNuevaPolizas = new ArrayList<PolizaRespuesta>();
                for (Poliza poliza : lstPolizas) {
                    PolizaRespuesta polizaRespuesta = new PolizaRespuesta();
                    polizaRespuesta.setCodigoPol(poliza.getCodigoPol());
                    polizaRespuesta.setEstado(poliza.getEstado());
                    polizaRespuesta.setMonto(poliza.getMonto());
                    polizaRespuesta.setInteres(poliza.getInteres());
                    polizaRespuesta.setFechaRegistro(poliza.getFechaRegistro());
                    polizaRespuesta.setFechaVencimiento(poliza.getFechaVencimiento());
                    polizaRespuesta.setDetalles(poliza.getDetalles());
                    lstNuevaPolizas.add(polizaRespuesta);
                }
                respuesta.setListaCreditos(lstNuevaPolizas);
            }
        } catch (Exception e) {
            respuesta.setCodigo(2);
            respuesta.setDescripcion("Error " + e.getMessage());
        }
        return respuesta;
    }

    public List<Poliza> polizasAprovadas(String cedulaCliente) {
        List<Poliza> listaPolizas = polizaDAO.getPolizas();
        List<Poliza> listPolizasTotales = new ArrayList<Poliza>();
        for (Poliza poliza : listaPolizas) {
            if (poliza.getSolicitud().getClientePoliza().getCedula().equalsIgnoreCase(cedulaCliente)) {
                listPolizasTotales.add(poliza);
            }
        }
        return listPolizasTotales;
    }

    public Respuesta cambioContraseña(String correo, String contraAntigua, String contraActual) {
        System.out.println(correo + "" + contraAntigua);
        Cliente cliente = new Cliente();
        Respuesta respuesta = new Respuesta();
        try {
            cliente = clienteDAO.obtenerClienteCorreoContraseña(correo, contraAntigua);
            System.out.println(cliente.toString());
            cliente.setClave(contraActual);
            clienteDAO.update(cliente);
            respuesta.setCodigo(1);
            respuesta.setDescripcion("Se ha actualizado su contraseña exitosamente");
            cambioContrasena(cliente);
        } catch (Exception e) {
            respuesta.setCodigo(2);
            respuesta.setDescripcion("Error " + e.getMessage());
        }

        return respuesta;
    }

    public String realizarTransaccion(String cuenta, double monto, String tipoTransaccion) {
        CuentaDeAhorro clp = cuentaDeAhorroDAO.read(cuenta);
        if (clp != null) {
            if (tipoTransaccion.equalsIgnoreCase("deposito")) {
                Double nvmonto = clp.getSaldoCuentaDeAhorro() + monto;
                clp.setSaldoCuentaDeAhorro(nvmonto);
                actualizarCuentaDeAhorro(clp);
                Transaccion t = new Transaccion();
                t.setCliente(clp.getCliente());
                t.setMonto(monto);
                t.setFecha(new Date());
                t.setTipo("deposito");
                t.setSaldoCuenta(nvmonto);
                try {
                    guardarTransaccion(t);
                    return "Hecho";
                } catch (Exception e1) {
                    e1.getMessage();
                }
            } else if (tipoTransaccion.equalsIgnoreCase("retiro") && monto <= clp.getSaldoCuentaDeAhorro()) {
                Double nvmonto2 = clp.getSaldoCuentaDeAhorro() - monto;
                clp.setSaldoCuentaDeAhorro(nvmonto2);
                actualizarCuentaDeAhorro(clp);
                Transaccion t2 = new Transaccion();
                t2.setCliente(clp.getCliente());
                t2.setMonto(monto);
                t2.setFecha(new Date());
                t2.setTipo("retiro");
                t2.setSaldoCuenta(nvmonto2);
                try {
                    guardarTransaccion(t2);
                    return "Hecho";
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.getMessage();
                }
            } else {
                return "Monto exedido";
            }
        } else {
            return "Cuenta Inexistente";
        }
        return "Fallido";
    }

    public Respuesta realizarTransferencia(String cedula, String cuentaAhorro2, double monto) {
        Respuesta respuesta = new Respuesta();
        CuentaDeAhorro cuentaAhorro = cuentaDeAhorroDAO.getCuentaCedulaCliente(cedula);
        CuentaDeAhorro cuentaAhorroTransferir = cuentaDeAhorroDAO.read(cuentaAhorro2);
        try {
            if (cuentaAhorro.getSaldoCuentaDeAhorro() >= monto) {
                cuentaAhorro.setSaldoCuentaDeAhorro(cuentaAhorro.getSaldoCuentaDeAhorro() - monto);
                actualizarCuentaDeAhorro(cuentaAhorro);
                cuentaAhorroTransferir.setSaldoCuentaDeAhorro(cuentaAhorroTransferir.getSaldoCuentaDeAhorro() + monto);
                actualizarCuentaDeAhorro(cuentaAhorroTransferir);
                Transferencia transfereciaLocal = new Transferencia();
                transfereciaLocal.setCliente(cuentaAhorro.getCliente());
                transfereciaLocal.setCuentaDeAhorroDestino(cuentaAhorroTransferir);
                transfereciaLocal.setMonto(monto);
                guardarTransferenciaLocal(transfereciaLocal);
                respuesta.setCodigo(1);
                respuesta.setDescripcion("Transferencia Satisfactoria");
            } else {
                respuesta.setCodigo(2);
                respuesta.setDescripcion("Monto Excedido");
            }
        } catch (Exception e) {
            respuesta.setCodigo(3);
            respuesta.setDescripcion(e.getMessage());
        }
        return respuesta;
    }

    public RespuestaTransferenciaExterna realizarTransferenciaExterna(TransferenciaExterna transferenciaExterna) {
        RespuestaTransferenciaExterna respuestaTransferenciaExterna = new RespuestaTransferenciaExterna();
        try {
            CuentaDeAhorro cuentaDeAhorro = cuentaDeAhorroDAO.read(transferenciaExterna.getCuentaPersonaLocal());
            if (cuentaDeAhorro != null) {
                if (cuentaDeAhorro.getSaldoCuentaDeAhorro() >= transferenciaExterna.getMontoTransferencia()) {
                    transferenciaExterna.setFechaTransaccion(new Date());
                    transferenciaExternaDAO.insert(transferenciaExterna);
                    cuentaDeAhorro.setSaldoCuentaDeAhorro(cuentaDeAhorro.getSaldoCuentaDeAhorro() - transferenciaExterna.getMontoTransferencia());
                    cuentaDeAhorroDAO.update(cuentaDeAhorro);
                    respuestaTransferenciaExterna.setCodigo(1);
                    respuestaTransferenciaExterna.setDescripcion("Transferencia se ha realizado exitosamente");
                } else {
                    respuestaTransferenciaExterna.setCodigo(2);
                    respuestaTransferenciaExterna.setDescripcion("No tiene esa cantidad en su cuenta");
                }
            } else {
                respuestaTransferenciaExterna.setCodigo(3);
                respuestaTransferenciaExterna.setDescripcion("La cuenta no existe");
            }
        } catch (Exception e) {
            respuestaTransferenciaExterna.setCodigo(4);
            respuestaTransferenciaExterna.setDescripcion("Error : " + e.getMessage());
        }
        return respuestaTransferenciaExterna;
    }

    public void guardarSolicitudPoliza(SolicitudPoliza solicituPoliza) {
        solicituPoliza.setCodigoPoliza(codigoPolizas());
        solicituPoliza.setSaldoCuenta(saldoCuenta(solicituPoliza));
        solicituPoliza.setCantidadPolizas(numeroPolizas(solicituPoliza));
        solicitudPolizaDAO.insert(solicituPoliza);
    }

    public Double saldoCuenta(SolicitudPoliza solicitudPoliza) {
        CuentaDeAhorro cuentaDeAhorro = cuentaDeAhorroDAO
                .getCuentaCedulaCliente(solicitudPoliza.getClientePoliza().getCedula());
        if (cuentaDeAhorro != null) {
            double saldo = cuentaDeAhorro.getSaldoCuentaDeAhorro();
            return saldo;
        }
        return 0.0;
    }

    public int numeroPolizas(SolicitudPoliza solicitudPoliza) {
        List<Poliza> lstPoliza = polizaDAO.getPolizas();
        int contador = 0;
        for (Poliza poliza : lstPoliza) {
            if (poliza.getSolicitud().getClientePoliza().getCedula()
                    .equalsIgnoreCase(solicitudPoliza.getClientePoliza().getCedula())) {
                contador++;
            }
        }
        return contador;
    }

    public int codigoPolizas() {
        List<SolicitudPoliza> lstSolicitudPoliza = solicitudPolizaDAO.getSolicitudes();
        int contador = lstSolicitudPoliza.size() + 1;
        return contador;
    }

    public List<SolicitudPoliza> listadoSolicitudPolizas() {
        return solicitudPolizaDAO.getSolicitudes();
    }

    public void actualizarSolicitudPoliza(SolicitudPoliza solicitudPoliza) {
        solicitudPolizaDAO.update(solicitudPoliza);
    }

    public void rechazarPoliza(Cliente cliente, String razon) {
        String destinatario = cliente.getCorreo();
        String asunto = "RECHAZO DE POLIZA";
        String cuerpo = "StarBack\n"
                + "------------------------------------------------------------------------------\n"
                + "              Estimado(a): " + cliente.getNombre().toUpperCase() + " "
                + cliente.getApellido().toUpperCase() + "\n"
                + "------------------------------------------------------------------------------\n"
                + "STARBANK le informa que su poliza no ha sido aprobado.                \n"
                + "Los detalles del rechazo se muestran a continuación.                          \n"
                + "                                   DETALLES                                   \n" + razon
                + "						             \n"
                + "                                                                              \n"
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

    public void guardarPoliza(Poliza poliza) {
        polizaDAO.insert(poliza);
    }

    public void aprobarPoliza(Poliza poliza, Cliente cliente) {
        String destinatario = cliente.getCorreo();
        String asunto = "APROBACIÓN DE POLIZA";
        String cuerpo = "STARBANK\n"
                + "------------------------------------------------------------------------------\n"
                + "              Estimado(a): " + cliente.getNombre().toUpperCase() + " "
                + cliente.getApellido().toUpperCase() + "\n"
                + "------------------------------------------------------------------------------\n"
                + "STARBANK le informa que su poliza ha sido aprobado.                   \n"
                + "                                                                              \n"
                + "                         Fecha: " + obtenerFecha(poliza.getFechaRegistro()) + "\n"
                + "                                                                              \n"
                + "La informacion de sus cuotas se encuentra en el archivo adjunto.              \n"
                + "------------------------------------------------------------------------------\n";

        CompletableFuture.runAsync(() -> {
            try {
                // enviarCorreo2(destinatario, asunto, cuerpo, poliza);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /*public void enviarCorreo2(String destinatario, String asunto, String cuerpo, Poliza poliza) {
        Properties propiedad = new Properties();
        propiedad.setProperty("mail.smtp.host", "smtp.gmail.com");
        propiedad.setProperty("mail.smtp.starttls.enable", "true");
        propiedad.setProperty("mail.smtp.port", "587");

        Session sesion = Session.getDefaultInstance(propiedad);
        String correoEnvia = "starbankoficial@gmail.com";
        String contrasena = "ZJRIcfjy1719";

        MimeMessage mail = new MimeMessage(sesion);
        Multipart multipart = new MimeMultipart();

        MimeBodyPart attachmentPart = new MimeBodyPart();

        MimeBodyPart textPart = new MimeBodyPart();

        try {
            mail.setFrom("STARBANK <" + correoEnvia + ">");
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
            mail.setSubject(asunto);
            File f = generarTabla(poliza);
            attachmentPart.attachFile(f);
            textPart.setText(cuerpo);
            multipart.addBodyPart(attachmentPart);
            multipart.addBodyPart(textPart);
            mail.setContent(multipart);

            Transport transportar = sesion.getTransport("smtp");
            transportar.connect(correoEnvia, contrasena);
            transportar.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));
        } catch (AddressException | IOException ex) {
            System.out.println(ex.getMessage());
        } catch (MessagingException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public File generarTablaAmor(Credito credito) {
        try {
            Cliente cliente = credito.getSolicitud().getClienteCredito();
            double monto = credito.getMonto();
            double interes = credito.getInteres();
            int meses = Integer.parseInt(credito.getSolicitud().getMesesCredito());
            Document document = new Document();

            File file = File.createTempFile("TablaAmortizacion", ".pdf");
            FileOutputStream fos = new FileOutputStream(file);
            PdfWriter.getInstance(document, fos);
            document.open();
            Paragraph par = new Paragraph();
            par.add(new Phrase("COOP JAM"));
            par.setAlignment(Element.ALIGN_CENTER);
            document.add(par);
            document.add(Chunk.NEWLINE);
            Paragraph par1 = new Paragraph();
            par1.add(new Phrase("TABLA DE AMORTIZACIÓN"));
            par1.setAlignment(Element.ALIGN_CENTER);
            document.add(par1);
            document.add(Chunk.NEWLINE);
            Paragraph par2 = new Paragraph();
            par2.add(new Phrase("               Detalles de Crédito"));
            par2.add(Chunk.NEWLINE);
            par2.add(new Phrase("               Cliente: " + cliente.getNombre() + " " + cliente.getApellido()));
            par2.add(Chunk.NEWLINE);
            par2.add(new Phrase("               Fecha Registro: " + obtenerFecha2(credito.getFechaRegistro())));
            par2.add(Chunk.NEWLINE);
            par2.add(new Phrase("               Fecha Vencimiento: " + obtenerFecha2(credito.getFechaVencimiento())));
            par2.add(Chunk.NEWLINE);
            par2.add(new Phrase("               Monto: " + monto));
            par2.add(Chunk.NEWLINE);
            par2.add(new Phrase("               Interes: " + interes + "%"));
            par2.add(Chunk.NEWLINE);
            par2.add(new Phrase("               Plazo: " + meses + " meses"));
            document.add(par2);
            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(6);
            PdfPCell celdaInicial = new PdfPCell(new Paragraph("Detalles de las Cuotas"));
            celdaInicial.setColspan(6);
            celdaInicial.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(celdaInicial);
            PdfPCell ct1 = new PdfPCell(new Phrase("#Cuota"));
            ct1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(ct1);
            PdfPCell ct2 = new PdfPCell(new Phrase("Fecha"));
            ct2.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(ct2);
            PdfPCell ct3 = new PdfPCell(new Phrase("Cuota"));
            ct3.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(ct3);
            PdfPCell ct4 = new PdfPCell(new Phrase("Capital"));
            ct4.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(ct4);
            PdfPCell ct5 = new PdfPCell(new Phrase("Interes"));
            ct5.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(ct5);
            PdfPCell ct6 = new PdfPCell(new Phrase("Saldo"));
            ct6.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(ct6);

            for (DetallePoliza dcre : credito.getDetalles()) {
                PdfPCell cell1 = new PdfPCell(new Phrase(String.valueOf(dcre.getNumeroCuota())));
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell1);
                PdfPCell cell2 = new PdfPCell(new Phrase(obtenerFecha2(dcre.getFechaPago())));
                cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell2);
                PdfPCell cell3 = new PdfPCell(new Phrase(String.valueOf(valorDecimalCr(dcre.getSaldo()))));
                cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                table.addCell(cell3);
                PdfPCell cell4 = new PdfPCell(new Phrase(String.valueOf(valorDecimalCr(dcre.getCuota()))));
                cell4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                table.addCell(cell4);
                PdfPCell cell5 = new PdfPCell(new Phrase(String.valueOf(valorDecimalCr(dcre.getInteres()))));
                cell5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                table.addCell(cell5);
                PdfPCell cell6 = new PdfPCell(new Phrase(String.valueOf(valorDecimalCr(dcre.getMonto()))));
                cell6.setHorizontalAlignment(Element.ALIGN_RIGHT);
                table.addCell(cell6);
            }
            document.add(table);

            document.close();
            return file;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }*/
    public List<Poliza> polizasAprobados(String cedulaCliente) {
        List<Poliza> listaPolizas = polizaDAO.getPolizas();
        List<Poliza> listPolizTotales = new ArrayList<Poliza>();
        for (Poliza poliza : listaPolizas) {
            if (poliza.getSolicitud().getClientePoliza().getCedula().equalsIgnoreCase(cedulaCliente)) {
                listPolizTotales.add(poliza);
            }
        }
        return listPolizTotales;
    }
}
