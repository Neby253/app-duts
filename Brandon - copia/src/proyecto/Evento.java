/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto;

import java.util.Date;

public class Evento {
    private int id;
    private String nombre;
    private String descripcion;
    private Date fecha;
    private double requiereDUTS;

    public Evento(int id, String nombre, String descripcion, Date fecha, double requiereDUTS) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.requiereDUTS = requiereDUTS;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public double getRequiereDUTS() {
        return requiereDUTS;
    }
}
