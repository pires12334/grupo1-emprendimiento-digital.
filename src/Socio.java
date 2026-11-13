package gimnasio_modelo;

public class Socio { 
    private int numeroSocio;
    private String nombre;
    private double valorCuota;
    private int pasesDisponibles;

    public Socio(int numeroSocio, String nombre, double valorCuota, int pasesDisponibles) {
        this.numeroSocio = numeroSocio;
        this.nombre = nombre;
        this.valorCuota = valorCuota;
        this.pasesDisponibles = pasesDisponibles;
    }

    public int getNumeroSocio() {
        return numeroSocio;
    }

    public void setNumeroSocio(int numeroSocio) {
        this.numeroSocio = numeroSocio;
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

    public void setPasesDisponibles(int pasesDisponibles) {
        this.pasesDisponibles = pasesDisponibles;
    }

    public String toCSV() {
        return numeroSocio + ";" + nombre + ";" + valorCuota + ";" + pasesDisponibles;
    }
    
    public static Socio fromCSV(String linea) {
        String[] datos = linea.split(";");
        int codigo = Integer.parseInt(datos[0]);
        String nombre = datos[1];
        double cuota = Double.parseDouble(datos[2]);
        int pases = Integer.parseInt(datos[3]);
        return new Socio(codigo, nombre, cuota, pases);
    }
}

    public String toString() {
        return "Socio #" + numeroSocio + " | Nombre: " + nombre + 
               " | Cuota: $" + valorCuota + " | Pases: " + pasesDisponibles;
    }
}
