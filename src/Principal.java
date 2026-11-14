package modelo;

public class Principal {

    public static void main(String[] args) {
        // Crear un nuevo socio usando el constructor (pasando el DNI como primer parámetro)
      Socio socio1 = new Socio(38456123, "Lucas Gonzalez", 15000, 12);

        // Mostrar los datos
        System.out.println("--- Datos iniciales ---");
        System.out.println(socio1);

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
        String textoLeido = "40123987;Lucia Fernandez;18000.5;20";
        Socio socio2 = Socio.fromCSV(textoLeido);
        System.out.println(socio2);
    
    }
}
