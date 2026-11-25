import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class ArchivoJSON {

    private String archivoClientes = "clientes.json";
    private String archivoVehiculos = "vehiculos.json";
    private String archivoContratos = "contratos.json";
    private String archivoContratosTerminados = "contratosTerminados.json";

    public String getArchivoClientes() {
        return archivoClientes;
    }

    public void setArchivoClientes(String archivoClientes) {
        this.archivoClientes = archivoClientes;
    }

    public String getArchivoVehiculos() {
        return archivoVehiculos;
    }

    public void setArchivoVehiculos(String archivoVehiculos) {
        this.archivoVehiculos = archivoVehiculos;
    }

    public String getArchivoContratos() {
        return archivoContratos;
    }

    public void setArchivoContratos(String archivoContratos) {
        this.archivoContratos = archivoContratos;
    }

    public String getArchivoContratosTerminados() {
        return archivoContratosTerminados;
    }

    public void setArchivoContratosTerminados(String archivoContratosTerminados) {
        this.archivoContratosTerminados = archivoContratosTerminados;
    }

    public void guardarClientes(LinkedList<Cliente> clientes) {
        String contenido = "[\n";
        for (int i = 0; i < clientes.size(); i++) {
            Cliente c = clientes.get(i);
            contenido = contenido + "  {"
                    + "\"cedula\": \"" + limpiar(c.getCedula()) + "\", "
                    + "\"nombre\": \"" + limpiar(c.getNombre()) + "\", "
                    + "\"apellido\": \"" + limpiar(c.getApellido()) + "\", "
                    + "\"telefono\": \"" + limpiar(c.getTelefono()) + "\", "
                    + "\"direccion\": \"" + limpiar(c.getDireccion()) + "\", "
                    + "\"licencia\": \"" + limpiar(c.getLicenciaConduccion()) + "\""
                    + "}";
            if (i < clientes.size() - 1) {
                contenido = contenido + ",";
            }
            contenido = contenido + "\n";
        }
        contenido = contenido + "]\n";
        escribirArchivo(archivoClientes, contenido);
    }

    public LinkedList<Cliente> cargarClientes() {
        LinkedList<Cliente> clientes = new LinkedList<>();
        LinkedList<String> lineas = leerArchivo(archivoClientes);

        for (String linea : lineas) {
            Cliente c = new Cliente();
            c.setCedula(obtenerValor(linea, "cedula"));
            c.setNombre(obtenerValor(linea, "nombre"));
            c.setApellido(obtenerValor(linea, "apellido"));
            c.setTelefono(obtenerValor(linea, "telefono"));
            c.setDireccion(obtenerValor(linea, "direccion"));
            c.setLicenciaConduccion(obtenerValor(linea, "licencia"));
            clientes.add(c);
        }
        return clientes;
    }

    public void guardarVehiculos(LinkedList<Vehiculo> vehiculos) {
        String contenido = "[\n";
        for (int i = 0; i < vehiculos.size(); i++) {
            Vehiculo v = vehiculos.get(i);
            contenido = contenido + "  {";

            if (v instanceof CarroSedan) {
                contenido = contenido + "\"tipo\": \"Sedan\", ";
            } else if (v instanceof CamionetaSUV) {
                contenido = contenido + "\"tipo\": \"SUV\", ";
            } else {
                contenido = contenido + "\"tipo\": \"Vehiculo\", ";
            }

            contenido = contenido
                    + "\"placa\": \"" + limpiar(v.getPlaca()) + "\", "
                    + "\"marca\": \"" + limpiar(v.getMarca()) + "\", "
                    + "\"modelo\": " + v.getModelo() + ", "
                    + "\"precioDiario\": " + v.getPrecioDiario() + ", "
                    + "\"estado\": \"" + limpiar(v.getEstado()) + "\"";

            if (v instanceof CarroSedan) {
                CarroSedan sedan = (CarroSedan) v;
                contenido = contenido
                        + ", \"tipoCombustible\": \"" + limpiar(sedan.getTipoCombustible()) + "\""
                        + ", \"transmision\": \"" + limpiar(sedan.getTransmision()) + "\"";
            } else if (v instanceof CamionetaSUV) {
                CamionetaSUV suv = (CamionetaSUV) v;
                contenido = contenido
                        + ", \"traccion\": \"" + limpiar(suv.getTraccion()) + "\""
                        + ", \"capacidadMaletero\": " + suv.getCapacidadMaletero();
            }

            contenido = contenido + "}";
            if (i < vehiculos.size() - 1) {
                contenido = contenido + ",";
            }
            contenido = contenido + "\n";
        }
        contenido = contenido + "]\n";
        escribirArchivo(archivoVehiculos, contenido);
    }

    public LinkedList<Vehiculo> cargarVehiculos() {
        LinkedList<Vehiculo> vehiculos = new LinkedList<>();
        LinkedList<String> lineas = leerArchivo(archivoVehiculos);

        for (String linea : lineas) {
            try {
                String tipo = obtenerValor(linea, "tipo");
                String placa = obtenerValor(linea, "placa");
                String marca = obtenerValor(linea, "marca");
                int modelo = Integer.parseInt(obtenerValor(linea, "modelo"));
                float precioDiario = Float.parseFloat(obtenerValor(linea, "precioDiario"));
                String estado = obtenerValor(linea, "estado");

                if (tipo.equals("Sedan")) {
                    String combustible = obtenerValor(linea, "tipoCombustible");
                    String transmision = obtenerValor(linea, "transmision");
                    vehiculos.add(new CarroSedan(combustible, transmision, estado, marca, modelo, placa, precioDiario));
                } else if (tipo.equals("SUV")) {
                    String traccion = obtenerValor(linea, "traccion");
                    float maletero = Float.parseFloat(obtenerValor(linea, "capacidadMaletero"));
                    vehiculos.add(new CamionetaSUV(maletero, traccion, estado, marca, modelo, placa, precioDiario));
                } else {
                    vehiculos.add(new Vehiculo(estado, marca, modelo, placa, precioDiario));
                }
            } catch (NumberFormatException e) {
                System.out.println("Se omitió un vehículo con datos numéricos inválidos en " + archivoVehiculos);
            }
        }
        return vehiculos;
    }

    public void guardarContratos(LinkedList<ContratoRenting> contratos, String nombreArchivo) {
        String contenido = "[\n";
        for (int i = 0; i < contratos.size(); i++) {
            ContratoRenting c = contratos.get(i);
            contenido = contenido + "  {"
                    + "\"idContrato\": \"" + limpiar(c.getIdContrato()) + "\", "
                    + "\"cedulaCliente\": \"" + limpiar(c.getCedulaCliente()) + "\", "
                    + "\"placaVehiculo\": \"" + limpiar(c.getPlacaVehiculo()) + "\", "
                    + "\"fechaInicio\": \"" + limpiar(c.getFechaInicio()) + "\", "
                    + "\"fechaFin\": \"" + limpiar(c.getFechaFin()) + "\", "
                    + "\"totalDias\": " + c.getTotalDias() + ", "
                    + "\"valorTotal\": " + c.getValorTotal()
                    + "}";
            if (i < contratos.size() - 1) {
                contenido = contenido + ",";
            }
            contenido = contenido + "\n";
        }
        contenido = contenido + "]\n";
        escribirArchivo(nombreArchivo, contenido);
    }

    public LinkedList<ContratoRenting> cargarContratos(String nombreArchivo) {
        LinkedList<ContratoRenting> contratos = new LinkedList<>();
        LinkedList<String> lineas = leerArchivo(nombreArchivo);

        for (String linea : lineas) {
            try {
                ContratoRenting c = new ContratoRenting();
                c.setIdContrato(obtenerValor(linea, "idContrato"));
                c.setCedulaCliente(obtenerValor(linea, "cedulaCliente"));
                c.setPlacaVehiculo(obtenerValor(linea, "placaVehiculo"));
                c.setFechaInicio(obtenerValor(linea, "fechaInicio"));
                c.setFechaFin(obtenerValor(linea, "fechaFin"));
                c.setTotalDias(Integer.parseInt(obtenerValor(linea, "totalDias")));
                c.setValorTotal(Float.parseFloat(obtenerValor(linea, "valorTotal")));
                contratos.add(c);
            } catch (NumberFormatException e) {
                System.out.println("Se omitió un contrato con datos numéricos inválidos en " + nombreArchivo);
            }
        }
        return contratos;
    }

    private void escribirArchivo(String nombreArchivo, String contenido) {
        try {
            FileWriter escritor = new FileWriter(nombreArchivo);
            escritor.write(contenido);
            escritor.close();
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo " + nombreArchivo + ": " + e.getMessage());
        }
    }

    private LinkedList<String> leerArchivo(String nombreArchivo) {
        LinkedList<String> lineas = new LinkedList<>();
        try {
            BufferedReader lector = new BufferedReader(new FileReader(nombreArchivo));
            String linea = lector.readLine();
            while (linea != null) {
                if (linea.contains("{")) {
                    lineas.add(linea);
                }
                linea = lector.readLine();
            }
            lector.close();
        } catch (IOException e) {
            System.out.println("Error al leer el archivo " + nombreArchivo + ": " + e.getMessage());
        }
        return lineas;
    }

    private String obtenerValor(String linea, String clave) {
        String buscar = "\"" + clave + "\":";
        int posicion = linea.indexOf(buscar);
        if (posicion == -1) {
            return "";
        }
        int inicio = posicion + buscar.length();

        while (inicio < linea.length() && linea.charAt(inicio) == ' ') {
            inicio++;
        }

        if (inicio < linea.length() && linea.charAt(inicio) == '"') {
            // Es un texto: va hasta la siguiente comilla
            int fin = linea.indexOf("\"", inicio + 1);
            return linea.substring(inicio + 1, fin);
        } else {
            int fin = inicio;
            while (fin < linea.length() && linea.charAt(fin) != ',' && linea.charAt(fin) != '}') {
                fin++;
            }
            return linea.substring(inicio, fin).trim();
        }
    }

    private String limpiar(String texto) {
        if (texto == null) {
            return "";
        }
        return texto.replace("\"", "'");
    }
}
