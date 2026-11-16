package gimnasio.modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Registra los ingresos al gimnasio en un archivo de texto: datos/asistencias.txt
 * Cada línea: dni;fecha y hora;pases que le quedan
 * Responsable: Alan Joel Pires.
 */
public class ArchivoAsistencias {

    private static final String CARPETA = "datos";
    private static final String RUTA = CARPETA + "/asistencias.txt";
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final ArchivoSocios archivoSocios;

    public ArchivoAsistencias(ArchivoSocios archivoSocios) {
        this.archivoSocios = archivoSocios;
    }

    /**
     * Registra el ingreso de un socio: valida que exista, que la cuota no esté vencida
     * y que tenga pases. Descuenta un pase y escribe una línea en asistencias.txt.
     * Devuelve los pases que le quedan.
     */
    public int registrarIngreso(int dni) throws SocioNoEncontrado, DatoInvalido, IOException {
        Socio socio = archivoSocios.buscarSocio(dni);

        if (socio.tieneCuotaVencida()) {
            throw new DatoInvalido("La cuota de " + socio.getNombre() + " venció el " + socio.getFechaVencimiento());
        }
        if (socio.getPasesDisponibles() <= 0) {
            throw new DatoInvalido(socio.getNombre() + " no tiene pases disponibles");
        }

        archivoSocios.actualizarPases(dni, -1);

        new File(CARPETA).mkdirs();
        String linea = dni + ";" + LocalDateTime.now().format(FORMATO) + ";" + socio.getPasesDisponibles();

        // true = append: cada ingreso se agrega al final del archivo
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA, true))) {
            bw.write(linea);
            bw.newLine();
        }
        return socio.getPasesDisponibles();
    }

    /** Devuelve todas las líneas de asistencia de un socio. */
    public List<String> historialDe(int dni) throws IOException {
        List<String> resultado = new ArrayList<>();
        File archivo = new File(RUTA);
        if (!archivo.exists()) {
            return resultado;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 3 && partes[0].equals(String.valueOf(dni))) {
                    resultado.add("Ingreso: " + partes[1] + " | Pases restantes: " + partes[2]);
                }
            }
        }
        return resultado;
    }
}
