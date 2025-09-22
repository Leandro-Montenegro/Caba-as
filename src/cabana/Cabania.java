package cabana;


public abstract class Cabania {
    protected String nombre;           
    protected double precioPorNoche;   
    protected int capacidadMaxima;     
    
    public Cabania(String nombreCabania, double precioNoche, int capacidadPersonas) {
        this.nombre = nombreCabania;
        this.precioPorNoche = precioNoche;
        this.capacidadMaxima = capacidadPersonas;
    }
    
    // Metodos set and get
    
    public String getNombre() { return nombre; }
    public double getPrecioPorNoche() { return precioPorNoche; }
    public int getCapacidadMaxima() { return capacidadMaxima; }
    
    public void setNombre(String nombreCabania) { 
        this.nombre = nombreCabania;
    }
    
    // Van afuera por que no son fijos, a diferencia de setNombre.
    public void setPrecioPorNoche(double precio) { this.precioPorNoche = precio; }
    public void setCapacidadMaxima(int capacidad) { this.capacidadMaxima = capacidad; }
    
    public String getDescripcionCompleta() {
        StringBuilder descripcion = new StringBuilder(); // StringBuilder para concatenación eficiente
        descripcion.append(nombre.toUpperCase()).append("\n");
        descripcion.append("Precio: $").append(String.format("%.2f", precioPorNoche)).append("/noche\n");
        descripcion.append("Capacidad: ").append(capacidadMaxima).append(" personas\n");
        return descripcion.toString();
    }
    
    public double calcularPrecioTotal(int noches) {
        return precioPorNoche * noches;
    }
    
    public abstract String getTipoCabania();           
    public abstract String getServicios();             
    public abstract double calcularPrecioFinal(double precioBase);
    public abstract void mostrarDetalles();            
    
    @Override
    public String toString() {
        return String.format("Cabaña: %s | Precio: $%.2f/noche | Capacidad: %d personas", 
                           nombre, precioPorNoche, capacidadMaxima);
    }
}