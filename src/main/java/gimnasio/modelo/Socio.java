package gimnasio.modelo;

import java.time.LocalDate;

public class Socio {
    private String dni;
    private String nombre;
    private String apellido;
    private String telefono;
    private String plan;
    private LocalDate vencimiento;

    public Socio(String dni, String nombre, String apellido, String telefono, String plan, LocalDate vencimiento) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.plan = plan;
        this.vencimiento = vencimiento;
    }

    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getPlan() {
        return plan;
    }

    public String getTelefono() {
        return telefono;
    }

    public LocalDate getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(LocalDate vencimiento) {
        this.vencimiento = vencimiento;
    }
}
