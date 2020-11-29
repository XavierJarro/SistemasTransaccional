/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ups.dao;

import ec.edu.ups.modelo.Cuenta;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Starman
 */
@Stateless
public class CuentaDAO {

    //Atributo de la clase
    private EntityManager em;

    /**
     * Metodo que permite registrar una cuenta de ahorro en la base de datos
     *
     * @param cuenta Cuenta que se registrar
     */
    public void insert(Cuenta cuenta) {
        em.persist(cuenta);
    }

    /**
     * Metodo que permite actualizar una cuenta de ahorro en la base de datos
     *
     * @param cuenta Cuenta que se actualiza
     */
    public void update(Cuenta cuenta) {
        em.merge(cuenta);
    }

    /**
     * Metodo que permite obtener una cuenta de ahorro de la base de datos
     *
     * @param numeroCuenta Numero de la cuenta que se busca
     * @return una cuenta de ahorro que este registrada en la base
     */
    public Cuenta read(int numeroCuenta) {
        return em.find(Cuenta.class, numeroCuenta);
    }

    /**
     * Metodo que permite eliminar una cuenta de ahorro de la base de datos
     *
     * @param numeroCuenta Numero de la cuenta que queremos eliminar
     */
    public void delete(int numeroCuenta) {
        Cuenta cuenta = read(numeroCuenta);
        em.remove(cuenta);
    }

    /**
     * Metodo que permite obtener las cuentas de ahorro que estan registrados en
     * la base de datos
     *
     * @return Lista de cuentas de ahorros que estan registradas en la base de
     * datos
     */
    public List<Cuenta> getCuentaDeAhorros() {
        String jpql = "SELECT c FROM Cuenta c ";

        Query q = em.createQuery(jpql, Cuenta.class);
        return q.getResultList();
    }

    /**
     * Metodo que permite obtener una cuenta de ahorro en base a su codigo
     * registrado en la base de datos
     *
     * @param cedulaCliente Cedula del cliente que queremos buscar
     * @return Cuenta de ahorro que tenga un cliente registrado en la base
     */
    public Cuenta getCuentaCedulaCliente(String cedulaCliente) {
        String jpql = "SELECT c FROM Cuenta c WHERE c.cliente.cedula = :cedulaCliente";
        Query q = em.createQuery(jpql, Cuenta.class);
        q.setParameter("cedulaCliente", cedulaCliente);
        Cuenta cuentaDeAhorro = (Cuenta) q.getSingleResult();
        return cuentaDeAhorro;
    }

}
