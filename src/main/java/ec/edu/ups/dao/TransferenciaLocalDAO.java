/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ups.dao;

import ec.edu.ups.modelo.TransferenciaLocal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Starman
 */
@Stateless
public class TransferenciaLocalDAO {

    //Atributo de la clase
    private EntityManager em;

    /**
     * Metodo que permite registrar una Transferencia Local en la base de datos
     *
     * @param t Transferencia Local que se va a registrar en la base
     *
     */
    public void insert(TransferenciaLocal t) {
        em.persist(t);
    }

    /**
     * Metodo que permite actualizar una Transferencia Local en la base de datos
     *
     * @param t Transferencia Local que se va a actualizar en la base
     */
    public void update(TransferenciaLocal t) {
        em.merge(t);
    }

    /**
     * Metodo que permite obtener una Transferencia Local de la base de datos
     *
     * @param codigoTra Codigo que se utilizara para obtener la Transferencia
     * Local
     * @return Una Transferencia Local que se encuentre registrado en la base
     */
    public TransferenciaLocal read(int codigoTra) {
        return em.find(TransferenciaLocal.class, codigoTra);
    }

    /**
     * Metodo que permite eliminar una Transferencia Local de la base de datos
     *
     * @param codigoTra Codigo que se utiliza para poder eliminar la
     * Transferencia Local
     */
    public void delete(int codigoTra) {
        TransferenciaLocal c = read(codigoTra);
        em.remove(c);
    }

    /**
     * Metodo que permite obtener las Transferencias Locales que estan
     * registradas en la base de datos
     *
     * @return Lista de Transferencias Locales que estan registradas en la base
     * de datos
     */
    public List<TransferenciaLocal> getTransfereciaLocals() {
        String jpql = "SELECT t FROM TransfereciaLocal t ";

        Query q = em.createQuery(jpql, TransferenciaLocal.class);
        return q.getResultList();
    }

}
