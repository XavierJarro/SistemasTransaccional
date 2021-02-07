package ec.edu.ups.vista;

import ec.edu.ups.modelo.CuentaDeAhorro;
import ec.edu.ups.modelo.DetallePoliza;
import ec.edu.ups.modelo.Empleado;
import ec.edu.ups.modelo.Poliza;
import ec.edu.ups.modelo.SolicitudPoliza;
import ec.edu.ups.modelo.Transaccion;
import ec.edu.ups.negocio.GestionUsuarioLocal;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Starman
 */
@ManagedBean
@SessionScoped
public class LoginBean {

    @Inject
    private GestionUsuarioLocal empleadoON;

    private String usuario;
    private String contrasena;
    private List<SolicitudPoliza> solicitudes;
    private SolicitudPoliza solicitudDePoliza;
    private SolicitudPoliza solicitudPolizaAux;
    private Empleado empleado;
    private boolean editable = false;
    private boolean editabledos = false;
    private String motivo;
    private String tipoC;

    public LoginBean() {
        init();
    }

    public void init() {
        solicitudes = new ArrayList<SolicitudPoliza>();
        //loadDataSol();
        empleado = new Empleado();
        empleado = new Empleado();

    }

    public GestionUsuarioLocal getEmpleadoON() {
        return empleadoON;
    }

    public void setEmpleadoON(GestionUsuarioLocal empleadoON) {
        this.empleadoON = empleadoON;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public List<SolicitudPoliza> getSolicitudes() {
        return solicitudes;
    }

    public void setSolicitudes(List<SolicitudPoliza> solicitudes) {
        this.solicitudes = solicitudes;
    }

    public SolicitudPoliza getSolicitudDePoliza() {
        return solicitudDePoliza;
    }

    public void setSolicitudDePoliza(SolicitudPoliza solicitudDePoliza) {
        this.solicitudDePoliza = solicitudDePoliza;
    }

    public SolicitudPoliza getSolicitudPolizaAux() {
        return solicitudPolizaAux;
    }

    public void setSolicitudPolizaAux(SolicitudPoliza solicitudPolizaAux) {
        this.solicitudPolizaAux = solicitudPolizaAux;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean isEditabledos() {
        return editabledos;
    }

    public void setEditabledos(boolean editabledos) {
        this.editabledos = editabledos;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getTipoC() {
        return tipoC;
    }

    public void setTipoC(String tipoC) {
        this.tipoC = tipoC;
    }

    public String validarUsuario() {
        Empleado emp;
        try {
            emp = empleadoON.usuario(usuario, contrasena);
            System.out.println("***************" + emp.getNombre());
            empleado = emp;
            if (emp != null && emp.getRol().equalsIgnoreCase("Cajero")) {
                try {
                    addMessage("OK", "Ingreso");

                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("empleado", emp);
                    FacesContext contex = FacesContext.getCurrentInstance();

                    contex.getExternalContext().redirect("PaginaCajero.xhtml");
                } catch (Exception e) {
                }
            } else if (emp != null && emp.getRol().equalsIgnoreCase("AsistenteDeCapacitaciones")) {
                try {
                    loadDataSol();
                    FacesContext contex = FacesContext.getCurrentInstance();
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("empleado", emp);
                    contex.getExternalContext().redirect("AsistenteCapacitaciones.xhtml");
                } catch (Exception e) {
                }
            } else if (emp != null && emp.getRol().equalsIgnoreCase("Admin")) {
                try {
                    FacesContext contex = FacesContext.getCurrentInstance();
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("empleado", emp);
                    contex.getExternalContext().redirect("Admin.xhtml");
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
            addMessage("ERROR", "NO SE PUEDO INGRESAR, REVISE USUARIO CONTRASEÑA");
            return "InicioUsuarios";
        }

        return null;
    }

    public void loadDataSol() {
        solicitudes = new ArrayList<SolicitudPoliza>();
        System.out.println("ENTRAAAAAAAA EN LOADDATASOL");
        List<SolicitudPoliza> soli = empleadoON.listadoSolicitudPolizas();
        System.out.println(soli.size());
        List<SolicitudPoliza> actual = new ArrayList<SolicitudPoliza>();
        for (SolicitudPoliza sol : soli) {
            if (sol.getEstado().equals("Solicitando")) {
                actual.add(sol);
            }
        }
        solicitudes = actual;
    }

    public List<SolicitudPoliza> loTTT() {
        System.out.println("ENTRAAAAAAAA EN LOADDATASOL");
        List<SolicitudPoliza> soli = empleadoON.listadoSolicitudPolizas();
        System.out.println(soli.size());
        List<SolicitudPoliza> actual = new ArrayList<SolicitudPoliza>();
        for (SolicitudPoliza sol : soli) {
            if (sol.getEstado().equals("Solicitando")) {
                actual.add(sol);
            }
        }
        return actual;
    }

    /* public List<SolicitudPoliza> loadDataSolAR(String apr) {
        System.out.println("ENTRAAAAAAAA APROBADOS RECHAZADOS");
        List<SolicitudPoliza> soli = empleadoON.listadoSolicitudPolizas();
        System.out.println(soli.size());
        List<SolicitudPoliza> actual = new ArrayList<SolicitudPoliza>();
        List<SolicitudPoliza> actual2 = new ArrayList<SolicitudPoliza>();
        for (SolicitudPoliza sol : soli) {
            if (sol.getTipoCliente().equals("1")) {
                actual.add(sol);
            } else if (sol.getTipoCliente().equals("2")) {
                actual2.add(sol);
            }
        }

        if (apr.equals("Ap")) {
            return actual;
        } else if (apr.equals("Rch")) {
            return actual2;
        }
        return null;
    }*/
    public String cargarSol(int cod) {
        editable = true;

        for (SolicitudPoliza sol : solicitudes) {
            if (sol.getCodigoPoliza() == cod) {
                solicitudDePoliza = sol;
            }
        }
        return null;
    }

    public String aprobar(int cod) {
        for (SolicitudPoliza sol : solicitudes) {
            if (sol.getCodigoPoliza() == cod && sol.getEstado().equalsIgnoreCase("Solicitando")) {

                Poliza poliza = new Poliza();
                double interes = 0;
                poliza.setEstado("Aprobado");
                System.out.println("el montoooooooooooooooooooooooooooooooooooo" + sol.getMontoPoliza());
                poliza.setMonto(sol.getMontoPoliza());
                poliza.setTasa(sol.getTasaPoliza());
                interes = sol.getMontoPoliza() * (sol.getTasaPoliza() / 100);
                poliza.setInteres(interes);
                poliza.setFechaRegistro(new Date());
                poliza.setJefeC(empleado);
                poliza.setSolicitud(sol);
                List<DetallePoliza> li = empleadoON.crearTabla(sol.getMesesPoliza(), interes);
                System.out.println(li.toString());
                poliza.setFechaVencimiento(li.get(li.size() - 1).getFecha());
                poliza.setDetalles(li);

                empleadoON.guardarPoliza(poliza);
                //empleadoON.aprobarPoliza(poliza, sol.getClientePoliza());
                solicitudDePoliza.setEstado("Aprobado");
                empleadoON.actualizarSolicitudPoliza(solicitudDePoliza);

                CuentaDeAhorro ccv = empleadoON.buscarCuentaDeAhorroCliente(sol.getClientePoliza().getCedula());
                Double nvmonto2 = ccv.getSaldoCuentaDeAhorro() - sol.getMontoPoliza();
                ccv.setSaldoCuentaDeAhorro(nvmonto2);
                empleadoON.actualizarCuentaDeAhorro(ccv);

                Transaccion t2 = new Transaccion();
                t2.setCliente(ccv.getCliente());
                t2.setMonto(sol.getMontoPoliza());
                t2.setFecha(new Date());
                t2.setTipo("poliza");
                t2.setSaldoCuenta(nvmonto2);
                try {
                    empleadoON.guardarTransaccion(t2);

                } catch (Exception e1) {
                    e1.getMessage();
                }

                solicitudDePoliza = new SolicitudPoliza();
                editable = false;
                editabledos = false;
                loadDataSol();
            }
        }

        return "AsistenteCapacitaciones";
    }

    public String rechazar() {
        solicitudDePoliza.setEstado("Rechazado");
        empleadoON.actualizarSolicitudPoliza(solicitudDePoliza);
        System.out.println(motivo);
        empleadoON.rechazarPoliza(solicitudDePoliza.getClientePoliza(), motivo);
        //solicitudDePoliza = new SolicitudPoliza();
        editable = false;
        editabledos = false;
        loadDataSol();
        return "AsistenteCapacitaciones";
    }

    public void cambio() {
        editable = false;
        editabledos = true;
    }

    public void ver(String tipo) throws IOException {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
        File file = File.createTempFile("archivoTemp", ".pdf");
        try (FileOutputStream fos = new FileOutputStream(file)) {
            if (tipo.equalsIgnoreCase("cedula")) {
                fos.write(solicitudDePoliza.getArCedula());
            } else if (tipo.equalsIgnoreCase("planilla")) {
                fos.write(solicitudDePoliza.getArPlanillaServicios());
            }
        }
        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        try {
            input = new BufferedInputStream(new FileInputStream(file), 10240);
            response.reset();
            response.setHeader("Content-Type", "application/pdf");
            response.setHeader("Content-Length", String.valueOf(file.length()));
            response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
            output = new BufferedOutputStream(response.getOutputStream(), 10240);
            byte[] buffer = new byte[10240];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            output.flush();
        } finally {

        }
        facesContext.responseComplete();
    }

    public void ver2(String tipo) throws IOException {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
        File file = File.createTempFile("archivoTemp", ".pdf");
        try (FileOutputStream fos = new FileOutputStream(file)) {
            if (tipo.equalsIgnoreCase("cedula")) {
                fos.write(solicitudDePoliza.getArCedula());
            } else if (tipo.equalsIgnoreCase("planilla")) {
                fos.write(solicitudDePoliza.getArPlanillaServicios());
            }

        }
        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        try {
            input = new BufferedInputStream(new FileInputStream(file), 10240);
            response.reset();
            response.setHeader("Content-Type", "application/pdf");
            response.setHeader("Content-Length", String.valueOf(file.length()));
            response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
            output = new BufferedOutputStream(response.getOutputStream(), 10240);
            byte[] buffer = new byte[10240];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            output.flush();
        } finally {

        }
        facesContext.responseComplete();
    }

    public void addMessage(String summary, String detail) {
        System.out.println(summary + "mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmkkk" + detail);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail));
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "InicioUsuarios?faces-redirect=true";
    }

}
