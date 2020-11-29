/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ups.dao;

import ec.edu.ups.modelo.Empleado;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Starman
 */
@Stateless
public class EmpleadoDAO {

    @Inject
    private EntityManager em;

    /**
     * Metodo para el registro de un Empleado
     *
     * @param emleado El parametro empleado me permite registrar un Empleado en
     * la Base de Datos
     * @return Si se logro registrar el Empleado TRUE o FALSE
     */
    public boolean insertEmpleado(Empleado empleado) {
        em.persist(empleado);
        return true;
    }

    /**
     * Metodo para obtener el Empleado
     *
     * @param cedula El parametro cedula me permite obtener el Empleado
     * @return EL empleado de acuerdo al parametro cedula
     */
    public Empleado readEmpleado(String cedula) {
        return em.find(Empleado.class, cedula);
    }

    /**
     * Metodo para modificar el Empleado
     *
     * @param empleado El parametro empleado permite modificar el Empleado que
     * se tienen en el parametro
     * @return Si se logro modificar el Empleado TRUE o FALSE
     */
    public boolean updateEmpleado(Empleado empleado) {
        em.merge(empleado);
        return true;
    }

    /**
     * Metodo para eliminar un Empleado
     *
     * @param empleado El parametro per me permite eliminar un Empleado de
     * acuedor al parametro
     */
    public void deleteEmpleado(Empleado empleado) {
        Empleado p = readEmpleado(empleado.getCedula());
        em.remove(p);

    }

    /**
     * Metodo para obtener la lista de los Empleados
     *
     * @return La lista con todos los empleados de Instituion
     */
    public List<Empleado> obtenerListaEmpleado() {
        String jpl = "select p from Empleado p";
        Query q = em.createQuery(jpl, Empleado.class);
        return q.getResultList();

    }

    /**
     * Metodo para ontener un Empleado
     *
     * @param correo El parametro usuario me permite obtener el Empleado de
     * acuerdo al parametro
     * @param password El parametro contra me permite obtener el Empleado de
     * acuedo al parametro
     * @return El empleado de acuedo al los parametros usuario y contra
     * @throws Exception Para cuando se ingresa las credenciales incorrectas
     */
    public Empleado obtenerEmpleado(String correo, String password) throws Exception {
        try {
            String jpl = "select p from Empleado p Where p.correo =:correo AND p.password =:password";
            Query q = em.createQuery(jpl, Empleado.class);
            q.setParameter("correo", correo);
            q.setParameter("password", password);
            return (Empleado) q.getSingleResult();

        } catch (NoResultException e) {
            //System.out.println(e.getMessage());
            throw new Exception("Credenciaales Inocorrectas");
        }
        //return null;
    }

}
