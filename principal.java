import java.util.Scanner;

public class principal {
    public static void main(String[] args) {
        Scanner sc  = new Scanner(System.in);
        utilidades u = new utilidades();
        GestionEstructuras gestion = new GestionEstructuras();
        Informes informes = new Informes();
        Validaciones v = new Validaciones();
        Menu menu = new Menu();
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
                    System.out.println(menu.MenuClientes(sc, u, gestion));
                    break;
                case 2:
                    System.out.println(menu.MenuVehiculos(sc, u, gestion));
                    break;
                case 3:
                    System.out.println(menu.MenuContratos(sc, u, gestion));
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
}
