package ec.edu.ups.vista;

import ec.edu.ups.modelo.Cliente;
import ec.edu.ups.modelo.SesionCliente;
import ec.edu.ups.negocio.GestionUsuarioLocal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author Starman
 */
@ManagedBean
@SessionScoped
public class LoginClientesBean {
    //Atributos de la clase

    @Inject
    private GestionUsuarioLocal gestionUsuarios;
    private Cliente cliente;
    private String usuario;
    private String contraseña;

    @PostConstruct
    public void init() {
        cliente = new Cliente();
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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String validarCliente() {
        List<Cliente> lstClis = gestionUsuarios.listaClientes();
        System.out.println("PASO LA LISTAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        for (Cliente c : lstClis) {
            System.out.println("ENTROOOOOOOOOOOO EN EL FORRRRRR");
            if (c.getUsuario().equalsIgnoreCase(usuario) && c.getClave().equalsIgnoreCase(contraseña)) {
                System.out.println("ENTROOOOOOOOOOOO EN EL IFFFFFFFFFFFFFF CORRECTO");

                List<SesionCliente> lis = new ArrayList<>();
                lis = gestionUsuarios.obtenerSesionesCliente(c.getCedula());
                int bandera = 0;
                for (int i = 0; i <= 3; i++) {
                    if (lis.get(i).getEstado().equalsIgnoreCase("Incorrecto")) {
                        bandera++;
                    }
                }
                if (bandera == 3) {
                    SesionCliente sesionCliente = new SesionCliente();
                    sesionCliente.setCliente(c);
                    sesionCliente.setFechaSesion(new Date());
                    sesionCliente.setEstado("Correcto");
                    gestionUsuarios.guardarSesion(sesionCliente);
                    try {
                        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cliente", c);

                        FacesContext contex = FacesContext.getCurrentInstance();
                        contex.getExternalContext().redirect("PaginaPrincipalClienteBloqueado.xhtml");
                        //contex.getExternalContext().redirect("PrincipalCliente.xhtml?faces-redirect=true&cedula=" + c.getCedula());
                        //PrincipalCliente
                    } catch (Exception e) {
                    }
                } else {
                    SesionCliente sesionCliente = new SesionCliente();
                    sesionCliente.setCliente(c);
                    sesionCliente.setFechaSesion(new Date());
                    sesionCliente.setEstado("Correcto");
                    gestionUsuarios.guardarSesion(sesionCliente);
                    try {
                        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cliente", c);

                        FacesContext contex = FacesContext.getCurrentInstance();
                        contex.getExternalContext().redirect("PaginaPrincipalCliente.xhtml?faces-redirect=true&cedula=" + c.getCedula());
                        //contex.getExternalContext().redirect("PrincipalCliente.xhtml?faces-redirect=true&cedula=" + c.getCedula());
                        //PrincipalCliente
                    } catch (Exception e) {
                    }
                }

            } else if (c.getUsuario().equalsIgnoreCase(usuario)) {
                System.out.println("ENTROOOOOOOOOOOO EN EL IFFFFFFFFFFFFFF MAL");
                SesionCliente sesionCliente2 = new SesionCliente();
                sesionCliente2.setCliente(c);
                sesionCliente2.setFechaSesion(new Date());
                sesionCliente2.setEstado("Incorrecto");
                gestionUsuarios.guardarSesion(sesionCliente2);
                return "InicioClientes";
            }
        }
        return "InicioClientes";
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "InicioClientes?faces-redirect=true";
        // return null;

    }

}
