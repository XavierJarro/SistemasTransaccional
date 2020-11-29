/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ups.dao;

import ec.edu.ups.modelo.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
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
public class ClienteDAO {

    @Inject
    private EntityManager em;

    /**
     * Metodo que permite registrar un cliente en la base de datos
     *
     * @param cliente Cliente que se va a registrar en la base
     */
    public boolean insertCliente(Cliente cliente) {
        em.persist(cliente);
        return true;
    }

    /**
     * Metodo que permite actualizar un cliente en la base de datos
     *
     * @param cliente Cliente que se va a actualizar en la base
     */
    public boolean updateCliente(Cliente cliente) {
        em.merge(cliente);
        return true;
    }

    /**
     * Metodo que permite obtener un cliente de la base de datos
     *
     * @param cedula Cedula que se utilizara para obtener el cliente
     * @return un cliente que se encuentre registrado en la base
     */
    public Cliente readCliente(String cedula) {
        return em.find(Cliente.class, cedula);
    }

    /**
     * Metodo que permite eliminar un cliente de la base de datos
     *
     * @param cedula Cedula utilizaremos para poder eliminar el cliente
     */
    public boolean deleteCliente(String cedula) {
        Cliente cliente = readCliente(cedula);
        em.remove(cliente);
        return true;
    }

    /**
     * Metodo que permite obtener los clientes que estan registrados en la base
     * de datos
     *
     * @return Lista de clientes que estan registrados en la base de datos
     */
    public List<Cliente> obtenerListaClientes() {
        String jpql = "SELECT c FROM Cliente c ";

        Query q = em.createQuery(jpql, Cliente.class);
        return q.getResultList();
    }

    /**
     * Metodo que permite obtener un cliente dependiendo de su correo y
     * cotraseña
     *
     * @param correo Variable de tipo String en donde se asigna el correo de la
     * persona que se desea obtener
     * @param password Variable de tipo String en donde se asigna la contraseña de
     * la persona que se desea obtener
     * @return Cliente que tenga la el correo y contraseña que se han pasado
     * como paramatro
     * @throws Exception
     */
    public Cliente obtenerCliente(String correo, String password) throws Exception {

        try {
            String jpl = "select c from Cliente c Where c.correo =:correo AND c.password =:password";
            Query q = em.createQuery(jpl, Cliente.class);
            q.setParameter("correo", correo);
            q.setParameter("password", password);
            return (Cliente) q.getSingleResult();

        } catch (NoResultException e) {
            //System.out.println(e.getMessage());
            throw new Exception("Credenciaales Inocorrectas");
        }

    }

}
