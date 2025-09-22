package cabana;

public class Premium extends Cabania {

    public Premium(String nombreCabania, double precioNoche, int capacidadPersonas) {
        super(nombreCabania, precioNoche, capacidadPersonas); 
    }
    
    @Override
    public String getTipoCabania() {
        return "PREMIUM - Cabaña de Alta Calidad".toUpperCase(); 
    }
    
    /*Servicios básicos incluidos en Premium */

    @Override
    public String getServicios() {
        StringBuilder servicios = new StringBuilder();  // StringBuilder para concatenación eficiente
        servicios.append("SERVICIOS PREMIUM INCLUIDOS:\n");
        servicios.append("Desayuno buffet incluido\n");
        servicios.append("Acceso al gimnasio\n");
        servicios.append("WiFi de alta velocidad\n");
        servicios.append("Servicio de habitaciones\n");
        servicios.append("Piscina y jacuzzi\n");
        servicios.append("Estacionamiento gratuito\n");
        
        return servicios.toString();
    }
    
    @Override
    public double calcularPrecioFinal(double precioBase) {
        return precioBase; 
    }
    
    @Override
    public void mostrarDetalles() {
        System.out.println("=== CABAÑA PREMIUM ===");
        System.out.println(getDescripcionCompleta());
        System.out.println("Tipo: " + getTipoCabania());
        System.out.println(getServicios());
        System.out.println("===========================");
    }
}