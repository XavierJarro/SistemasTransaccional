/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ups.dao;

import ec.edu.ups.modelo.Poliza;
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
public class PolizaDAO {

    @PersistenceContext(name = "SitemasTransaccional2PersistenceUnit")
    private EntityManager em;

    /**
     * Metodo para guardar un Poliza
     *
     * @param s El parametro s me permite asignar los datos del Poliza
     */
    public void insert(Poliza s) {
        em.persist(s);
    }

    /**
     * Metodo para actualizar el Poliza
     *
     * @param s El parametro s me permite asignar los nuevos valores a un
     * Poliza
     */
    public void update(Poliza s) {
        em.merge(s);
    }

    /**
     * Metodo para obtener un Poliza
     *
     * @param codigoCredito El parametro codigocredito me permite obtener el
     * Poliza con el codigo igual al paremetro
     * @returnn Un Poliza
     */
    public Poliza read(int codigoPoliza) {
        return em.find(Poliza.class, codigoPoliza);
    }

    /**
     * Metodo para eliminar un Poliza
     *
     * @param codigoCredito El parametro codigocredito me permite eliminar el
     * credito con el codigo igual al paremetro
     */
    public void delete(int codigoPoliza) {
        Poliza c = read(codigoPoliza);
        em.remove(c);
    }

    /**
     * Metodo para obtener los Poliza de la aplicacion
     *
     * @return Una lista de Poliza
     */
    public List<Poliza> getPolizas() {
        String jpql = "SELECT s FROM Poliza s ";

        Query q = em.createQuery(jpql, Poliza.class);
        return q.getResultList();
    }
}
