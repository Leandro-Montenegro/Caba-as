package storage;
import java.util.ArrayList;
import java.util.List;

//implements → se usa para cumplir el contrato de una interfaz (implementación de métodos).
public class AlmacenamientoEnMemoria implements ReservaAlmacenamiento {
    private List<String> reservas;

    public AlmacenamientoEnMemoria() {
        this.reservas = new ArrayList<>();
    }

    @Override
    public void guardarReserva(String detalleReserva) {
        reservas.add(detalleReserva);
    }

    @Override
    public List<String> obtenerTodasLasReservas() {
        return new ArrayList<>(reservas); 
    }

    @Override
    public void eliminarReserva(int index) {
        if (index >= 0 && index < reservas.size()) {
            reservas.remove(index);
        }
    }

    @Override
    public void actualizarReserva(int index, String nuevoDetalle) {
        if (index >= 0 && index < reservas.size()) {
            reservas.set(index, nuevoDetalle);
        }
    }
}