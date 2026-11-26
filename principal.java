import java.util.Scanner;

public class principal {
    public static void main(String[] args) {
        Scanner sc  = new Scanner(System.in);
        utilidades u = new utilidades();
        GestionEstructuras gestion = new GestionEstructuras();
        Informes informes = new Informes();
        Validaciones v = new Validaciones();
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n===== MENÚ PRINCIPAL =====");
            System.out.println("1. Gestión de clientes");
            System.out.println("2. Gestión de vehículos");
            System.out.println("3. Gestión de contratos de renting");
            System.out.println("4. Imprimir informe general");
            System.out.println("5. Salir del sistema");
            System.out.print("Opción: ");
            int opcion = v.ValidarEntero(sc, 1, 5);

            switch (opcion) {
                case 1:
                    System.out.println(MenuClientes(sc));
                    break;
                case 2:
                    System.out.println(MenuVehiculos(sc));
                    break;
                case 3:
                    System.out.println(MenuContratos(sc, u, gestion));
                    break;
                case 4:
                    System.out.println(informes.imprimirInforme(gestion));
                    break;
                case 5:
                    gestion.guardarDatos();
                    System.out.println("Hasta pronto!!");
                    continuar = false;
                    break;
            
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        }
    }

public static String MenuClientes(Scanner sc){
    Validaciones v = new Validaciones();
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
                    System.out.println(registrarCliente());
                    break;
                case 2:
                    System.out.println(modificarCliente());
                    break;
                case 3:
                    System.out.println(eliminarCliente());
                    break;
                case 4:
                    System.out.println(buscarCliente());
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

public static String MenuVehiculos(Scanner sc) {
    Validaciones v = new Validaciones();
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
                    System.out.println(registrarVehiculo());
                    break;
                case 2:
                    System.out.println(modificarVehiculo());
                    break;
                case 3:
                    System.out.println(eliminarVehiculo());
                    break;
                case 4:
                    System.out.println(buscarVehiculo());
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

public static String MenuContratos(Scanner sc, utilidades u, GestionEstructuras gestion) {
        Validaciones v = new Validaciones();
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
