package ec.edu.ups.vista;

import ec.edu.ups.modelo.Cliente;
import ec.edu.ups.modelo.CuentaDeAhorro;
import ec.edu.ups.modelo.Poliza;
import ec.edu.ups.modelo.SesionCliente;
import ec.edu.ups.modelo.SolicitudPoliza;
import ec.edu.ups.modelo.Transaccion;
import ec.edu.ups.negocio.GestionUsuarioLocal;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.servlet.http.Part;

import org.primefaces.event.CloseEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.MoveEvent;
import org.primefaces.model.file.UploadedFile;

/**
 *
 * @author Starman
 */
@ManagedBean
@SessionScoped
public class ClientesBean {

    @Inject
    private GestionUsuarioLocal gestionUsuarios;

    private Cliente cliente;
    private String numeroCuenta;
    private CuentaDeAhorro cuentaDeAhorro;
    private CuentaDeAhorro buscarCuentaDeAhorro;
    private String cedulaParametro;
    private Transaccion transaccion;
    private List<Cliente> lstClientes;
    private List<SesionCliente> lstSesionesCliente;
    private List<Transaccion> lstTransacciones;
    private String saldoCuenta;
    private Date fechaInicio;
    private Date fechaFinal;
    private String tipoTransaccion;
    private String fechasInvalidas;
    private String cedulaGarante;
    private InputStream arCedula;
    private InputStream arPlanillaServicios;
    private String mensajeGarante;
    private double ingresos;
    private double egresos;
    private double tasa;
    private boolean editable;
    private int codigoCredito;
    private SolicitudPoliza solicitudPoliza;
    private List<Poliza> lstPolizasAprobados;

    @PostConstruct
    public void init() {
        listarClientes();
        tipoTransaccion = "Todos";
        System.out.println(lstClientes.size());
        cuentaDeAhorro = new CuentaDeAhorro();
        cliente = new Cliente();
        buscarCuentaDeAhorro = new CuentaDeAhorro();
        solicitudPoliza = new SolicitudPoliza();
        tasa = 0.0;
        solicitudPoliza.setTasaPoliza(tasa);
        lstPolizasAprobados = new ArrayList<Poliza>();
    }

    public double getTasa() {
        return tasa;
    }

    public void setTasa(double tasa) {
        this.tasa = tasa;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public GestionUsuarioLocal getGestionUsuarios() {
        return gestionUsuarios;
    }

    public void setGestionUsuarios(GestionUsuarioLocal gestionUsuarios) {
        this.gestionUsuarios = gestionUsuarios;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public CuentaDeAhorro getCuentaDeAhorro() {
        return cuentaDeAhorro;
    }

    public void setCuentaDeAhorro(CuentaDeAhorro cuentaDeAhorro) {
        this.cuentaDeAhorro = cuentaDeAhorro;
    }

    public CuentaDeAhorro getBuscarCuentaDeAhorro() {
        return buscarCuentaDeAhorro;
    }

    public void setBuscarCuentaDeAhorro(CuentaDeAhorro buscarCuentaDeAhorro) {
        this.buscarCuentaDeAhorro = buscarCuentaDeAhorro;
    }

    public String getCedulaParametro() {
        return cedulaParametro;
    }

    public String getFechasInvalidas() {
        return fechasInvalidas;
    }

    public void setFechasInvalidas(String fechasInvalidas) {
        this.fechasInvalidas = fechasInvalidas;
    }

    public List<Poliza> getLstPolizasAprobados() {
        return lstPolizasAprobados;
    }

    public void setLstPolizasAprobados(List<Poliza> lstPolizasAprobados) {
        this.lstPolizasAprobados = lstPolizasAprobados;
    }

    public void setCedulaParametro(String cedulaParametro) {
        this.cedulaParametro = cedulaParametro;
        if (cedulaParametro != null) {
            System.out.println("ENTRAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA al if");
            try {
                buscarCuentaDeAhorro = gestionUsuarios.buscarCuentaDeAhorroCliente(cedulaParametro);
                System.out.println(cedulaParametro + "---------------------------------------------------------cdeula");
                List<Transaccion> lista = gestionUsuarios.listadeTransacciones(cedulaParametro);
                transaccion = lista.get(lista.size() - 1);
                ultimosDias();
                System.out.println(buscarCuentaDeAhorro.getNumeroCuentaDeAhorro() + "---------------------------------------------------------");
                polizasAprobados(cedulaParametro);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Transaccion getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(Transaccion transaccion) {
        this.transaccion = transaccion;
    }

    public List<Cliente> getLstClientes() {
        return lstClientes;
    }

    public void setLstClientes(List<Cliente> lstClientes) {
        this.lstClientes = lstClientes;
    }

    public List<SesionCliente> getLstSesionesCliente() {
        return lstSesionesCliente;
    }

    public void setLstSesionesCliente(List<SesionCliente> lstSesionesCliente) {
        this.lstSesionesCliente = lstSesionesCliente;
    }

    public List<Transaccion> getLstTransacciones() {
        return lstTransacciones;
    }

    public void setLstTransacciones(List<Transaccion> lstTransacciones) {
        this.lstTransacciones = lstTransacciones;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public String getTipoTransaccion() {
        return tipoTransaccion;
    }

    public void setTipoTransaccion(String tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    public String getCedulaGarante() {
        return cedulaGarante;
    }

    public void setCedulaGarante(String cedulaGarante) {
        this.cedulaGarante = cedulaGarante;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public int getCodigoCredito() {
        return codigoCredito;
    }

    public void setCodigoCredito(int codigoCredito) {
        this.codigoCredito = codigoCredito;
    }

    public String crearCliente() {
        try {
            gestionUsuarios.guardarCliente(cliente);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public double getIngresos() {
        return ingresos;
    }

    public void setIngresos(double ingresos) {
        this.ingresos = ingresos;
    }

    public double getEgresos() {
        return egresos;
    }

    public void setEgresos(double egresos) {
        this.egresos = egresos;
    }

    public String validarCedula() {
        if (cliente.getCedula() != null) {
            Cliente cli = gestionUsuarios.buscarCliente(cliente.getCedula());
            if (cli != null) {
                return "Este cliente ya se encuentra registrado";
            }
            try {
                boolean verificar = gestionUsuarios.validadorDeCedula(cliente.getCedula());
                if (verificar) {
                    return "Cedula Valida";
                } else if (verificar == false) {
                    return "Cedula Incorrecta";
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return " ";

    }

    public String generarNumeroCuenta() {
        this.numeroCuenta = gestionUsuarios.generarNumeroDeCuenta();
        return numeroCuenta;
    }

    public String getSaldoCuenta() {
        return saldoCuenta;
    }

    public void setSaldoCuenta(String saldoCuenta) {
        this.saldoCuenta = saldoCuenta;
    }

    public String getMensajeGarante() {
        return mensajeGarante;
    }

    public void setMensajeGarante(String mensajeGarante) {
        this.mensajeGarante = mensajeGarante;
    }

    public void handleClose(CloseEvent event) {
        addMessage(event.getComponent().getId() + " closed", "So you don't like nature?");
    }

    public void handleMove(MoveEvent event) {
        event.setTop(500);
        addMessage(event.getComponent().getId() + " moved", "Left: " + event.getLeft() + ", Top: " + event.getTop());
    }

    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void listarClientes() {
        lstClientes = gestionUsuarios.listaClientes();
    }

    public String obtenerFecha(Date fecha) {
        DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return hourdateFormat.format(fecha);
    }

    public SolicitudPoliza getSolicitudPoliza() {
        return solicitudPoliza;
    }

    public void setSolicitudPoliza(SolicitudPoliza solicitudPoliza) {
        this.solicitudPoliza = solicitudPoliza;
    }

    public List<Poliza> getLstListaPolizaAprobados() {
        return lstPolizasAprobados;
    }
    public void setLstListaCreditosAprobados(List<Poliza> lstListaPolizaAprobados) {
        this.lstPolizasAprobados = lstListaPolizaAprobados;
    }

    public List<SesionCliente> cargarSesiones() {
        List<SesionCliente> lis = gestionUsuarios.obtenerSesionesCliente(cedulaParametro);
        if (lis != null) {
            lstSesionesCliente = lis;
            return lstSesionesCliente;
        }
        return null;
    }

    public String consultarTransacciones() {
        return "ConsultaTransacciones";
    }

    public void validarFechas() throws Exception {
        if (this.fechaInicio != null && this.fechaFinal != null) {
            /*
			 * System.out.println(fechaInicio.getClass()); DateFormat hourdateFormat = new
			 * SimpleDateFormat("dd/MM/yyyy"); String d =
			 * hourdateFormat.format(fechaInicio);
			 * System.out.println(buscarCuentaDeAhorro.getNumeroCuentaDeAhorro());
			 * System.out.println(d +"***"+fechaFinal);
             */
            DateFormat hourdateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String inicioF = hourdateFormat.format(fechaInicio);
            String finalF = hourdateFormat.format(fechaFinal);
            List<Transaccion> listaTrans = gestionUsuarios.obtenerTransaccionesFechaHora(cedulaParametro, inicioF,
                    finalF);
            lstTransacciones = listaTrans;
            System.out.println("H" + lstTransacciones.size());
            System.out.println(cedulaParametro);
            System.out.println(new Date());
        }
    }

    public void obtenerTransaccionesInicioFinal() {
        /*
		 * lstTransacciones =
		 * gestionUsuarios.obtenerTransaccionesFechaHora(buscarCuentaDeAhorro.getCliente
		 * ().getCedula(), fechaInicio, fechaFinal);
         */
        System.out.println("Este es el tipo de transaccion : " + tipoTransaccion);

    }

    public void ultimosDias() {
        Calendar c = Calendar.getInstance();
        fechaFinal = c.getTime();
        c.add(Calendar.DATE, -30);
        fechaInicio = c.getTime();
        DateFormat hourdateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String inicioF = hourdateFormat.format(fechaInicio);
        String finalF = hourdateFormat.format(fechaFinal);
        List<Transaccion> listaTrans = gestionUsuarios.obtenerTransaccionesFechaHora(cedulaParametro, inicioF, finalF);
        lstTransacciones = listaTrans;
        System.out.println(lstTransacciones.size());
        System.out.println(cedulaParametro);
    }

    public void validarFechas2() throws Exception {
        System.out.println(tipoTransaccion);

        if (this.fechaInicio != null && this.fechaFinal != null) {

            if (errorFechas() == null) {
                fechasInvalidas = errorFechas();
                DateFormat hourdateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String inicioF = hourdateFormat.format(fechaInicio);
                String finalF = hourdateFormat.format(fechaFinal);
                List<Transaccion> listaTrans = gestionUsuarios.obtenerTransaccionesFechaHora(cedulaParametro, inicioF,
                        finalF);

                if (tipoTransaccion != null) {
                    if (tipoTransaccion.equals("Todos")) {
                        lstTransacciones = listaTrans;
                    } else if (tipoTransaccion.equals("Depositos")) {
                        lstTransacciones = new ArrayList<Transaccion>();
                        for (Transaccion transaccion : listaTrans) {
                            if (transaccion.getTipo().equals("deposito")) {
                                lstTransacciones.add(transaccion);
                            }
                        }
                    } else {
                        lstTransacciones = new ArrayList<Transaccion>();
                        for (Transaccion transaccion : listaTrans) {
                            if (transaccion.getTipo().equals("retiro")) {
                                lstTransacciones.add(transaccion);
                            }
                        }
                    }
                }
            } else {
                fechasInvalidas = errorFechas();
                lstTransacciones.removeAll(lstTransacciones);
            }

            /*
			 * System.out.println("H"+lstTransacciones.size());
			 * System.out.println(cedulaParametro); System.out.println(new Date());
             */
        }

        System.out.println("LISTA DE TRANSACCION SIZE :   " + lstTransacciones.size());
    }

    public String errorFechas() {
        /*
		 * System.out.println(fechaInicio.after(fechaFinal));;
		 * System.out.println("Fecha inicio: "+this.fechaInicio);
		 * System.out.println("Fecha final: "+this.fechaFinal);
		 * if(fechaInicio.after(fechaFinal)) {
		 * System.out.println("ENTROOOOO VALIDACION"); fechasInvalidas = false; return
		 * "No se puede consultar entre estas fechas"; }else { fechasInvalidas = true; }
		 * return "si";
         */
        Date fechaInicioDate = this.fechaInicio;
        Date fechaFinDate = this.fechaFinal;
        System.out.println("Inicial: " + fechaInicioDate);
        System.out.println("Final: " + fechaFinDate);
        if (fechaInicioDate.after(fechaFinDate)) {
            return "Fecha inicio mayor";
        }
        return null;
    }

    public String confirmarTasaPago(double ingresos, double egresos) {
        if (egresos > ingresos) {
            return "Los egresos no debe ser mayor a los ingresos";
        }
        return null;
    }

    public String obtenerTasa() {
        double meses = solicitudPoliza.getMesesPoliza();
        if (meses >= 0 && meses <= 29) {
            tasa = 0.0;
        } else if (meses >= 30 && meses <= 59) {
            tasa = 5.5;
        } else if (meses >= 60 && meses <= 89) {
            tasa = 5.75;
        } else if (meses >= 90 && meses <= 179) {
            tasa = 6.25;
        } else if (meses >= 180 && meses <= 269) {
            tasa = 7.00;
        } else if (meses >= 270 && meses <= 359) {
            tasa = 7.50;
        } else {
            tasa = 8.50;
        }
        solicitudPoliza.setTasaPoliza(tasa);

        return null;
    }

    public void archivo1(FileUploadEvent event) throws IOException {
        FacesMessage msg = new FacesMessage("Successful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        arCedula = event.getFile().getInputStream();
    }

    public void archivo2(FileUploadEvent event) throws IOException {
        FacesMessage msg = new FacesMessage("Successful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        arPlanillaServicios = event.getFile().getInputStream();
    }

    public void cambioVar(int cod) {
        codigoCredito = cod;
        editable = true;
    }

    public String crearSolicitudPoliza() throws IOException {
        System.out.println("ENTRO EN LA SOLICITUD");
        solicitudPoliza.setClientePoliza(gestionUsuarios.buscarCliente(cedulaParametro));
        solicitudPoliza.setTasaPoliza(tasa);
        solicitudPoliza.setEstado("Solicitando");
        solicitudPoliza.setArCedula(gestionUsuarios.toByteArray(arCedula));
        solicitudPoliza.setArPlanillaServicios(gestionUsuarios.toByteArray(arPlanillaServicios));
        if (gestionUsuarios.verificarSolicitudSolicitando(cedulaParametro)) {
            gestionUsuarios.guardarSolicitudPoliza(solicitudPoliza);
            addMessage("Confirmacion", "Solicitud Guardada");
        } else {
            addMessage("Atencion", "Usted ya ha enviado una solicitud de poliza para su aprovacion");
        }
        solicitudPoliza = new SolicitudPoliza();
        return "SolicitudCredito";
    }

    public void polizasAprobados(String cedula) {
        System.out.println("ENTRO EN ESTE PINCHE METODO" + cedulaParametro);
        lstPolizasAprobados = gestionUsuarios.polizasAprobados(cedula);
    }

}
