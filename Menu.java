import java.util.Scanner;

public class Menu {

    private Validaciones v = new Validaciones();

    public String MenuClientes(Scanner sc, utilidades u, GestionEstructuras gestion) {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n--- GESTIÓN DE CLIENTES ---");
            System.out.println("1. Registrar cliente");
            System.out.println("2. Modificar cliente");
            System.out.println("3. Eliminar cliente");
            System.out.println("4. Buscar cliente");
            System.out.println("5. Volver al menú principal");
            System.out.print("Digite la opcion que desea: ");
            int opcion = v.ValidarEntero(sc, 1, 5);

            switch (opcion) {
                case 1:
                    System.out.println(u.registrarCliente(sc, gestion));
                    break;
                case 2:
                    System.out.println(u.modificarCliente(sc, gestion));
                    break;
                case 3:
                    System.out.println(u.eliminarCliente(sc, gestion));
                    break;
                case 4:
                    System.out.println(u.buscarCliente(sc, gestion));
                    break;
                case 5:
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        }
        return "Regresando al menú principal...";
    }

    public String MenuVehiculos(Scanner sc, utilidades u, GestionEstructuras gestion) {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n--- GESTIÓN DE VEHÍCULOS ---");
            System.out.println("1. Registrar vehículo");
            System.out.println("2. Modificar vehículo");
            System.out.println("3. Eliminar vehículo");
            System.out.println("4. Buscar vehículo");
            System.out.println("5. Volver al menú principal");
            System.out.print("Digite la opcion que desea: ");
            int opcion = v.ValidarEntero(sc, 1, 5);

            switch (opcion) {
                case 1:
                    System.out.println(u.registrarVehiculo(sc, gestion));
                    break;
                case 2:
                    System.out.println(u.modificarVehiculo(sc, gestion));
                    break;
                case 3:
                    System.out.println(u.eliminarVehiculo(sc, gestion));
                    break;
                case 4:
                    System.out.println(u.buscarVehiculo(sc, gestion));
                    break;
                case 5:
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        }
        return "Regresando al menú principal...";
    }

    public String MenuContratos(Scanner sc, utilidades u, GestionEstructuras gestion) {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n--- GESTIÓN DE CONTRATOS DE RENTING ---");
            System.out.println("1. Registrar nuevo contrato");
            System.out.println("2. Modificar contrato");
            System.out.println("3. Finalizar contrato (devolución)");
            System.out.println("4. Buscar contrato");
            System.out.println("5. Volver al menú principal");
            System.out.print("Digite la opcion que desea: ");
            int opcion = v.ValidarEntero(sc, 1, 5);

            switch (opcion) {
                case 1:
                    System.out.println(u.registrarContrato(sc, gestion));
                    break;
                case 2:
                    System.out.println(u.modificarContrato(sc, gestion));
                    break;
                case 3:
                    System.out.println(u.finalizarContrato(sc, gestion));
                    break;
                case 4:
                    System.out.println(u.buscarContrato(sc, gestion));
                    break;
                case 5:
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        }
        return "Regresando al menú principal...";
    }
}
