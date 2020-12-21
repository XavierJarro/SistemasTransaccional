/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ups.dao;

import ec.edu.ups.modelo.SolicitudPoliza;
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
public class SolicitudPolizaDAO {

    @PersistenceContext(name = "SitemasTransaccional2PersistenceUnit")
    private EntityManager em;

    public void insert(SolicitudPoliza s) {
        em.persist(s);
    }

    public void update(SolicitudPoliza s) {
        em.merge(s);
    }

    public SolicitudPoliza read(int codigo) {
        return em.find(SolicitudPoliza.class, codigo);
    }

    public void delete(int codigo) {
        SolicitudPoliza s = read(codigo);
        em.remove(s);
    }

    public List<SolicitudPoliza> getSolicitudes() {
        String jpql = "SELECT s FROM SolicitudPoliza s ";

        Query q = em.createQuery(jpql, SolicitudPoliza.class);
        return q.getResultList();
    }

}
