import java.util.LinkedList;

public class GestionEstructuras {

    private LinkedList<Cliente> clientes = new LinkedList<>();
    private LinkedList<Vehiculo> vehiculos = new LinkedList<>();
    private LinkedList<ContratoRenting> contratos = new LinkedList<>();
    private LinkedList<ContratoRenting> contratosTerminados = new LinkedList<>();
    
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

    public String agregarCliente (Cliente cliente){
        if (cliente == null){
            return "No se pudo agregar cliente";
        }else{
            clientes.add(cliente);
            return "Cliente agregado correctamente";
        }
    }

    public String agregarVehiculo (Vehiculo vehiculo){
        if (vehiculo == null){
            return "No se pudo agregar vehiculo";
        }else{
            vehiculos.add(vehiculo);
            return "Vehiculo agregado correctamente";
        }
    }

    public String agregarContrato (ContratoRenting contrato){
        if (contrato == null){
            return "No se pudo agregar contrato";
        }else{
            contratos.add(contrato);
            return "Contrato agregado correctamente";
        }
    }

    public String eliminarCliente(String cedula){
        Cliente clienteEncontrado = null;
        ContratoRenting contratoEncontrado = null;

        for (Cliente cliente : clientes){
            if (cliente.getCedula().equals(cedula)){
                clienteEncontrado = cliente;
                break;
            }
        }

        if (clienteEncontrado == null){
            return "No existe cliente con esa cédula.";
        }    
        for (ContratoRenting contrato : contratos){
            if (contrato.getCedulaCliente().equals(clienteEncontrado.getCedula())){
                contratoEncontrado = contrato;
                break;
            }
        }
        if (contratoEncontrado != null){
            for(Vehiculo vehiculo : vehiculos){
                if (vehiculo.getPlaca().equalsIgnoreCase(contratoEncontrado.getPlacaVehiculo())){
                    vehiculo.setEstado("Disponible");
                    break;
                }
            }
            contratosTerminados.add(contratoEncontrado);
            contratos.remove(contratoEncontrado);
        }
        clientes.remove(clienteEncontrado);
    
        return "Cliente eliminado y contrato terminado.";
    }

    public String eliminarVehiculo (String placa){
        Vehiculo vehiculoEncontrado = null;
        ContratoRenting contratoEncontrado = null;

        for (Vehiculo vehiculo : vehiculos){
            if (vehiculo.getPlaca().equalsIgnoreCase(placa)){
                vehiculoEncontrado = vehiculo;
                break;
            }
        }

        if (vehiculoEncontrado == null){
            return "No existe registro con la placa " + placa;
        }

        for(ContratoRenting contrato : contratos){
            if (contrato.getPlacaVehiculo().equalsIgnoreCase(vehiculoEncontrado.getPlaca())){
                contratoEncontrado = contrato;
                break;
            }
        }
        if (contratoEncontrado != null){
            contratosTerminados.add(contratoEncontrado);
            contratos.remove(contratoEncontrado);
        }
        vehiculos.remove(vehiculoEncontrado);
        return "Vehiculo eliminado correctamente.";
    }

    public String eliminarContrato(String idContrato){
        ContratoRenting contratoEncontrado = null;

        for (ContratoRenting contrato : contratos){
            if (contrato.getIdContrato().equalsIgnoreCase(idContrato)){
                contratoEncontrado = contrato;
                break;
            }
        }
        
        if (contratoEncontrado == null){
            return "No existe contrato.";
        }
        for (Vehiculo vehiculo : vehiculos){
            if (vehiculo.getPlaca().equalsIgnoreCase(contratoEncontrado.getPlacaVehiculo())){
                vehiculo.setEstado("Disponible");
                break;
            }
        }
        contratosTerminados.add(contratoEncontrado);
        contratos.remove(contratoEncontrado);
        return "Contrato eliminado correctamente.";
    }
}


                