import java.util.LinkedList;

public class GestionEstructuras {

    private LinkedList<Cliente> clientes;
    private LinkedList<Vehiculo> vehiculos;
    private LinkedList<ContratoRenting> contratos;
    private LinkedList<ContratoRenting> contratosTerminados;
    private ArchivoJSON archivo;

    public GestionEstructuras() {
        archivo = new ArchivoJSON();
        cargarDatos();
    }

    public void cargarDatos() {
        clientes = archivo.cargarClientes();
        vehiculos = archivo.cargarVehiculos();
        contratos = archivo.cargarContratos(archivo.getArchivoContratos());
        contratosTerminados = archivo.cargarContratos(archivo.getArchivoContratosTerminados());
    }

    public void guardarDatos() {
        archivo.guardarClientes(clientes);
        archivo.guardarVehiculos(vehiculos);
        archivo.guardarContratos(contratos, archivo.getArchivoContratos());
        archivo.guardarContratos(contratosTerminados, archivo.getArchivoContratosTerminados());
    }

    public ArchivoJSON getArchivo() {
        return archivo;
    }

    public LinkedList<Cliente> getClientes() {
        return clientes;
    }

    public LinkedList<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public LinkedList<ContratoRenting> getContratos() {
        return contratos;
    }

    public LinkedList<ContratoRenting> getContratosTerminados() {
        return contratosTerminados;
    }

    public Cliente buscarCliente(String cedula) {
        for (Cliente cliente : clientes) {
            if (cliente.getCedula().equals(cedula)) {
                return cliente;
            }
        }
        return null;
    }

    public Vehiculo buscarVehiculo(String placa) {
        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo.getPlaca().equalsIgnoreCase(placa)) {
                return vehiculo;
            }
        }
        return null;
    }

    public ContratoRenting buscarContrato(String idContrato) {
        for (ContratoRenting contrato : contratos) {
            if (contrato.getIdContrato().equalsIgnoreCase(idContrato)) {
                return contrato;
            }
        }
        return null;
    }

    public ContratoRenting buscarContratoTerminado(String idContrato) {
        for (ContratoRenting contrato : contratosTerminados) {
            if (contrato.getIdContrato().equalsIgnoreCase(idContrato)) {
                return contrato;
            }
        }
        return null;
    }

    public ContratoRenting buscarContratoActivoCliente(String cedula) {
        for (ContratoRenting contrato : contratos) {
            if (contrato.getCedulaCliente().equals(cedula)) {
                return contrato;
            }
        }
        return null;
    }

    public ContratoRenting buscarContratoActivoVehiculo(String placa) {
        for (ContratoRenting contrato : contratos) {
            if (contrato.getPlacaVehiculo().equalsIgnoreCase(placa)) {
                return contrato;
            }
        }
        return null;
    }

    public String agregarCliente(Cliente cliente) {
        if (cliente == null) {
            return "No se pudo agregar cliente";
        }
        if (buscarCliente(cliente.getCedula()) != null) {
            return "Ya existe un cliente con la cédula " + cliente.getCedula();
        }
        clientes.add(cliente);
        guardarDatos();
        return "Cliente agregado correctamente";
    }

    public String agregarVehiculo(Vehiculo vehiculo) {
        if (vehiculo == null) {
            return "No se pudo agregar vehiculo";
        }
        if (buscarVehiculo(vehiculo.getPlaca()) != null) {
            return "Ya existe un vehículo con la placa " + vehiculo.getPlaca();
        }
        vehiculos.add(vehiculo);
        guardarDatos();
        return "Vehiculo agregado correctamente";
    }

    public String agregarContrato(ContratoRenting contrato) {
        if (contrato == null) {
            return "No se pudo agregar contrato";
        }
        Vehiculo vehiculo = buscarVehiculo(contrato.getPlacaVehiculo());
        if (vehiculo != null) {
            vehiculo.setEstado("Alquilado");
        }
        contratos.add(contrato);
        guardarDatos();
        return "Contrato agregado correctamente";
    }

    public String modificarContrato(String idContrato, String fechaInicio, String fechaFin, int totalDias, float valorTotal) {
        ContratoRenting contrato = buscarContrato(idContrato);
        if (contrato == null) {
            return "No existe un contrato activo con el Id " + idContrato;
        }
        contrato.setFechaInicio(fechaInicio);
        contrato.setFechaFin(fechaFin);
        contrato.setTotalDias(totalDias);
        contrato.setValorTotal(valorTotal);
        guardarDatos();
        return "Contrato modificado:\n" + contrato;
    }

    public String finalizarContrato(String idContrato) {
        ContratoRenting contrato = buscarContrato(idContrato);
        if (contrato == null) {
            if (buscarContratoTerminado(idContrato) != null) {
                return "El vehículo de este contrato ya fue devuelto.";
            }
            return "No existe el contrato " + idContrato;
        }
        Vehiculo vehiculo = buscarVehiculo(contrato.getPlacaVehiculo());
        if (vehiculo != null) {
            vehiculo.setEstado("Disponible");
        }
        contratos.remove(contrato);
        contratosTerminados.push(contrato);
        guardarDatos();
        return "Vehículo " + contrato.getPlacaVehiculo() + " devuelto. Contrato finalizado.";
    }

    public String eliminarCliente(String cedula) {
        Cliente cliente = buscarCliente(cedula);
        if (cliente == null) {
            return "No existe cliente con esa cédula.";
        }
        String mensaje = "Cliente eliminado correctamente.";
        ContratoRenting contrato = buscarContratoActivoCliente(cedula);
        if (contrato != null) {
            finalizarContrato(contrato.getIdContrato());
            mensaje = "Cliente eliminado y su contrato " + contrato.getIdContrato() + " fue finalizado.";
        }
        clientes.remove(cliente);
        guardarDatos();
        return mensaje;
    }

    public String eliminarVehiculo(String placa) {
        Vehiculo vehiculo = buscarVehiculo(placa);
        if (vehiculo == null) {
            return "No existe registro con la placa " + placa;
        }
        String mensaje = "Vehiculo eliminado correctamente.";
        ContratoRenting contrato = buscarContratoActivoVehiculo(placa);
        if (contrato != null) {
            finalizarContrato(contrato.getIdContrato());
            mensaje = "Vehiculo eliminado y su contrato " + contrato.getIdContrato() + " fue finalizado.";
        }
        vehiculos.remove(vehiculo);
        guardarDatos();
        return mensaje;
    }
}
