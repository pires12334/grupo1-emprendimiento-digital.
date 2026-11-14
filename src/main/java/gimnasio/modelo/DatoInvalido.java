package modelo;

public class DatoInvalido extends Exception {

    private static final long serialVersionUID = 1L;

    public DatoInvalido(String mensaje) {
        super(mensaje);
    }
}