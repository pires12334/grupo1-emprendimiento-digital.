package modelo;

public class SocioNoEncontrado extends Exception {

    private static final long serialVersionUID = 1L;

    public SocioNoEncontrado(String mensaje) {
        super(mensaje);
    }
}