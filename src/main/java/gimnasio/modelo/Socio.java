package modelo;

import java.time.LocalDate;

public class Socio {
    private int dni;
    private String nombre;
    private double valorCuota;
    private int pasesDisponibles;
    private LocalDate fechaVencimiento;
    
    public Socio(int dni, String nombre, double valorCuota, int pasesDisponibles, LocalDate fechaVencimiento) {
        this.dni = dni;
        this.nombre = nombre;
        this.valorCuota = valorCuota;
        this.pasesDisponibles = pasesDisponibles;
        this.fechaVencimiento = fechaVencimiento;
    }
    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getValorCuota() {
        return valorCuota;
    }

    public void setValorCuota(double valorCuota) {
        this.valorCuota = valorCuota;
    }

    public int getPasesDisponibles() {
        return pasesDisponibles;
    }
    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
    public void setPasesDisponibles(int pasesDisponibles) {
        this.pasesDisponibles = pasesDisponibles;
    }

    public String toCSV() {

        return dni + ";" + nombre + ";" + valorCuota + ";" + pasesDisponibles + ";" + fechaVencimiento;

    }

    public static Socio fromCSV(String linea) {

        String[] partes = linea.split(";");

        int dni = Integer.parseInt(partes[0]);
        String nombre = partes[1];
        double valorCuota = Double.parseDouble(partes[2]);
        int pasesDisponibles = Integer.parseInt(partes[3]);
        LocalDate fechaVencimiento = LocalDate.parse(partes[4]);

        return new Socio(dni, nombre, valorCuota, pasesDisponibles, fechaVencimiento);
    }
    @Override
    public String toString() {
        return "Socio [DNI=" + dni + ", Nombre=" + nombre + ", Cuota=$" + valorCuota + ", Pases=" + pasesDisponibles + "]";
    }
}
