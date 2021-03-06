package ec.edu.ups.vista;

import ec.edu.ups.modelo.Cliente;
import ec.edu.ups.modelo.CuentaDeAhorro;
import ec.edu.ups.modelo.Transaccion;
import ec.edu.ups.negocio.GestionUsuarioLocal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.event.CloseEvent;
import org.primefaces.event.MoveEvent;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.pie.PieChartDataSet;
import org.primefaces.model.charts.pie.PieChartModel;

/**
 *
 * @author Starman
 */
@ManagedBean
@SessionScoped
public class CajeroBean {

    @Inject
    private GestionUsuarioLocal clienteON;

    private Double monto;

    private Cliente cliente;

    private List<Transaccion> listaTra;

    private boolean editable;

    private boolean editable2;

    private String tipoTransaccion;

    private String cedulaAux;

    private int codigoAux;

    private int codigoAux2;

    private int codigoAux3;

    private Transaccion transaccionAux;

    private PieChartModel pieModel;

    private boolean grafica;

    @PostConstruct
    public void init() {

        transaccionAux = new Transaccion();
        cliente = new Cliente();
        grafica = false;
    }

    public GestionUsuarioLocal getClienteON() {
        return clienteON;
    }

    public void setClienteON(GestionUsuarioLocal clienteON) {
        this.clienteON = clienteON;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Transaccion> getListaTra() {
        return listaTra;
    }

    public void setListaTra(List<Transaccion> listaTra) {
        this.listaTra = listaTra;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public String getTipoTransaccion() {
        return tipoTransaccion;
    }

    public void setTipoTransaccion(String tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    public String getCedulaAux() {
        return cedulaAux;
    }

    public void setCedulaAux(String cedulaAux) {
        this.cedulaAux = cedulaAux;
    }

    public boolean isEditable2() {
        return editable2;
    }

    public void setEditable2(boolean editable2) {
        this.editable2 = editable2;
    }

    public int getCodigoAux() {
        return codigoAux;
    }

    public void setCodigoAux(int codigoAux) {
        this.codigoAux = codigoAux;
    }

    public Transaccion getTransaccionAux() {
        return transaccionAux;
    }

    public void setTransaccionAux(Transaccion transaccionAux) {
        this.transaccionAux = transaccionAux;
    }

    public PieChartModel getPieModel() {
        return pieModel;
    }

    public void setPieModel(PieChartModel pieModel) {
        this.pieModel = pieModel;
    }

    public int getCodigoAux2() {
        return codigoAux2;
    }

    public void setCodigoAux2(int codigoAux2) {
        this.codigoAux2 = codigoAux2;
    }

    public int getCodigoAux3() {
        return codigoAux3;
    }

    public void setCodigoAux3(int codigoAux3) {
        this.codigoAux3 = codigoAux3;
    }

    public boolean isGrafica() {
        return grafica;
    }

    public void setGrafica(boolean grafica) {
        this.grafica = grafica;
    }

    public String valCedula() {
        if (cliente.getCedula() != null) {
            try {
                boolean c = clienteON.validadorDeCedula(cliente.getCedula());
                if (true) {
                    Cliente usuarioRegistrado = clienteON.buscarCliente(cliente.getCedula());
                    if (usuarioRegistrado != null) {
                        CuentaDeAhorro cuen = clienteON.buscarCuentaDeAhorroCliente(usuarioRegistrado.getCedula());
                        System.out.println("Registrado");
                        String l = (String) (usuarioRegistrado.getNombre() + "    " + usuarioRegistrado.getApellido());
                        editable = false;
                        cargarTransacciones();
                        return l;
                    } else {
                        String kl = "Cliente No registrado en el sistema";
                        editable = false;
                        cargarTransacciones();
                        return kl;
                    }
                } else {
                    String ml = "Cedula Incorrecta";
                    editable = false;
                    cargarTransacciones();
                    return ml;
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                //e.printStackTrace();
            }

        }
        return " ";
    }

    public String numCuenta() {

        try {
            boolean c;
            c = clienteON.validadorDeCedula(cliente.getCedula());
            if (c) {
                Cliente usuarioRegistrado = clienteON.buscarCliente(cliente.getCedula());
                if (usuarioRegistrado != null) {
                    CuentaDeAhorro cuen = clienteON.buscarCuentaDeAhorroCliente(usuarioRegistrado.getCedula());
                    return cuen.getNumeroCuentaDeAhorro();
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
        return null;

    }

    public String valMonto() {
        try {
            if (cliente.getCedula() != null) {
                Cliente usuarioRegistrado = clienteON.buscarCliente(cliente.getCedula());
                if (usuarioRegistrado != null) {
                    CuentaDeAhorro cl = clienteON.buscarCuentaDeAhorroCliente(cliente.getCedula());
                    String l = String.valueOf(cl.getSaldoCuentaDeAhorro());
                    if (tipoTransaccion.equalsIgnoreCase("retiro") && monto == null) {
                        return l;
                    } else if (tipoTransaccion.equalsIgnoreCase("retiro") && cl.getSaldoCuentaDeAhorro() < monto) {
                        String ms = "La cuenta no cuenta con el saldo suficiente, Su saldo es: "
                                + cl.getSaldoCuentaDeAhorro();
                        return ms;

                    } else {
                        return l;
                    }

                }
            }

        } catch (Exception e) {
            // TODO: handle exception
        }

        return " ";
    }

    public String registrar() {
        CuentaDeAhorro clp = clienteON.buscarCuentaDeAhorroCliente(cliente.getCedula());
        if (tipoTransaccion.equalsIgnoreCase("deposito")) {
            Double nvmonto = clp.getSaldoCuentaDeAhorro() + monto;
            clp.setSaldoCuentaDeAhorro(nvmonto);
            clienteON.actualizarCuentaDeAhorro(clp);
            Transaccion t = new Transaccion();
            t.setCliente(clp.getCliente());
            t.setMonto(monto);
            t.setFecha(new Date());
            t.setTipo("deposito");
            t.setSaldoCuenta(nvmonto);
            try {
                clienteON.guardarTransaccion(t);
                addMessage("Confirmacion", "Transaccion Guardada");
                editable = false;
                listaTra = new ArrayList<Transaccion>();

            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.getMessage();
            }
            try {
                FacesContext contex = FacesContext.getCurrentInstance();
                contex.getExternalContext().redirect("PaginaCajero.xhtml");
            } catch (Exception e) {
            }
        } else if (tipoTransaccion.equalsIgnoreCase("retiro") && monto <= clp.getSaldoCuentaDeAhorro()) {
            Double nvmonto2 = clp.getSaldoCuentaDeAhorro() - monto;
            clp.setSaldoCuentaDeAhorro(nvmonto2);
            clienteON.actualizarCuentaDeAhorro(clp);
            Transaccion t2 = new Transaccion();
            t2.setCliente(clp.getCliente());
            t2.setMonto(monto);
            t2.setFecha(new Date());
            t2.setTipo("retiro");
            t2.setSaldoCuenta(nvmonto2);
            try {

                clienteON.guardarTransaccion(t2);
                addMessage("Confirmacion", "Transaccion Guardada");
                editable = false;
                listaTra = new ArrayList<Transaccion>();

            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.getMessage();
            }
            try {

                FacesContext contex = FacesContext.getCurrentInstance();
                contex.getExternalContext().redirect("PaginaCajero.xhtml");
            } catch (Exception e) {
            }
        }
        return "PaginaCajero";
    }

    public String cargarTransacciones() {
        List<Transaccion> lis = clienteON.listadeTransacciones(cliente.getCedula());
        if (lis != null) {
            listaTra = lis;
            editable = true;
            editable2 = false;
        }
        return null;
    }

    public void activar() {
        try {
            editable = true;
            editable2 = false;

        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    public void cambioVar(int cod) {
        codigoAux = cod;
        editable = false;
        editable2 = true;

    }

    public void addMessage(String summary, String detail) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail));
    }

    public void handleClose(CloseEvent event) {
        addMessage(event.getComponent().getId() + " closed", "So you don't like nature?");
    }

    public void handleMove(MoveEvent event) {
        event.setTop(500);
        addMessage(event.getComponent().getId() + " moved", "Left: " + event.getLeft() + ", Top: " + event.getTop());
    }

}
