package modelo;

import java.time.LocalDate;

public class Pago {

    private String dniSocio;
    private LocalDate fecha;
    private double monto;
    private LocalDate nuevoVencimiento;

    public Pago(String dniSocio, LocalDate fecha, double monto) throws DatoInvalido {

        if (dniSocio == null || dniSocio.isBlank()) {
            throw new DatoInvalido("El DNI no puede estar vacío");
        }

        if (fecha == null) {
            throw new DatoInvalido("La fecha de pago no puede estar vacía");
        }

        if (monto <= 0) {
            throw new DatoInvalido("El monto debe ser mayor a cero");
        }

        this.dniSocio = dniSocio;
        this.fecha = fecha;
        this.monto = monto;
        this.nuevoVencimiento = fecha.plusMonths(1);
    }

    public void actualizarVencimiento(Socio socio) {
        socio.setFechaVencimiento(nuevoVencimiento);
    }

    public String getDniSocio() {
        return dniSocio;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public double getMonto() {
        return monto;
    }

    public LocalDate getNuevoVencimiento() {
        return nuevoVencimiento;
    }

    @Override
    public String toString() {
        return "Pago | DNI: " + dniSocio +
               " | Fecha: " + fecha +
               " | Monto: $" + monto +
               " | Nuevo vencimiento: " + nuevoVencimiento;
    }
}