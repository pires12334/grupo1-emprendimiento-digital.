package gimnasio.archivos;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import gimnasio.modelo.DatoInvalido;
import gimnasio.modelo.Socio;

/**
 * Registra los ingresos de los socios en un archivo de texto (asistencias.txt).
 * Escribe con FileWriter y lee con FileReader + BufferedReader, como en el apunte de la Clase 10.
 */
public class RegistroAsistencias {

    // Ruta del archivo de texto donde se guardan los ingresos
    private String ruta;

    public RegistroAsistencias(String ruta) {
        this.ruta = ruta;
    }

    // Registra el ingreso de un socio: valida que tenga pases, anota el ingreso y descuenta un pase
    public void registrarIngreso(Socio socio) throws IOException, DatoInvalido {
        // 1) Validar: sin pases no puede entrar
        if (socio.getPasesDisponibles() <= 0) {
            throw new DatoInvalido("El socio " + socio.getNombre() + " no tiene pases disponibles");
        }

        // 2) Calcular cuántos pases le van a quedar
        int restantes = socio.getPasesDisponibles() - 1;

        // 3) Escribir la línea al final del archivo (true = agregar, no borrar lo anterior).
        //    try-with-resources: el archivo se cierra solo, aunque ocurra un error.
        try (FileWriter fw = new FileWriter(ruta, true)) {
            String linea = LocalDateTime.now() + " - DNI " + socio.getDni()
                    + " - " + socio.getNombre()
                    + " - Pases restantes: " + restantes;
            fw.write(linea + "\n");
        }

        // 4) Recién ahora descontar el pase (si falló la escritura, no se descuenta)
        socio.setPasesDisponibles(restantes);
    }

    // Lee asistencias.txt línea por línea y devuelve solo los ingresos de ese DNI
    public List<String> ingresosDe(int dni) throws IOException {
        List<String> ingresos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                // El " -" del final evita que el DNI 123 coincida con el 1234
                if (linea.contains("DNI " + dni + " -")) {
                    ingresos.add(linea);
                }
            }
        } catch (FileNotFoundException e) {
            // Si todavía nadie entró, el archivo no existe: devolvemos la lista vacía
        }
        return ingresos;
    }
}
