package gimnasio.modelo;

public class Principal {

	public static void main(String[] args) {
		//Crear un nuevo socio usando el constructor
		Socio socio1 = new Socio(1,"Lucas Gonzalez", 15000,12);
		
		//Mostrar los datos 
		System.out.println("--- Datos iniciales ---");
		System.out.println(socio1);
		
		//Modificar datos usando setters y consultar con getters
		socio1.setPasesDisponibles(socio1.getPasesDisponibles() - 1);
		socio1.setValorCuota(16500);
		
		System.out.println("\n--- Despues de usar un pase y actualizar cuota ---");
		System.out.println("Nombre: " + socio1.getNombre());
		System.out.println("Pases restantes: " + socio1.getPasesDisponibles());
		
		// 4. Probar la conversión a texto CSV
        String lineaCSV = socio1.toCSV();
        System.out.println("\n--- Formato CSV ---");
        System.out.println(lineaCSV);

        // 5. Crear otro socio a partir de una línea de texto CSV
        String textoLeido = "102;Lucia Fernandez;18000.5;20";
        Socio socio2 = Socio.fromCSV(textoLeido);
        
        System.out.println("\n--- Socio cargado desde CSV ---");
        System.out.println(socio2);

	}

}
