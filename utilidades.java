import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class utilidades {

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

    public String registrarContrato(GestionEstructuras gestion, String idContrato, String cedula, String placa,
                                    String fechaInicio, String fechaFin) {
        if (gestion.buscarContrato(idContrato) != null || gestion.buscarContratoTerminado(idContrato) != null) {
            return "Ya existe un contrato con el Id " + idContrato;
        }

        Cliente cliente = gestion.buscarCliente(cedula);
        if (cliente == null) {
            return "No existe un cliente con la cédula " + cedula;
        }

        Vehiculo vehiculo = gestion.buscarVehiculo(placa);
        if (vehiculo == null) {
            return "No existe un vehículo con la placa " + placa;
        }

        if (gestion.buscarContratoActivoCliente(cedula) != null) {
            return "El cliente ya tiene un vehículo alquilado. No puede alquilar otro.";
        }

        if (vehiculo.getEstado().equalsIgnoreCase("Alquilado")) {
            return "El vehículo " + placa + " ya está alquilado.";
        }

        int dias = calcularDias(fechaInicio, fechaFin);
        if (dias <= 0) {
            return "Fechas inválidas. Use el formato yyyy-MM-dd y la fecha fin debe ser posterior a la de inicio.";
        }

        ContratoRenting contrato = new ContratoRenting();
        contrato.setIdContrato(idContrato);
        contrato.setCedulaCliente(cedula);
        contrato.setPlacaVehiculo(vehiculo.getPlaca());
        contrato.setFechaInicio(fechaInicio);
        contrato.setFechaFin(fechaFin);
        contrato.setTotalDias(dias);
        contrato.setValorTotal(calcularValorTotal(vehiculo, dias));
        gestion.agregarContrato(contrato);

        return "Contrato registrado con éxito:\n" + contrato;
    }

    public String modificarFechasContrato(GestionEstructuras gestion, String idContrato,
                                          String nuevaFechaInicio, String nuevaFechaFin) {
        ContratoRenting contrato = gestion.buscarContrato(idContrato);
        if (contrato == null) {
            return "No existe un contrato activo con el Id " + idContrato;
        }

        int dias = calcularDias(nuevaFechaInicio, nuevaFechaFin);
        if (dias <= 0) {
            return "Fechas inválidas. Use el formato yyyy-MM-dd y la fecha fin debe ser posterior a la de inicio.";
        }

        Vehiculo vehiculo = gestion.buscarVehiculo(contrato.getPlacaVehiculo());
        if (vehiculo == null) {
            return "No existe el vehículo del contrato.";
        }
        return gestion.modificarContrato(idContrato, nuevaFechaInicio, nuevaFechaFin, dias,
                calcularValorTotal(vehiculo, dias));
    }

    public String modificarDuracionContrato(GestionEstructuras gestion, String idContrato, int nuevosDias) {
        ContratoRenting contrato = gestion.buscarContrato(idContrato);
        if (contrato == null) {
            return "No existe un contrato activo con el Id " + idContrato;
        }
        if (nuevosDias <= 0) {
            return "La duración debe ser mayor a 0 días.";
        }

        Vehiculo vehiculo = gestion.buscarVehiculo(contrato.getPlacaVehiculo());
        if (vehiculo == null) {
            return "No existe el vehículo del contrato.";
        }
        LocalDate inicio = convertirFecha(contrato.getFechaInicio());
        String nuevaFechaFin = inicio.plusDays(nuevosDias).toString();
        return gestion.modificarContrato(idContrato, contrato.getFechaInicio(), nuevaFechaFin, nuevosDias,
                calcularValorTotal(vehiculo, nuevosDias));
    }

    public String devolverVehiculo(GestionEstructuras gestion, String idContrato) {
        return gestion.finalizarContrato(idContrato);
    }

    public String consultarContrato(GestionEstructuras gestion, String idContrato) {
        ContratoRenting contrato = gestion.buscarContrato(idContrato);
        if (contrato != null) {
            return "[ACTIVO] " + contrato;
        }
        contrato = gestion.buscarContratoTerminado(idContrato);
        if (contrato != null) {
            return "[FINALIZADO] " + contrato;
        }
        return "No existe el contrato " + idContrato;
    }

    public String consultarContratosPorCliente(GestionEstructuras gestion, String cedula) {
        String resultado = "";
        for (ContratoRenting c : gestion.getContratos()) {
            if (c.getCedulaCliente().equals(cedula)) {
                resultado = resultado + "[ACTIVO] " + c + "\n";
            }
        }
        for (ContratoRenting c : gestion.getContratosTerminados()) {
            if (c.getCedulaCliente().equals(cedula)) {
                resultado = resultado + "[FINALIZADO] " + c + "\n";
            }
        }
        if (resultado.isEmpty()) {
            return "El cliente " + cedula + " no tiene contratos registrados.";
        }
        return resultado;
    }

    public String registrarContrato(Scanner sc, GestionEstructuras gestion) {
        System.out.print("Id del contrato: ");
        String idContrato = sc.next();
        System.out.print("Cédula del cliente: ");
        String cedula = sc.next();
        System.out.print("Placa del vehículo: ");
        String placa = sc.next();
        System.out.print("Fecha de inicio (yyyy-MM-dd): ");
        String fechaInicio = sc.next();
        System.out.print("Fecha de fin (yyyy-MM-dd): ");
        String fechaFin = sc.next();
        return registrarContrato(gestion, idContrato, cedula, placa, fechaInicio, fechaFin);
    }

    public String modificarContrato(Scanner sc, GestionEstructuras gestion) {
        System.out.print("Id del contrato a modificar: ");
        String idContrato = sc.next();
        if (gestion.buscarContrato(idContrato) == null) {
            return "No existe un contrato activo con el Id " + idContrato;
        }
        System.out.println("1. Modificar fechas");
        System.out.println("2. Modificar duración (días)");
        System.out.print("Digite la opcion que desea: ");
        String opcion = sc.next();

        if (opcion.equals("1")) {
            System.out.print("Nueva fecha de inicio (yyyy-MM-dd): ");
            String fechaInicio = sc.next();
            System.out.print("Nueva fecha de fin (yyyy-MM-dd): ");
            String fechaFin = sc.next();
            return modificarFechasContrato(gestion, idContrato, fechaInicio, fechaFin);
        } else if (opcion.equals("2")) {
            System.out.print("Nueva cantidad de días: ");
            try {
                int dias = Integer.parseInt(sc.next());
                return modificarDuracionContrato(gestion, idContrato, dias);
            } catch (NumberFormatException e) {
                return "La cantidad de días debe ser un número entero.";
            }
        }
        return "Opción no válida";
    }

    public String finalizarContrato(Scanner sc, GestionEstructuras gestion) {
        System.out.print("Id del contrato a finalizar: ");
        String idContrato = sc.next();
        return devolverVehiculo(gestion, idContrato);
    }

    public String buscarContrato(Scanner sc, GestionEstructuras gestion) {
        System.out.println("1. Buscar por Id de contrato");
        System.out.println("2. Buscar por cédula del cliente");
        System.out.print("Digite la opcion que desea: ");
        String opcion = sc.next();

        if (opcion.equals("1")) {
            System.out.print("Id del contrato: ");
            return consultarContrato(gestion, sc.next());
        } else if (opcion.equals("2")) {
            System.out.print("Cédula del cliente: ");
            return consultarContratosPorCliente(gestion, sc.next());
        }
        return "Opción no válida";
    }
}
