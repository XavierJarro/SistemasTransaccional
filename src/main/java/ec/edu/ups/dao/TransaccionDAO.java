/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ups.dao;

import ec.edu.ups.modelo.Transaccion;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Starman
 */
@Stateless
public class TransaccionDAO {

    @PersistenceContext(name = "SitemasTransaccional2PersistenceUnit")
    private EntityManager em;

    public void insert(Transaccion s) {
        em.persist(s);
    }

    public void update(Transaccion s) {
        em.merge(s);
    }

    public Transaccion read(int codigoTransaccion) {
        return em.find(Transaccion.class, codigoTransaccion);
    }

    public void delete(int codT) {
        Transaccion c = read(codT);
        em.remove(c);
    }

    public List<Transaccion> getListaTransacciones(String cedula) throws Exception {
        try {
            String jpql = "SELECT s FROM Transaccion s Where s.cliente.cedula =:ced";
            Query q = em.createQuery(jpql, Transaccion.class);
            q.setParameter("ced", cedula);
            return q.getResultList();
        } catch (NoResultException e) {
            // System.out.println(e.getMessage());
            throw new Exception("Credenciaales Inocorrectas");
        }

    }

    public List<Transaccion> getListaTransaccionesFechas(String cedula, String fechI, String fechaF) throws Exception {
        //select * from transaccion where cedula_cliente = 0105011399 AND fecha BETWEEN '2020-06-03 20:21:40.090000' AND '2020-06-03 23:22:39.160000';
        String tl = "select s from Transaccion s where s.cliente.cedula = '" + cedula + "' AND s.fecha BETWEEN '" + fechI + "' AND '" + fechaF + "' ORDER BY s.fecha DESC";
        try {
            String jpql = tl;
            Query q = em.createQuery(jpql, Transaccion.class);
            //q.setParameter("ced", cedula);
            //q.setParameter("fcI", fechI);
            //q.setParameter("fcF", fechaF);
            return q.getResultList();
        } catch (NoResultException e) {
            throw new Exception("Erro Consultas Entre Fechas");
        }

    }
}
