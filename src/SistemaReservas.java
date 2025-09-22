import cabana.*;
import storage.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.util.InputMismatchException;

/*
 * Sistema de gestión de reservas de cabañas.
 * Permite a los clientes ver los tipos de cabañas,
 * realizar reservas y gestionar las reservas existentes.
 */
public class SistemaReservas {
    // Escáner para la entrada del usuario en toda la clase
    private static Scanner scanner = new Scanner(System.in);
    
    // Lista para almacenar los tipos de cabañas disponibles
    private static ArrayList<Cabania> tiposCabanias = new ArrayList<>();
    
    // Instancia de la interfaz de almacenamiento de reservas
    private static ReservaAlmacenamiento reservaAlmacenamiento = new AlmacenamientoEnMemoria();

    public static void main(String[] args) {
        inicializarTiposCabanias();
        mostrarMenuCliente();
    }
    
    /**
     * Inicializa los tipos de cabañas disponibles en el sistema.
     */
    private static void inicializarTiposCabanias() {
        try {
            tiposCabanias.add(new Gold("Cabaña Gold", 500.0, 4));
            tiposCabanias.add(new Premium("Cabaña Premium", 250.0, 6));
            tiposCabanias.add(new Estandar("Cabaña Estándar", 80.0, 2));
            System.out.println("Tipos de cabañas disponibles cargados correctamente.");
        } catch (Exception e) {
            System.err.println("Error al cargar tipos de cabañas: " + e.getMessage());
        }
    }
    
    /**
     * Muestra el menú principal para el cliente y gestiona las opciones.
     */
    private static void mostrarMenuCliente() {
        int opcion = 0;
        
        do {
            try {
                System.out.println("\n=== BIENVENIDO AL SISTEMA DE RESERVAS ===");
                System.out.println("1. Ver cabañas disponibles");
                System.out.println("2. Ver detalles de una cabaña");
                System.out.println("3. Realizar una reserva");
                System.out.println("4. Gestionar reservas (CRUD)");
                System.out.println("0. Salir");
                System.out.print("Seleccione una opción: ");
                
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer del scanner
                
                switch (opcion) {
                    case 1:
                        verCabanasDisponibles();
                        break;
                    case 2:
                        verDetallesCabana();
                        break;
                    case 3:
                        realizarReserva();
                        break;
                    case 4:
                        gestionarReservas();
                        break;
                    case 0:
                        System.out.println("¡Gracias por usar nuestro sistema! Hasta la próxima.");
                        break;
                    default:
                        System.out.println("Opción inválida. Solo disponibles: 1, 2, 3, 4, 0");
                }
            } catch (InputMismatchException e) {
                System.err.println("Error: Debe ingresar un número válido.");
                scanner.nextLine(); // Limpiar el buffer en caso de error
            } catch (Exception e) {
                System.err.println("Ocurrió un error inesperado: " + e.getMessage());
            }
        } while (opcion != 0);
    }
    
    /**
     * Muestra una lista de las cabañas disponibles.
     */
    private static void verCabanasDisponibles() {
        System.out.println("\n--- CABAÑAS DISPONIBLES ---");
        for (int i = 0; i < tiposCabanias.size(); i++) {
            System.out.println((i + 1) + ". " + tiposCabanias.get(i).toString());
        }
    }
    
    /**
     * Permite al usuario ver los detalles completos de una cabaña específica.
     */
    private static void verDetallesCabana() {
        try {
            verCabanasDisponibles();
            System.out.print("Ingrese el número de la cabaña para ver sus detalles: ");
            int indice = scanner.nextInt() - 1;
            scanner.nextLine();
            
            if (indice >= 0 && indice < tiposCabanias.size()) {
                tiposCabanias.get(indice).mostrarDetalles();
            } else {
                System.out.println("Número de cabaña inválido.");
            }
        } catch (InputMismatchException e) {
            System.err.println("Error: Debe ingresar un número válido.");
            scanner.nextLine();
        }
    }

    /**
     * Guía al cliente a través del proceso de realizar una reserva.
     */
    private static void realizarReserva() {
        try {
            System.out.println("\n--- REALIZAR UNA RESERVA ---");
            verCabanasDisponibles();
            
            System.out.print("Ingrese el número de la cabaña que desea reservar: ");
            int indiceCabana = scanner.nextInt() - 1;
            scanner.nextLine();
            
            if (indiceCabana < 0 || indiceCabana >= tiposCabanias.size()) {
                throw new IllegalArgumentException("Número de cabaña inválido.");
            }
            
            Cabania cabaniaSeleccionada = tiposCabanias.get(indiceCabana);
            
            System.out.print("Ingrese su nombre completo: ");
            String nombreCliente = scanner.nextLine().trim();
            if (nombreCliente.isEmpty()) {
                throw new IllegalArgumentException("El nombre del cliente no puede estar vacío.");
            }
            
            System.out.print("Ingrese el número de noches que desea quedarse: ");
            int noches = scanner.nextInt();
            scanner.nextLine();
            
            if (noches <= 0) {
                throw new IllegalArgumentException("El número de noches debe ser mayor que cero.");
            }
            
            double precioTotal = cabaniaSeleccionada.calcularPrecioTotal(noches);
            
            // Generar un código de reserva único basado en la hora del sistema
            String codigoReserva = "RES" + System.currentTimeMillis() % 10000;
            String detalleReserva = "Código: " + codigoReserva + " | Cliente: " + nombreCliente.toUpperCase() +
                                  " | Cabaña: " + cabaniaSeleccionada.getTipoCabania() +
                                  " | Noches: " + noches + " | Precio total: $" + precioTotal;
            
            reservaAlmacenamiento.guardarReserva(detalleReserva);
            
            // Mostrar confirmación
            System.out.println("\n¡RESERVA CONFIRMADA!");
            System.out.println("===================================================");
            System.out.println("Código de reserva: " + codigoReserva);
            System.out.println("Cliente: " + nombreCliente.toUpperCase());
            System.out.println("Cabaña: " + cabaniaSeleccionada.getTipoCabania());
            System.out.println("Noches: " + noches);
            System.out.println("Precio total: $" + String.format("%.2f", precioTotal));
            System.out.println("===================================================");
            System.out.println("¡Gracias por su reserva! Le esperamos pronto.");
            
        } catch (InputMismatchException e) {
            System.err.println("Error: Debe ingresar un número válido.");
            scanner.nextLine();
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println("Error al procesar la reserva: " + e.getMessage());
        }
    }

    /**
     * Muestra un sub-menú para gestionar las reservas existentes.
     */
    private static void gestionarReservas() {
        int opcionCrud = 0;
        do {
            try {
                System.out.println("\n=== GESTIÓN DE RESERVAS ===");
                System.out.println("1. Ver todas las reservas");
                System.out.println("2. Eliminar una reserva");
                System.out.println("3. Actualizar una reserva");
                System.out.println("0. Volver al menú principal");
                System.out.print("Seleccione una opción: ");
                opcionCrud = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer

                switch (opcionCrud) {
                    case 1:
                        verTodasLasReservas();
                        break;
                    case 2:
                        eliminarReserva();
                        break;
                    case 3:
                        actualizarReserva();
                        break;
                    case 0:
                        System.out.println("Volviendo al menú principal...");
                        break;
                    default:
                        System.out.println("Opción inválida. Solo disponibles: 1, 2, 3, 0");
                }
            } catch (InputMismatchException e) {
                System.err.println("Error: Debe ingresar un número válido.");
                scanner.nextLine();
            }
        } while (opcionCrud != 0);
    }

    /**
     * Muestra todas las reservas guardadas en el almacenamiento.
     */
    private static void verTodasLasReservas() {
        System.out.println("\n--- LISTA DE RESERVAS REALIZADAS ---");
        List<String> reservas = reservaAlmacenamiento.obtenerTodasLasReservas();
        if (reservas.isEmpty()) {
            System.out.println("No hay reservas registradas.");
        } else {
            for (int i = 0; i < reservas.size(); i++) {
                System.out.println((i + 1) + ". " + reservas.get(i));
            }
        }
    }

    /**
     * Permite eliminar una reserva específica por su índice.
     */
    private static void eliminarReserva() {
        verTodasLasReservas();
        List<String> reservas = reservaAlmacenamiento.obtenerTodasLasReservas();
        if (reservas.isEmpty()) {
            return;
        }
        try {
            System.out.print("Ingrese el número de la reserva a eliminar: ");
            int index = scanner.nextInt() - 1;
            scanner.nextLine();
            if (index >= 0 && index < reservas.size()) {
                reservaAlmacenamiento.eliminarReserva(index);
                System.out.println("Reserva eliminada con éxito.");
            } else {
                System.err.println("Número de reserva inválido.");
            }
        } catch (InputMismatchException e) {
            System.err.println("Entrada inválida. Debe ser un número.");
            scanner.nextLine();
        }
    }

    /**
     * Permite actualizar una reserva específica por su índice.
     */
    private static void actualizarReserva() {
        verTodasLasReservas();
        List<String> reservas = reservaAlmacenamiento.obtenerTodasLasReservas();
        if (reservas.isEmpty()) {
            return;
        }
        try {
            System.out.print("Ingrese el número de la reserva a actualizar: ");
            int index = scanner.nextInt() - 1;
            scanner.nextLine();

            if (index < 0 || index >= reservas.size()) {
                System.err.println("Número de reserva inválido.");
                return;
            }

            System.out.print("Ingrese el nuevo detalle de la reserva: ");
            String nuevoDetalle = scanner.nextLine().trim();

            if (nuevoDetalle.isEmpty()) {
                System.err.println("El detalle no puede estar vacío.");
                return;
            }

            reservaAlmacenamiento.actualizarReserva(index, nuevoDetalle);
            System.out.println("Reserva actualizada con éxito.");
        } catch (InputMismatchException e) {
            System.err.println("Entrada inválida. Debe ser un número.");
            scanner.nextLine();
        }
    }
}
