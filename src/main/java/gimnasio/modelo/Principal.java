package modelo;

import java.time.LocalDate;
import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;

public class Principal {

    public static void main(String[] args) {
        // Crear un nuevo socio usando el constructor (pasando el DNI como primer parámetro)
    	Socio socio1 = new Socio(38456123, "Lucas Gonzalez", 15000, 12, LocalDate.of(2026, 11, 30));

        // Mostrar los datos
    	
        System.out.println("--- Datos iniciales ---");
        System.out.println(socio1);
        
     // Registrar un pago
        try {

            Pago pago1 = new Pago(
                String.valueOf(socio1.getDni()),
                LocalDate.now(),
                socio1.getValorCuota()
            );

            pago1.actualizarVencimiento(socio1);

            ArchivoPagos archivo = new ArchivoPagos();
            archivo.guardarPago(pago1);
            
            System.out.println("--- Pagos recuperados del archivo ---");

            for (String[] fila : archivo.leerPagos()) {
                System.out.println(String.join(" | ", fila));
            }
            
            System.out.println("--- Después del pago ---");
            System.out.println("Fecha de vencimiento: " + socio1.getFechaVencimiento());
            System.out.println(pago1);

        } catch (DatoInvalido e) {
            System.out.println("Error al registrar el pago: " + e.getMessage());

        } catch (IOException e) {
            System.out.println("Error al guardar el pago: " + e.getMessage());
        }
        catch (CsvValidationException e) {
            System.out.println("Error al leer pagos: " + e.getMessage());
        }
        // Modificar datos usando setters y consultar con getters
        socio1.setPasesDisponibles(socio1.getPasesDisponibles() - 1);
        socio1.setValorCuota(16500);

        System.out.println("\n--- Después de usar un pase y actualizar cuota ---");
        System.out.println("DNI: " + socio1.getDni());
        System.out.println("Nombre: " + socio1.getNombre());
        System.out.println("Pases restantes: " + socio1.getPasesDisponibles());

        // 4. Probar la conversión a texto CSV
        String lineaCSV = socio1.toCSV();
        System.out.println("\n--- Formato CSV ---");
        System.out.println(lineaCSV);

        // 5. Crear otro socio a partir de una línea de texto CSV (con DNI)
        String textoLeido = "40123987;Lucia Fernandez;18000.5;20;2026-12-14";
        Socio socio2 = Socio.fromCSV(textoLeido);
        System.out.println(socio2);
        System.out.println("Vencimiento recuperado: " + socio2.getFechaVencimiento());
    
    }
}
