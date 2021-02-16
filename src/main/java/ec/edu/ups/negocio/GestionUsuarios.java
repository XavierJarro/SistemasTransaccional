/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ups.negocio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.NoResultException;
import ec.edu.ups.dao.ClienteDAO;
import ec.edu.ups.dao.CuentaDeAhorroDAO;
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
import ec.edu.ups.modelo.Empleado;
import ec.edu.ups.modelo.Poliza;
import ec.edu.ups.modelo.SesionCliente;
import ec.edu.ups.modelo.SolicitudPoliza;
import ec.edu.ups.modelo.Transaccion;
import ec.edu.ups.modelo.Transferencia;
import ec.edu.ups.modelo.TransferenciaExterna;
import java.util.List;
import java.util.Properties;
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
    private TranferenciaExternaDAO transferenciaExternaDAO;

    /**
     * Metodo que permite generar el numero de cuenta al que vamos a crear
     *
     *
     */
    public String generarNumeroDeCuenta() {
        int numeroInicio = 4040;
        List<CuentaDeAhorro> listaCuentas = listaCuentaDeAhorros();
        int numero = listaCuentas.size() + 1;
        String resultado = String.format("%08d", numero);
        String resultadoFinal = String.valueOf(numeroInicio) + resultado;
        return resultadoFinal;
    }

    /**
     * Metodo que permite obtener el usuario
     *
     *
     */
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

    /**
     * Metodo que permite obtener la contrasena
     *
     *
     */
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

    /**
     * Metodo que permite enviar correo
     *
     *
     */
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

    /**
     * Metodo que permite dar dormato a la fecha
     *
     *
     */
    public String fecha() {
        Date date = new Date();
        DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return hourdateFormat.format(date);
    }

    /**
     * Metodo que permite obtener el fecha
     *
     *
     */
    public String obtenerFecha(Date fecha) {
        DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return hourdateFormat.format(fecha);
    }

    /**
     * Metodo que permite guardar el cliente en la base de datos
     *
     *
     */
    public void guardarCliente(Cliente c) {
        clienteDAO.insert(c);
    }

    /**
     * Metodo que permite buscar el cliente en la base de datos
     *
     *
     */
    public Cliente buscarCliente(String cedulaCliente) {
        Cliente cliente = clienteDAO.read(cedulaCliente);
        return cliente;
    }

    /**
     * Metodo que permite buscar cliente en base de datos por contrasena y
     * nombre de usuario
     *
     *
     */
    public Cliente buscarClienteUsuarioContraseña(String usuario, String contraseña) {
        try {
            return clienteDAO.obtenerClienteUsuarioContraseña(usuario, contraseña);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Metodo que permite eliminar un cliente si es necesario
     *
     *
     */
    public void eliminarCliente(String cedulaCliente) {
        clienteDAO.delete(cedulaCliente);
    }

    /**
     * Metodo que permite oactualizar el cliente
     *
     *
     */
    public void actualizarCliente(Cliente cliente) {
        clienteDAO.update(cliente);
    }

    /**
     * Metodo que permite obtener la lista de los clientes
     *
     *
     */
    public List<Cliente> listaClientes() {
        List<Cliente> clientes = clienteDAO.getClientes();
        return clientes;
    }

    /**
     * Metodo que permite guardar un cuenta de ahorrros
     *
     *
     */
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

    /**
     * Metodo que permite buscar una ca
     *
     *
     */
    public CuentaDeAhorro buscarCuentaDeAhorro(String numeroCuentaDeAhorro) {
        CuentaDeAhorro cuentaDeAhorro = cuentaDeAhorroDAO.read(numeroCuentaDeAhorro);
        return cuentaDeAhorro;
    }

    /**
     * Metodo que permite buscar una cs por cliente especifico
     *
     *
     */
    public CuentaDeAhorro buscarCuentaDeAhorroCliente(String cedulaCliente) {
        CuentaDeAhorro cuentaDeAhorro = cuentaDeAhorroDAO.getCuentaCedulaCliente(cedulaCliente);
        return cuentaDeAhorro;

    }

    /**
     * Metodo que permite eliminar una cuenta de ahorros
     *
     *
     */
    public void eliminarCuentaDeAhorro(String numeroCuentaDeAhorro) {
        cuentaDeAhorroDAO.delete(numeroCuentaDeAhorro);
    }

    /**
     * Metodo que permite actualizar una cuenta de ahorros
     *
     *
     */
    public void actualizarCuentaDeAhorro(CuentaDeAhorro cuentaDeAhorro) {
        cuentaDeAhorroDAO.update(cuentaDeAhorro);
    }

    /**
     * Metodo que permite obtener una lista de cuenta de ahorros
     *
     *
     */
    public List<CuentaDeAhorro> listaCuentaDeAhorros() {
        List<CuentaDeAhorro> clientes = cuentaDeAhorroDAO.getCuentaDeAhorros();
        return clientes;
    }

    /**
     * Metodo que permite guardar la sesion de cuando se loguea
     *
     *
     */
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

    /**
     * Metodo que permite obtener el atributo
     *
     *
     */
    public SesionCliente buscarSesionCliente(int codigoSesionCliente) {
        return sesionClienteDAO.read(codigoSesionCliente);
    }

    /**
     * Metodo que permite obtener la sesiones que realizo el cliente en su
     * cuenta
     *
     *
     */
    public List<SesionCliente> obtenerSesionesCliente(String cedulaCliente) {
        try {
            return sesionClienteDAO.obtenerSesionCliente(cedulaCliente);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Metodo que permite verificar si la cedula ingresada esta correcta
     *
     *
     */
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

    /**
     * Metodo que permite guardar en la base dedatos a un empleado
     *
     *
     */
    public void guardarEmpleado(Empleado empleado) throws SQLException, Exception {

        try {
            empleadoDAO.insertarEmpleado(empleado);
        } catch (Exception e) {
            throw new Exception(e.toString());
        }

    }

    /**
     * Metodo que permite obtener a un empleado al memento de loguearse
     *
     *
     */
    public Empleado usuarioRegistrado(String cedula) {
        return empleadoDAO.obtenerEmpleado(cedula);
    }

    /**
     * Metodo que permite obtener una lista de los empleados existentes
     *
     *
     */
    public List<Empleado> listadoEmpleados() {
        return empleadoDAO.obtener();
    }

    /**
     * Metodo que permite obtener el usuario mediante la contrase;a y nombre de
     * usuario para el login
     *
     *
     */
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

    /**
     * Metodo que permite obtener las trasanciones que tiene un cliente en
     * especifico.
     *
     *
     */
    public List<Transaccion> listadeTransacciones(String cedula) {
        try {
            return transaccionDAO.getListaTransacciones(cedula);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }

    /**
     * Metodo que permite guardar transacciones en la base de datos
     *
     *
     */
    public void guardarTransaccion(Transaccion t) throws Exception {

        try {
            transaccionDAO.insert(t);
        } catch (Exception e) {
            throw new Exception(e.toString());
        }
    }

    /**
     * Metodo que permite obtener el atributo
     *
     *
     */
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

    /**
     * Metodo que permite guardar transferencias locales de la mismo banco
     *
     *
     */
    public void guardarTransferenciaLocal(Transferencia transfereciaLocal) {
        transferenciaLocalDAO.insert(transfereciaLocal);
    }

    /**
     * Metodo que permite obtener el atributo
     *
     *
     */
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

    /**
     * Metodo que permite obtener la fecha
     *
     *
     */
    public String obtenerFecha2(Date fecha) {
        DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return hourdateFormat.format(fecha);
    }

    /**
     * Metodo que permite cambiar la contrasena del cliente
     *
     *
     */
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

    /**
     * Metodo que permite obtener el atributo
     *
     *
     */
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

    /**
     * Metodo que permite obtener el atributo
     *
     *
     */
    public double valorDecimalCr(double valor) {
        String num = String.format(Locale.ROOT, "%.2f", valor);
        return Double.parseDouble(num);
    }

    /**
     * Metodo que permite obtener las solicitudes de poliza
     *
     *
     */
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

    /**
     * Metodo que permite obtener el atributo
     *
     *
     */
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

    /**
     * Metodo que permite hacer el login en la aplicacion web
     *
     *
     */
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
                lstPolizas = polizasAprobadas(cliente.getCedula());
                List<PolizaRespuesta> lstNuevaPolizas = new ArrayList<PolizaRespuesta>();
                for (Poliza poliza : lstPolizas) {
                    PolizaRespuesta polizaRespuesta = new PolizaRespuesta();
                    polizaRespuesta.setCodigoPol(poliza.getCodigoPol());
                    polizaRespuesta.setEstado(poliza.getEstado());
                    polizaRespuesta.setMonto(poliza.getMonto());
                    polizaRespuesta.setTasa(poliza.getTasa());
                    polizaRespuesta.setInteres(poliza.getInteres());
                    polizaRespuesta.setFechaRegistro(poliza.getFechaRegistro());
                    polizaRespuesta.setFechaVencimiento(poliza.getFechaVencimiento());
                    lstNuevaPolizas.add(polizaRespuesta);
                }
                respuesta.setListaPolizas(lstNuevaPolizas);
            }
        } catch (Exception e) {
            respuesta.setCodigo(2);
            respuesta.setDescripcion("Error " + e.getMessage());
        }
        return respuesta;
    }

    /**
     * Metodo que permite obtener solo las polizas aprobadas
     *
     *
     */
    public List<Poliza> polizasAprobadas(String cedulaCliente) {
        List<Poliza> listaPolizas = polizaDAO.getPolizas();
        List<Poliza> listPolizasTotales = new ArrayList<Poliza>();
        for (Poliza poliza : listaPolizas) {
            if (poliza.getSolicitud().getClientePoliza().getCedula().equalsIgnoreCase(cedulaCliente)) {
                listPolizasTotales.add(poliza);
            }
        }
        return listPolizasTotales;
    }

    /**
     * Metodo que permite cambiar la contrasena
     *
     *
     */
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

    /**
     * Metodo que permite realizar una transferencia
     *
     *
     */
    public Respuesta realizarTransferencia(String cedula, String cuentaAhorro2, double monto) {
        Respuesta respuesta = new Respuesta();
        CuentaDeAhorro cuentaAhorro = cuentaDeAhorroDAO.getCuentaCedulaCliente(cedula);
        CuentaDeAhorro cuentaAhorroTransferir = cuentaDeAhorroDAO.read(cuentaAhorro2);
        try {
            if (cuentaAhorro.getSaldoCuentaDeAhorro() >= monto) {

                Double nvmonto2 = cuentaAhorro.getSaldoCuentaDeAhorro() - monto;

                cuentaAhorro.setSaldoCuentaDeAhorro(nvmonto2);
                actualizarCuentaDeAhorro(cuentaAhorro);
                cuentaAhorroTransferir.setSaldoCuentaDeAhorro(cuentaAhorroTransferir.getSaldoCuentaDeAhorro() + monto);
                actualizarCuentaDeAhorro(cuentaAhorroTransferir);
                Transferencia transfereciaLocal = new Transferencia();
                transfereciaLocal.setCliente(cuentaAhorro.getCliente());
                transfereciaLocal.setCuentaDeAhorroDestino(cuentaAhorroTransferir);
                transfereciaLocal.setMonto(monto);
                guardarTransferenciaLocal(transfereciaLocal);

                Transaccion t2 = new Transaccion();
                t2.setCliente(cuentaAhorro.getCliente());
                t2.setMonto(monto);
                t2.setFecha(new Date());
                t2.setTipo("Transferencia");
                t2.setSaldoCuenta(nvmonto2);

                guardarTransaccion(t2);

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

    /**
     * Metodo que permite realizar una transferencia externa a otra entidad
     *
     *
     */
    public RespuestaTransferenciaExterna realizarTransferenciaExterna(TransferenciaExterna transferenciaExterna) {
        RespuestaTransferenciaExterna respuestaTransferenciaExterna = new RespuestaTransferenciaExterna();
        try {
            CuentaDeAhorro cuentaDeAhorro = cuentaDeAhorroDAO.read(transferenciaExterna.getCuentaPersonaLocal());
            if (cuentaDeAhorro != null) {
                if (cuentaDeAhorro.getSaldoCuentaDeAhorro() >= transferenciaExterna.getMontoTransferencia()) {
                    transferenciaExterna.setFechaTransaccion(new Date());
                    Double nvmonto2 = cuentaDeAhorro.getSaldoCuentaDeAhorro() - transferenciaExterna.getMontoTransferencia();
                    transferenciaExternaDAO.insert(transferenciaExterna);
                    cuentaDeAhorro.setSaldoCuentaDeAhorro(nvmonto2);
                    cuentaDeAhorroDAO.update(cuentaDeAhorro);
                    respuestaTransferenciaExterna.setCodigo(1);
                    respuestaTransferenciaExterna.setDescripcion("Transferencia se ha realizado exitosamente");

                    Transaccion t2 = new Transaccion();
                    t2.setCliente(cuentaDeAhorro.getCliente());
                    t2.setMonto(transferenciaExterna.getMontoTransferencia());
                    t2.setFecha(new Date());
                    t2.setTipo("Transferencia externa");
                    t2.setSaldoCuenta(nvmonto2);

                    guardarTransaccion(t2);

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

    /**
     * Metodo que permite guardar soicitudes de poliza
     *
     *
     */
    public void guardarSolicitudPoliza(SolicitudPoliza solicituPoliza) {
        solicituPoliza.setCodigoPoliza(codigoPolizas());
        solicituPoliza.setSaldoCuenta(saldoCuenta(solicituPoliza));
        solicituPoliza.setCantidadPolizas(numeroPolizas(solicituPoliza));
        solicitudPolizaDAO.insert(solicituPoliza);
    }

    /**
     * Metodo que permite obtener el saldo de la cuenta de un cliente
     *
     *
     */
    public Double saldoCuenta(SolicitudPoliza solicitudPoliza) {
        CuentaDeAhorro cuentaDeAhorro = cuentaDeAhorroDAO
                .getCuentaCedulaCliente(solicitudPoliza.getClientePoliza().getCedula());
        if (cuentaDeAhorro != null) {
            double saldo = cuentaDeAhorro.getSaldoCuentaDeAhorro();
            return saldo;
        }
        return 0.0;
    }

    /**
     * Metodo que permite obtener el codigo siguiente de poliza
     *
     *
     */
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

    /**
     * Metodo que permite obtener el atributo
     *
     *
     */
    public int codigoPolizas() {
        List<SolicitudPoliza> lstSolicitudPoliza = solicitudPolizaDAO.getSolicitudes();
        int contador = lstSolicitudPoliza.size() + 1;
        return contador;
    }

    /**
     * Metodo que permite obtener el atributo
     *
     *
     */
    public List<SolicitudPoliza> listadoSolicitudPolizas() {
        return solicitudPolizaDAO.getSolicitudes();
    }

    /**
     * Metodo que permite obtener el atributo
     *
     *
     */
    public void actualizarSolicitudPoliza(SolicitudPoliza solicitudPoliza) {
        solicitudPolizaDAO.update(solicitudPoliza);
    }

    /**
     * Metodo que permite rechazar una poliza
     *
     *
     */
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

    /**
     * Metodo que permite oguardar una poliza en la base de datos
     *
     *
     */
    public void guardarPoliza(Poliza poliza) {
        polizaDAO.insert(poliza);
    }

    /**
     * Metodo que permite obtener un alista de las polizas aprobadas
     *
     *
     */
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

    /**
     * Metodo que permite bloquear la cuenta de un usuario al fallar 3 intentos
     *
     *
     */
    public void bloquearCuenta(Cliente cliente) {
        cliente.setEstado("B");
        try {
            clienteDAO.update(cliente);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo que permite obtener actualizar el estado de la cuenta del cliente
     * al fallar 3 intentos
     *
     *
     */
    public void intentosFallidosCliente(Cliente cliente) {
        if (cliente.getContador() == 0) {
            bloquearCuenta(cliente);
        } else {
            try {
                clienteDAO.update(cliente);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Metodo que permite desbloquear la cuenta de un usurio
     *
     *
     */
    public void desbloquear(String cedula) {
        try {
            for (Cliente c : listaClientes()) {
                if (c.getCedula().equalsIgnoreCase(cedula)) {
                    c.setEstado("D");
                    c.setContador(3);
                    try {
                        clienteDAO.update(c);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo que permite obtener el atributo
     *
     *
     */
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

    /**
     * Metodo que permite obtener el atributo
     *
     *
     */
    public Date crearFechaVencimiento(int cuotas) {
        Date fecha = new Date();
        Calendar calendar1 = Calendar.getInstance();
        calendar1.add(Calendar.MONTH, cuotas);
        fecha = calendar1.getTime();
        return fecha;
    }

    /**
     * Metodo que permite obtener el atributo
     *
     *
     */
    public Poliza verPoliza(int codigo) {
        Poliza pol = polizaDAO.read(codigo);
        return pol;
    }

    /**
     * Metodo que permite obtener el atributo
     *
     *
     */
    public void relizarCobro(int codigo) {
        Poliza pol = verPoliza(codigo);
        CuentaDeAhorro clp = buscarCuentaDeAhorroCliente(pol.getSolicitud().getClientePoliza().getCedula());
        pol.setEstado("Cancelada");
        polizaDAO.update(pol);
        Double nvmonto2 = clp.getSaldoCuentaDeAhorro() + pol.getTotal();
        clp.setSaldoCuentaDeAhorro(nvmonto2);
        actualizarCuentaDeAhorro(clp);
        Transaccion transaccion = new Transaccion();
        transaccion.setCliente(clp.getCliente());
        transaccion.setMonto(pol.getTotal());
        transaccion.setFecha(new Date());
        transaccion.setTipo("Credito");
        transaccion.setSaldoCuenta(nvmonto2);

        try {
            guardarTransaccion(transaccion);
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.getMessage();
        }

    }

}
