package cabana;
public class Gold extends Cabania {
    
    public Gold(String nombreCabania, double precioNoche, int capacidadPersonas) {
        super(nombreCabania, precioNoche, capacidadPersonas); // Llamada al constructor padre
    }
    
    @Override
    public String getTipoCabania() {
        return "GOLD - Cabaña de Lujo Supremo".toUpperCase(); 
    }

        /*Servicios básicos incluidos en Gold */
     
    @Override
    public String getServicios() {
        StringBuilder servicios = new StringBuilder(); // StringBuilder para concatenación eficiente
        servicios.append("SERVICIOS GOLD INCLUIDOS:\n");
        servicios.append("Servicio de mayordomo 24/7\n");
        servicios.append("Spa privado con jacuzzi\n");
        servicios.append("Desayuno gourmet incluido\n");
        servicios.append("Transporte privado\n");
        servicios.append("Concierge personalizado\n");
        
        return servicios.toString();
    }
    
    @Override
    public double calcularPrecioFinal(double precioBase) {
        return precioBase; 
    }
    
    @Override
    public void mostrarDetalles() {
        System.out.println("=== CABAÑA GOLD ===");
        System.out.println(getDescripcionCompleta()); // Método heredado de clase padre
        System.out.println("Tipo: " + getTipoCabania());
        System.out.println(getServicios());
        System.out.println("========================");
    }
}