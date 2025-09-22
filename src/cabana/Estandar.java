package cabana;
public class Estandar extends Cabania {
    
    public Estandar(String nombreCabania, double precioNoche, int capacidadPersonas) {
        super(nombreCabania, precioNoche, capacidadPersonas); 
    }
    
    @Override
    public String getTipoCabania() {
        return "ESTÁNDAR - Cabaña Básica y Cómoda".toUpperCase(); 
    }
    
       /*Servicios básicos incluidos en Estandar */

    @Override
    public String getServicios() {
        StringBuilder servicios = new StringBuilder(); // StringBuilder para concatenación eficiente
        servicios.append("SERVICIOS ESTÁNDAR INCLUIDOS:\n");
        servicios.append("WiFi gratuito\n");
        servicios.append("Limpieza diaria\n");
        servicios.append("Recepción 24 horas\n");
        servicios.append("Aire acondicionado\n");
        servicios.append("TV por cable\n");
        servicios.append("Baño privado\n");
        
        return servicios.toString();
    }
    
    @Override
    public double calcularPrecioFinal(double precioBase) {
        return precioBase; 
    }
    
    @Override
    public void mostrarDetalles() {
        System.out.println("=== CABAÑA ESTÁNDAR ===");
        System.out.println(getDescripcionCompleta());
        System.out.println("Tipo: " + getTipoCabania());
        System.out.println(getServicios());
        System.out.println("============================");
    }
}