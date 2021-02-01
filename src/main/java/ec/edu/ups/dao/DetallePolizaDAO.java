/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ups.dao;

import ec.edu.ups.modelo.DetallePoliza;
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
public class DetallePolizaDAO {

    @PersistenceContext(name = "SitemasTransaccional2PersistenceUnit")
    private EntityManager em;

    /**
     * Metodo para guardar un DetallePoliza
     *
     * @param s El parametro s me permite asignar los datos del DetallePoliza
     */
    public void insert(DetallePoliza s) {
        em.persist(s);
    }

    /**
     * Metodo para actualizar el DetallePoliza
     *
     * @param s El parametro s me permite asignar los nuevos valores a un
     * DetallePoliza
     */
    public void update(DetallePoliza s) {
        em.merge(s);
    }

    /**
     * Metodo para obtener un DetallePoliza
     *
     * @param codigoDetallePoliza El parametro codigocredito me permite obtener
     * el DetallePoliza con el codigo igual al paremetro
     * @returnn Un DetallePoliza
     */
    public DetallePoliza read(int codigoDetallePoliza) {
        return em.find(DetallePoliza.class, codigoDetallePoliza);
    }

    /**
     * Metodo para eliminar un DetallePoliza
     *
     * @param codigoDetallePoliza El parametro codigoDetallePoliza me permite
     * eliminar el DetallePoliza con el codigo igual al paremetro
     */
    public void delete(int codigoDetallePoliza) {
        DetallePoliza c = read(codigoDetallePoliza);
        em.remove(c);
    }

    /**
     * Metodo para obtener los DetallePoliza de la aplicacion
     *
     * @return Una lista de DetallePolizas
     */
    public List<DetallePoliza> getDetallesCreditos() {
        String jpql = "SELECT s FROM DetallePoliza s ";

        Query q = em.createQuery(jpql, DetallePoliza.class);
        return q.getResultList();
    }

}
