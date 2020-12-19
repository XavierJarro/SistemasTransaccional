/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ups.dao;

import ec.edu.ups.modelo.CuentaDeAhorro;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Starman
 */
@Stateless
public class CuentaDeAhorroDAO {

    @PersistenceContext(name = "SitemasTransaccional2PersistenceUnit")
    private EntityManager em;

    public void insert(CuentaDeAhorro c) {
        em.persist(c);
    }

    public void update(CuentaDeAhorro c) {
        em.merge(c);
    }

    public CuentaDeAhorro read(String numeroCuentaDeAhorro) {
        return em.find(CuentaDeAhorro.class, numeroCuentaDeAhorro);
    }

    public void delete(String numeroCuentaDeAhorro) {
        CuentaDeAhorro c = read(numeroCuentaDeAhorro);
        em.remove(c);
    }

    public List<CuentaDeAhorro> getCuentaDeAhorros() {
        String jpql = "SELECT c FROM CuentaDeAhorro c ";

        Query q = em.createQuery(jpql, CuentaDeAhorro.class);
        return q.getResultList();
    }

    public CuentaDeAhorro getCuentaCedulaCliente(String cedulaCliente) {
        String jpql = "SELECT c FROM CuentaDeAhorro c WHERE c.cliente.cedula = :cedulaCliente";
        Query q = em.createQuery(jpql, CuentaDeAhorro.class);
        q.setParameter("cedulaCliente", cedulaCliente);
        CuentaDeAhorro cuentaDeAhorro = (CuentaDeAhorro) q.getSingleResult();
        return cuentaDeAhorro;
    }
}
