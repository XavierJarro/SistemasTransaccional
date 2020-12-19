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
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Starman
 */
@Stateless
public class SesionClienteDAO {

    @PersistenceContext(name = "SitemasTransaccional2PersistenceUnit")
    private EntityManager em;

    public void insert(SesionCliente s) {
        em.persist(s);
    }

    public void update(SesionCliente s) {
        em.merge(s);
    }

    public SesionCliente read(int codigoSesion) {
        return em.find(SesionCliente.class, codigoSesion);
    }

    public void delete(int codigoSesion) {
        SesionCliente c = read(codigoSesion);
        em.remove(c);
    }

    public List<SesionCliente> getSesionClientes() {
        String jpql = "SELECT s FROM SesionCliente s ";

        Query q = em.createQuery(jpql, SesionCliente.class);
        return q.getResultList();
    }

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
