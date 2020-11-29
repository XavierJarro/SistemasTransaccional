/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ups.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Starman
 */
@Entity
public class Cliente implements Serializable {

    //Atributos de la entidad
    @Id
    @Column(name = "cedula_cliente")
    private String cedula;
    private String nombre;
    private String apellido;
    private String direccion;
    private String telefono;
    private Date fechaNacimiento;
    private String correo;
    private String estadoCivil;
    private String sexo;
    private String password;

    /**
     * Constructor de la clase
     */
    public Cliente() {

    }

    /**
     * Metodo que permite obtener el atributo cedula
     *
     * @return El atributo cedula de esta clase
     */
    public String getCedula() {
        return cedula;
    }

    /**
     * Metodo que permite asignarle un valor al atributo cedula
     *
     * @param cedula parametro para poder obtener
     */
    public void setCedula(String cedulae) {
        this.cedula = cedula;
    }

    /**
     * Metodo que permite obtener el atributo nombre
     *
     * @return El atributo nombre de esta clase
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Metodo que permite asignarle un valor al atributo nombre
     *
     * @param cedula Variable que se asigna al atributo de la clase
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Metodo que permite obtener el atributo apellido
     *
     * @return El atributo apellido de esta clase
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * Metodo que me permite asignarle un valor al atributo apellido
     *
     * @param apellido Variable que se asigna al atributo de la clase
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * Metodo que permite obtener el atributo direccion
     *
     * @return El atributo direccion de esta clase
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Metodo que me permite asignarle un valor al atributo direccion
     *
     * @param direccion Variable que se asigna al atributo de la clase
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Metodo que permite obtener el atributo telefono1
     *
     * @return El atributo telefono1 de esta clase
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Metodo que me permite asignarle un valor al atributo telefono1
     *
     * @param telefono1 Variable que se asigna al atributo de la clase
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Metodo que permite obtener el atributo fechaNacimiento
     *
     * @return El atributo fechaNacimiento de esta clase
     */
    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Metodo que me permite asignarle un valor al atributo fechaNacimiento
     *
     * @param fechaNacimiento Variable que se asigna al atributo de la clase
     */
    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Metodo que permite obtener el atributo correo
     *
     * @return El atributo correo de esta clase
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Metodo que me permite asignarle un valor al atributo correo
     *
     * @param correo variable que se asigna al atributo de la clase
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Metodo que permite obtener el atributo estadoCivil
     *
     * @return El atributo correo de esta clase
     */
    public String getEstadoCivil() {
        return estadoCivil;
    }

    /**
     * Metodo que me permite asignarle un valor al atributo estadoCivil
     *
     * @param estadoCivil variable que se asigna al atributo de la clase
     */
    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    /**
     * Metodo que permite obtener el atributo sexo
     *
     * @return El atributo correo de esta clase
     */
    public String getSexo() {
        return sexo;
    }

    /**
     * Metodo que me permite asignarle un valor al atributo sexo
     *
     * @param sexo variable que se asigna al atributo de la clase
     */
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    /**
     * Metodo que permite obtener el atributo clave
     *
     * @return El atributo clave de esta clase
     */
    public String getPassword() {
        return password;
    }

    /**
     * Metodo que me permite asignarle un valor al atributo clave
     *
     * @param clave Variable que se asigna al atributo de la clase
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
