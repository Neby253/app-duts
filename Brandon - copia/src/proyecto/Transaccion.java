/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto;

import java.util.Date;

public class Transaccion {
    private double monto;
    private Date fecha;
    private String tipo; // Ej: "enviar", "recibir"

    public Transaccion(double monto, Date fecha, String tipo) {
        this.monto = monto;
        this.fecha = fecha;
        this.tipo = tipo;
    }

    public double getMonto() {
        return monto;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getTipo() {
        return tipo;
    }
}
