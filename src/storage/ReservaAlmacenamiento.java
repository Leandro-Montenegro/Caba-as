package storage;
import java.util.List;

public interface ReservaAlmacenamiento {
    void guardarReserva(String detalleReserva);
    List<String> obtenerTodasLasReservas();
    void eliminarReserva(int index);
    void actualizarReserva(int index, String nuevoDetalle);
}