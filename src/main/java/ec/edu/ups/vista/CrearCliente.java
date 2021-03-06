package ec.edu.ups.vista;

import ec.edu.ups.modelo.Cliente;
import ec.edu.ups.modelo.CuentaDeAhorro;
import ec.edu.ups.modelo.Transaccion;
import ec.edu.ups.negocio.GestionUsuarioLocal;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.Part;

import org.primefaces.event.CloseEvent;
import org.primefaces.event.MoveEvent;
import org.primefaces.model.file.UploadedFile;

/**
 *
 * @author Starman
 */
@ManagedBean
@SessionScoped
public class CrearCliente {
    //Atributos de la clase

    @Inject
    private GestionUsuarioLocal gestionUsuarios;
    private Cliente cliente;
    private String numeroCuenta;
    private String saldoCuenta;
    private CuentaDeAhorro cuentaDeAhorro;
    private List<Cliente> lstClientes;
    //private SolicitudDeCredito solicitudDeCredito;
    private Part arCedula;
    private Part arPlanillaServicios;
    private Part arRolDePagos;

    /**
     * Metodo que permite inicializar atributos y metodos al momento que se
     * llama a esta clase
     */
    @PostConstruct
    private void iniciar() {
        cliente = new Cliente();
        cliente.setEstado("D");
        cliente.setContador(3);
        cuentaDeAhorro = new CuentaDeAhorro();
    }

    public GestionUsuarioLocal getGestionUsuarios() {
        return gestionUsuarios;
    }

    public void setGestionUsuarios(GestionUsuarioLocal gestionUsuarios) {
        this.gestionUsuarios = gestionUsuarios;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getSaldoCuenta() {
        return saldoCuenta;
    }

    public void setSaldoCuenta(String saldoCuenta) {
        this.saldoCuenta = saldoCuenta;
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

    public List<Cliente> getLstClientes() {
        return lstClientes;
    }

    public void setLstClientes(List<Cliente> lstClientes) {
        this.lstClientes = lstClientes;
    }

    public Part getArCedula() {
        return arCedula;
    }

    public void setArCedula(Part arCedula) {
        this.arCedula = arCedula;
    }

    public Part getArPlanillaServicios() {
        return arPlanillaServicios;
    }

    public void setArPlanillaServicios(Part arPlanillaServicios) {
        this.arPlanillaServicios = arPlanillaServicios;
    }

    public Part getArRolDePagos() {
        return arRolDePagos;
    }

    public void setArRolDePagos(Part arRolDePagos) {
        this.arRolDePagos = arRolDePagos;
    }

    public void handleClose(CloseEvent event) {
        addMessage(event.getComponent().getId() + " closed", "So you don't like nature?");
    }

    public void handleMove(MoveEvent event) {
        event.setTop(500);
        addMessage(event.getComponent().getId() + " moved", "Left: " + event.getLeft() + ", Top: " + event.getTop());
    }

    public void addMessage(String summary, String detail) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail));
        //FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        //FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public String crearCliente() {
        cliente.setEstado("D");
        cliente.setContador(3);
        try {

            gestionUsuarios.guardarCliente(cliente);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
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

    public String crearCuenta() {
        try {
            cuentaDeAhorro.setNumeroCuentaDeAhorro(numeroCuenta);
            cuentaDeAhorro.setFechaDeRegistro(new Date());
            cuentaDeAhorro.setCliente(cliente);
            cuentaDeAhorro.setSaldoCuentaDeAhorro(Double.parseDouble(saldoCuenta));
            gestionUsuarios.guardarCuentaDeAhorros(cuentaDeAhorro);
            Transaccion transaccion = new Transaccion();
            transaccion.setFecha(new Date());
            transaccion.setMonto(cuentaDeAhorro.getSaldoCuentaDeAhorro());
            transaccion.setTipo("deposito");
            transaccion.setCliente(cliente);
            transaccion.setSaldoCuenta(cuentaDeAhorro.getSaldoCuentaDeAhorro());
            gestionUsuarios.guardarTransaccion(transaccion);
            addMessage("Confirmacion", "Cliente Guardado");
            cliente = new Cliente();
            try {
                FacesContext contex = FacesContext.getCurrentInstance();
                contex.getExternalContext().redirect("CrearCliente.xhtml");
            } catch (Exception t) {

            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public List<Cliente> obtenerClientes() {
        try {
            List<Cliente> clis = gestionUsuarios.listaClientes();
            System.out.println(clis.size());
            return gestionUsuarios.listaClientes();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }

    public List<Cliente> obtenerClientesBloqueados() {
        try {
            List<Cliente> clis = new ArrayList<Cliente>();
            for (Cliente cli : gestionUsuarios.listaClientes()) {
                if (cli.getEstado().equalsIgnoreCase("B")) {
                    clis.add(cli);
                }
            }
            return clis;
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }

    public String desbloquearCliente(String cedula) {
        gestionUsuarios.desbloquear(cedula);
        return null;

    }

}
