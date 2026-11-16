import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;

public class utilidades {

    public Cliente buscarCliente(LinkedList<Cliente> clientes, String cedula) {
        for (Cliente c : clientes) {
            if (c.getCedula().equals(cedula)) {
                return c;
            }
        }
        return null;
    }

    public Vehiculo buscarVehiculo(LinkedList<Vehiculo> vehiculos, String placa) {
        for (Vehiculo v : vehiculos) {
            if (v.getPlaca().equalsIgnoreCase(placa)) {
                return v;
            }
        }
        return null;
    }

    public ContratoRenting buscarContrato(LinkedList<ContratoRenting> contratos, String idContrato) {
        for (ContratoRenting c : contratos) {
            if (c.getIdContrato().equals(idContrato)) {
                return c;
            }
        }
        return null;
    }

    public boolean clienteTieneContratoActivo(LinkedList<ContratoRenting> contratos, String cedula) {
        for (ContratoRenting c : contratos) {
            if (c.getCedulaCliente().equals(cedula)) {
                return true;
            }
        }
        return false;
    }

    public LocalDate convertirFecha(String fecha) {
        try {
            return LocalDate.parse(fecha);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public int calcularDias(String fechaInicio, String fechaFin) {
        LocalDate inicio = convertirFecha(fechaInicio);
        LocalDate fin = convertirFecha(fechaFin);
        if (inicio == null || fin == null || !fin.isAfter(inicio)) {
            return -1;
        }
        return (int) ChronoUnit.DAYS.between(inicio, fin);
    }

    public float calcularValorTotal(Vehiculo vehiculo, int dias) {
        return vehiculo.getPrecioDiario() * dias;
    }

    public boolean registrarContrato(LinkedList<ContratoRenting> contratos,
                                            LinkedList<Cliente> clientes,
                                            LinkedList<Vehiculo> vehiculos,
                                            String idContrato, String cedula, String placa,
                                            String fechaInicio, String fechaFin) {
        if (buscarContrato(contratos, idContrato) != null) {
            System.out.println("Ya existe un contrato con el Id " + idContrato);
            return false;
        }

        Cliente cliente = buscarCliente(clientes, cedula);
        if (cliente == null) {
            System.out.println("No existe un cliente con la cédula " + cedula);
            return false;
        }

        Vehiculo vehiculo = buscarVehiculo(vehiculos, placa);
        if (vehiculo == null) {
            System.out.println("No existe un vehículo con la placa " + placa);
            return false;
        }

        if (clienteTieneContratoActivo(contratos, cedula)) {
            System.out.println("El cliente ya tiene un vehículo alquilado. No puede alquilar otro.");
            return false;
        }

        if (vehiculo.getEstado().equalsIgnoreCase("Alquilado")) {
            System.out.println("El vehículo " + placa + " ya está alquilado.");
            return false;
        }

        int dias = calcularDias(fechaInicio, fechaFin);
        if (dias <= 0) {
            System.out.println("Fechas inválidas. Use el formato yyyy-MM-dd y la fecha fin debe ser posterior a la de inicio.");
            return false;
        }

        float valor = calcularValorTotal(vehiculo, dias);
        ContratoRenting contrato = new ContratoRenting();
        contrato.setIdContrato(idContrato);
        contrato.setCedulaCliente(cedula);
        contrato.setPlacaVehiculo(vehiculo.getPlaca());
        contrato.setFechaInicio(fechaInicio);
        contrato.setFechaFin(fechaFin);
        contrato.setTotalDias(dias);
        contrato.setValorTotal(valor);
        contratos.add(contrato);
        vehiculo.setEstado("Alquilado");

        System.out.println("Contrato registrado con éxito:");
        System.out.println(contrato.toString());
        return true;
    }

    public boolean modificarFechasContrato(LinkedList<ContratoRenting> contratos,
                                                  LinkedList<Vehiculo> vehiculos,
                                                  String idContrato,
                                                  String nuevaFechaInicio, String nuevaFechaFin) {
        ContratoRenting contrato = buscarContrato(contratos, idContrato);
        if (contrato == null) {
            System.out.println("No existe un contrato activo con el Id " + idContrato);
            return false;
        }

        int dias = calcularDias(nuevaFechaInicio, nuevaFechaFin);
        if (dias <= 0) {
            System.out.println("Fechas inválidas. Use el formato yyyy-MM-dd y la fecha fin debe ser posterior a la de inicio.");
            return false;
        }

        Vehiculo vehiculo = buscarVehiculo(vehiculos, contrato.getPlacaVehiculo());
        contrato.setFechaInicio(nuevaFechaInicio);
        contrato.setFechaFin(nuevaFechaFin);
        contrato.setTotalDias(dias);
        contrato.setValorTotal(calcularValorTotal(vehiculo, dias));

        System.out.println("Contrato modificado:");
        System.out.println(contrato.toString());
        return true;
    }

    public boolean modificarDuracionContrato(LinkedList<ContratoRenting> contratos,
                                                    LinkedList<Vehiculo> vehiculos,
                                                    String idContrato, int nuevosDias) {
        ContratoRenting contrato = buscarContrato(contratos, idContrato);
        if (contrato == null) {
            System.out.println("No existe un contrato activo con el Id " + idContrato);
            return false;
        }
        if (nuevosDias <= 0) {
            System.out.println("La duración debe ser mayor a 0 días.");
            return false;
        }

        LocalDate inicio = convertirFecha(contrato.getFechaInicio());
        Vehiculo vehiculo = buscarVehiculo(vehiculos, contrato.getPlacaVehiculo());
        contrato.setFechaFin(inicio.plusDays(nuevosDias).toString());
        contrato.setTotalDias(nuevosDias);
        contrato.setValorTotal(calcularValorTotal(vehiculo, nuevosDias));

        System.out.println("Contrato modificado:");
        System.out.println(contrato.toString());
        return true;
    }

    public boolean devolverVehiculo(LinkedList<ContratoRenting> contratos,
                                           LinkedList<ContratoRenting> contratosTerminados,
                                           LinkedList<Vehiculo> vehiculos,
                                           String idContrato) {
        ContratoRenting contrato = buscarContrato(contratos, idContrato);
        if (contrato == null) {
            if (buscarContrato(contratosTerminados, idContrato) != null) {
                System.out.println("El vehículo de este contrato ya fue devuelto.");
            } else {
                System.out.println("No existe el contrato " + idContrato);
            }
            return false;
        }

        Vehiculo vehiculo = buscarVehiculo(vehiculos, contrato.getPlacaVehiculo());
        if (vehiculo != null) {
            vehiculo.setEstado("Disponible");
        }
        contratos.remove(contrato);
        contratosTerminados.add(contrato);

        System.out.println("Vehículo " + contrato.getPlacaVehiculo() + " devuelto. Contrato finalizado.");
        return true;
    }

    public void consultarContrato(LinkedList<ContratoRenting> contratos, String idContrato) {
        ContratoRenting contrato = buscarContrato(contratos, idContrato);
        if (contrato == null) {
            System.out.println("No existe el contrato " + idContrato);
        } else {
            System.out.println(contrato);
        }
    }

    public void consultarContratosPorCliente(LinkedList<ContratoRenting> contratos, String cedula) {
        boolean encontrado = false;
        for (ContratoRenting c : contratos) {
            if (c.getCedulaCliente().equals(cedula)) {
                System.out.println(c);
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("El cliente " + cedula + " no tiene contratos registrados.");
        }
    }

    public void listarContratos(LinkedList<ContratoRenting> contratos) {
        if (contratos.isEmpty()) {
            System.out.println("No hay contratos registrados.");
            return;
        }
        for (ContratoRenting c : contratos) {
            System.out.println(c);
        }
    }

    public void consultarContratosTerminados(LinkedList<ContratoRenting> contratosTerminados) {
        if (contratosTerminados.isEmpty()) {
            System.out.println("No hay contratos terminados.");
            return;
        }
        for (ContratoRenting c : contratosTerminados) {
            System.out.println(c);
        }
    }
}
