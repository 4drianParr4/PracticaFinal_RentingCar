import java.util.LinkedList;

public class Informes {

    public String imprimir(Cliente cliente) {
        if (cliente == null) {
            return "Cliente no encontrado.";
        }
        return cliente.toString();
    }

    public String imprimir(Vehiculo vehiculo) {
        if (vehiculo == null) {
            return "Vehículo no encontrado.";
        }
        return vehiculo.toString();
    }

    public String imprimir(ContratoRenting contrato) {
        if (contrato == null) {
            return "Contrato no encontrado.";
        }
        return contrato.toString();
    }

    public String imprimirInforme(GestionEstructuras gestion) {
        gestion.guardarDatos();

        ArchivoJSON archivo = gestion.getArchivo();
        LinkedList<Cliente> clientes = archivo.cargarClientes();
        LinkedList<Vehiculo> vehiculos = archivo.cargarVehiculos();
        LinkedList<ContratoRenting> activos = archivo.cargarContratos(archivo.getArchivoContratos());
        LinkedList<ContratoRenting> finalizados = archivo.cargarContratos(archivo.getArchivoContratosTerminados());

        String informe = "\n==================================================\n";
        informe = informe + "   INFORME GENERAL - SISTEMA DE RENTING DE CARROS\n";
        informe = informe + "==================================================\n";
        informe = informe + seccionClientes(clientes);
        informe = informe + seccionVehiculos(vehiculos);
        informe = informe + seccionContratos("CONTRATOS ACTIVOS", activos);
        informe = informe + seccionContratos("CONTRATOS FINALIZADOS", finalizados);
        informe = informe + seccionResumen(clientes, vehiculos, activos, finalizados);
        return informe;
    }

    private String seccionClientes(LinkedList<Cliente> clientes) {
        String texto = encabezado("CLIENTES REGISTRADOS", clientes.size());
        if (clientes.isEmpty()) {
            return texto + "  No hay clientes registrados.\n";
        }

        clientes.sort((a, b) -> a.getCedula().compareToIgnoreCase(b.getCedula()));

        int numero = 1;
        for (Cliente cliente : clientes) {
            texto = texto + "  " + numero + ". " + imprimir(cliente) + "\n";
            numero++;
        }
        return texto;
    }

    private String seccionVehiculos(LinkedList<Vehiculo> vehiculos) {
        String texto = encabezado("VEHÍCULOS REGISTRADOS", vehiculos.size());
        if (vehiculos.isEmpty()) {
            return texto + "  No hay vehículos registrados.\n";
        }

        vehiculos.sort((a, b) -> a.getPlaca().compareToIgnoreCase(b.getPlaca()));

        int numero = 1;
        for (Vehiculo vehiculo : vehiculos) {
            texto = texto + "  " + numero + ". " + imprimir(vehiculo) + "\n";
            numero++;
        }
        return texto;
    }

    private String seccionContratos(String titulo, LinkedList<ContratoRenting> contratos) {
        String texto = encabezado(titulo, contratos.size());
        if (contratos.isEmpty()) {
            return texto + "  No hay registros en este grupo.\n";
        }

        contratos.sort((a, b) -> a.getIdContrato().compareToIgnoreCase(b.getIdContrato()));

        int numero = 1;
        for (ContratoRenting contrato : contratos) {
            texto = texto + "  " + numero + ". " + imprimir(contrato) + "\n";
            numero++;
        }
        texto = texto + "  Subtotal: $" + String.format("%,.2f", calcularIngresos(contratos, 0)) + "\n";
        return texto;
    }

    private String seccionResumen(LinkedList<Cliente> clientes, LinkedList<Vehiculo> vehiculos,
                                  LinkedList<ContratoRenting> activos, LinkedList<ContratoRenting> finalizados) {
        float ingresosActivos = calcularIngresos(activos, 0);
        float ingresosFinalizados = calcularIngresos(finalizados, 0);
        float totalIngresos = ingresosActivos + ingresosFinalizados;

        String texto = "\n==================================================\n";
        texto = texto + "  RESUMEN GENERAL\n";
        texto = texto + "--------------------------------------------------\n";
        texto = texto + "  Total clientes: " + clientes.size() + "\n";
        texto = texto + "  Total vehículos: " + vehiculos.size()
                + " (Disponibles: " + contarVehiculosPorEstado(vehiculos, "Disponible", 0)
                + " | Alquilados: " + contarVehiculosPorEstado(vehiculos, "Alquilado", 0) + ")\n";
        texto = texto + "  Contratos activos: " + activos.size() + "\n";
        texto = texto + "  Contratos finalizados: " + finalizados.size() + "\n";
        texto = texto + "--------------------------------------------------\n";
        texto = texto + "  Ingresos contratos activos: $" + String.format("%,.2f", ingresosActivos) + "\n";
        texto = texto + "  Ingresos contratos finalizados: $" + String.format("%,.2f", ingresosFinalizados) + "\n";
        texto = texto + "  TOTAL INGRESOS POR RENTING: $" + String.format("%,.2f", totalIngresos) + "\n";
        texto = texto + "==================================================\n";
        return texto;
    }

    private String encabezado(String titulo, int cantidad) {
        return "\n--------------------------------------------------\n"
                + "  " + titulo + " (" + cantidad + ")\n"
                + "--------------------------------------------------\n";
    }

    public float calcularIngresos(LinkedList<ContratoRenting> contratos, int indice) {
        if (indice >= contratos.size()) {
            return 0;
        }
        return contratos.get(indice).getValorTotal() + calcularIngresos(contratos, indice + 1);
    }

    public int contarVehiculosPorEstado(LinkedList<Vehiculo> vehiculos, String estado, int indice) {
        if (indice >= vehiculos.size()) {
            return 0;
        }
        int cuenta = 0;
        if (vehiculos.get(indice).getEstado().equalsIgnoreCase(estado)) {
            cuenta = 1;
        }
        return cuenta + contarVehiculosPorEstado(vehiculos, estado, indice + 1);
    }
}
