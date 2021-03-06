package ec.edu.ups.vista;

import ec.edu.ups.modelo.Empleado;
import ec.edu.ups.negocio.GestionUsuarioLocal;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.event.CloseEvent;
import org.primefaces.event.MoveEvent;

@ManagedBean
@SessionScoped
public class EmpleadosBean {

    @Inject
    private GestionUsuarioLocal empleadoON;

    private Empleado empleado;

    private boolean ced;

    private List<Empleado> listaEmpleados;

    private String tipoEmpleado;

    //private List<SolicitudDeCredito> solicitudes;
    //private SolicitudDeCredito solicitudDeCredito;
    private boolean editable = false;

    private boolean editabledos = false;

    private String motivo;

    @PostConstruct
    public void init() {
        empleado = new Empleado();
        //solicitudDeCredito = new SolicitudDeCredito();
        loadData();
        //loadDataSol();
    }

    public GestionUsuarioLocal getEmpleadoON() {
        return empleadoON;
    }

    public void setEmpleadoON(GestionUsuarioLocal empleadoON) {
        this.empleadoON = empleadoON;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public boolean isCed() {
        return ced;
    }

    public void setCed(boolean ced) {
        this.ced = ced;
    }

    public List<Empleado> getListaEmpleados() {
        return listaEmpleados;
    }

    public void setListaEmpleados(List<Empleado> listaEmpleados) {
        this.listaEmpleados = listaEmpleados;
    }

    public String getTipoEmpleado() {
        return tipoEmpleado;
    }

    public void setTipoEmpleado(String tipoEmpleado) {
        this.tipoEmpleado = tipoEmpleado;
    }

    public boolean getEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public boolean getEditabledos() {
        return editabledos;
    }

    public void setEditabledos(boolean editabledos) {
        this.editabledos = editabledos;
    }

    public String guardarDatos() {

        System.out.println(this.empleado.getCedula() + "   " + this.empleado.getNombre() + tipoEmpleado);

        try {
            if (tipoEmpleado.equalsIgnoreCase("cajero")) {
                empleado.setRol("Cajero");
                empleadoON.guardarEmpleado(empleado);
                addMessage("Confirmacion", "Empleado Guardado");

            } else if (tipoEmpleado.equalsIgnoreCase("asistenteDeCapacitaciones")) {
                empleado.setRol("AsistenteDeCapacitaciones");
                empleadoON.guardarEmpleado(empleado);
                addMessage("Confirmacion", "Empleado Guardado");
            } else if (tipoEmpleado.equalsIgnoreCase("administrador")) {
                empleado.setRol("Admin");
                empleadoON.guardarEmpleado(empleado);
                addMessage("Confirmacion", "Empleado Guardado");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {

            FacesContext contex = FacesContext.getCurrentInstance();
            contex.getExternalContext().redirect("Lista-Empleados.xhtml");
        } catch (Exception e) {
        }
        return null;

    }

    public String valCedula() {
        System.out.println("*-------*" + empleado.getCedula());
        if (empleado.getCedula() != null) {
            Empleado usuarioRegistrado = empleadoON.usuarioRegistrado(empleado.getCedula());
            if (usuarioRegistrado != null) {
                System.out.println("Registrado");
                return "Empleado REGISTRADO";
            }
            try {
                ced = empleadoON.validadorDeCedula(empleado.getCedula());
                System.out.println(ced);
                if (ced) {
                    return "Cedula Valida";
                } else if (ced == false) {
                    return "Cedula Incorrecta";
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return " ";
    }

    public void loadData() {
        listaEmpleados = empleadoON.listadoEmpleados();
    }

    /*public void loadDataSol() {
		solicitudes = empleadoON.listadoSolicitudDeCreditos();
	}*/

 /*public String cargarSol(int cod) {
		editable = true;
		
		
		for (SolicitudDeCredito sol : solicitudes) {
			if (sol.getCodigoCredito() == cod) {
				solicitudDeCredito = sol;
			}
		}
		return null;
	}*/
 /*
	public String aprobar(int cod) {	
		for (SolicitudDeCredito sol : solicitudes) {
			if (sol.getCodigoCredito() == cod && sol.getEstadoCredito().equalsIgnoreCase("Solicitando") ) {
				solicitudDeCredito.setEstadoCredito("Aprobado");
				empleadoON.actualizarSolicitudCredito(solicitudDeCredito);
				Credito credito = new Credito();
				credito.setFechaRegistro(new Date());
				credito.setInteres(12);
				credito.setMonto(sol.getMontoCredito());
				//credito.setJefeC();
				credito.setEstado("Pendiente");
				credito.setSolicitud(sol);
				List<DetalleCredito> li = empleadoON.crearTablaAmortizacion(Integer.parseInt(sol.getMesesCredito()), sol.getMontoCredito(), 12.00);
				System.out.println(li.toString());
				credito.setDetalles(li);
				empleadoON.guardarCredito(credito);
				empleadoON.aprobarCredito(credito, sol.getClienteCredito());
				
			}
		}
		
		return "PaginaJefeCredito";
	}*/

 /*public String rechazar() {
		solicitudDeCredito.setEstadoCredito("Rechazado");
		
		empleadoON.actualizarSolicitudCredito(solicitudDeCredito);
		System.out.println(motivo);
		//System.out.println(solicitudDeCredito.getCodigoCredito());
		return "PaginaJefeCredito";
	}*/
    public void cambio() {
        editable = false;
        editabledos = true;
    }

    /*public void ver(String tipo) throws IOException {
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

		File file = File.createTempFile("archivoTemp", ".pdf");
		try (FileOutputStream fos = new FileOutputStream(file)) {
			if (tipo.equalsIgnoreCase("cedula")) {
				fos.write(solicitudDeCredito.getArCedula());
			}else if (tipo.equalsIgnoreCase("planilla")) {
				fos.write(solicitudDeCredito.getArPlanillaServicios());
			}else if (tipo.equalsIgnoreCase("rol")) {
				fos.write(solicitudDeCredito.getArRolDePagos());
			}
			
		}
		BufferedInputStream input = null;
		BufferedOutputStream output = null;

		try {
			// Open file.
			input = new BufferedInputStream(new FileInputStream(file), 10240);

			// Init servlet response.
			response.reset();
			response.setHeader("Content-Type", "application/pdf");
			response.setHeader("Content-Length", String.valueOf(file.length()));
			response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
			output = new BufferedOutputStream(response.getOutputStream(), 10240);

			// Write file contents to response.
			byte[] buffer = new byte [10240];
			int length;
			while ((length = input.read(buffer)) > 0) {
				output.write(buffer, 0, length);
			}

			// Finalize task.
			output.flush();
		} finally {

		}

		// Inform JSF that it doesn't need to handle response.
		// This is very important, otherwise you will get the following exception in the
		// logs:
		// java.lang.IllegalStateException: Cannot forward after response has been
		// committed.
		facesContext.responseComplete();
	}*/
    public void addMessage(String summary, String detail) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail));
        //FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        //FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void handleClose(CloseEvent event) {
        addMessage(event.getComponent().getId() + " closed", "So you don't like nature?");
    }

    public void handleMove(MoveEvent event) {
        event.setTop(500);
        addMessage(event.getComponent().getId() + " moved", "Left: " + event.getLeft() + ", Top: " + event.getTop());
    }

}
