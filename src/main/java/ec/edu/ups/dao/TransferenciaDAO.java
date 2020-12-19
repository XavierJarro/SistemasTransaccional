/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ups.dao;

import ec.edu.ups.modelo.Transferencia;
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
public class TransferenciaDAO {

    @PersistenceContext(name = "SitemasTransaccional2PersistenceUnit")
    private EntityManager em;

    public void insert(Transferencia t) {
        em.persist(t);
    }

    public void update(Transferencia t) {
        em.merge(t);
    }

    public Transferencia read(int codigoTra) {
        return em.find(Transferencia.class, codigoTra);
    }

    public void delete(int codigoTra) {
        Transferencia c = read(codigoTra);
        em.remove(c);
    }

    public List<Transferencia> getTransfereciaLocals() {
        String jpql = "SELECT t FROM TransfereciaLocal t ";

        Query q = em.createQuery(jpql, Transferencia.class);
        return q.getResultList();
    }
}
