/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ups.dao;

import ec.edu.ups.modelo.SesionCliente;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Starman
 */
@Stateless
public class SesionClienteDAO {

    //Atributo de la clase
    private EntityManager em;

    /**
     * Metodo que permite registrar una sesion en la base de datos
     *
     * @param s Sesion que se inserta en la base de datos
     */
    public void insert(SesionCliente s) {
        em.persist(s);
    }

    /**
     * Metodo que permite actualizar una sesion en la base de datos
     *
     * @param s Sesion que se actualiza en la base
     */
    public void update(SesionCliente s) {
        em.merge(s);
    }

    /**
     * Metodo que permite obtener una sesion de la base de datos
     *
     * @param codigoSesion Codigo de la sesion que se busca
     * @return una sesion que este registrada en la base de datos
     */
    public SesionCliente read(int codigoSesion) {
        return em.find(SesionCliente.class, codigoSesion);
    }

    /**
     * Metodo que permite eliminar una sesion de la base de datos
     *
     * @param codigoSesion Codigo de la sesion que se elimina
     */
    public void delete(int codigoSesion) {
        SesionCliente c = read(codigoSesion);
        em.remove(c);
    }

    /**
     * Metodo que permite obtener las sesiones que estan registrados en la base
     * de datos
     *
     * @return Lista de sesiones que estan registradas en la base de datos
     */
    public List<SesionCliente> getSesionClientes() {
        String jpql = "SELECT s FROM SesionCliente s ";

        Query q = em.createQuery(jpql, SesionCliente.class);
        return q.getResultList();
    }

    /**
     * Metodo que permite obtener las sesiones de un cliente registrado en la
     * base de datos
     *
     * @param cedulaCliente Cedula del cliente que debe tener las sesiones
     * registradas en la base
     * @return lista de sesiones de un cliente en especifico
     * @throws Exception Control de errores a la hora de realizar la consulta
     */
    public List<SesionCliente> obtenerSesionCliente(String cedulaCliente) throws Exception {
        try {
            String jpql = "SELECT s FROM SesionCliente s WHERE s.cliente.cedula = :cedulaCliente order by s.fechaSesion desc";
            Query q = em.createQuery(jpql, SesionCliente.class);
            q.setParameter("cedulaCliente", cedulaCliente);
            return q.getResultList();
        } catch (Exception e) {
            throw new Exception("No ha ingresado ni una sola vez");
        }

    }
}
