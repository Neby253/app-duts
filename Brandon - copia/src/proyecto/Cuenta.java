/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto;
import java.util.ArrayList;
import java.util.List;

public class Cuenta {
    private double saldo;
    private List<Transaccion> historial;
    private double promedio;

    public Cuenta() {
        this.saldo = 0;
        this.historial = new ArrayList<>();
        this.promedio = 0;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public List<Transaccion> getHistorial() {
        return historial;
    }

    public void agregarTransaccion(Transaccion transaccion) {
        this.historial.add(transaccion);
    }

    public double calcularPromedio() {
        double total = 0;
        for (Transaccion t : historial) {
            total += t.getMonto();
        }
        return total / historial.size();
    }

    public double obtenerPromedio() {
        return promedio;
    }
}
