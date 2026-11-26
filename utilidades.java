import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.Scanner;

public class utilidades {

    private Validaciones v = new Validaciones();
    private Informes informes = new Informes();

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

    public String pedirCedula(Scanner sc) {
        System.out.print("Cédula del cliente: ");
        return v.ValidarSoloNumeros(sc, 10);
    }

    public Cliente crearCliente(Scanner sc, String cedula) {
        Cliente cliente = new Cliente();
        cliente.setCedula(cedula);
        System.out.print("Nombre: ");
        cliente.setNombre(v.ValidarSoloLetras(sc, 30));
        System.out.print("Apellido: ");
        cliente.setApellido(v.ValidarSoloLetras(sc, 30));
        System.out.print("Teléfono: ");
        cliente.setTelefono(v.ValidarSoloNumeros(sc, 10));
        System.out.print("Dirección: ");
        cliente.setDireccion(v.ValidarTexto(sc, 50));
        System.out.print("Licencia de conducción: ");
        cliente.setLicenciaConduccion(v.ValidarAlfanumerico(sc, 15));
        return cliente;
    }

    public String registrarCliente(Scanner sc, GestionEstructuras gestion) {
        String cedula = pedirCedula(sc);
        if (gestion.buscarCliente(cedula) != null) {
            return "Ya existe un cliente con la cédula " + cedula;
        }
        Cliente cliente = crearCliente(sc, cedula);
        return gestion.agregarCliente(cliente);
    }

    public int mostrarOpcionesCliente(Scanner sc) {
        System.out.println("¿Qué dato desea modificar?");
        System.out.println("1. Nombre");
        System.out.println("2. Apellido");
        System.out.println("3. Teléfono");
        System.out.println("4. Dirección");
        System.out.println("5. Licencia de conducción");
        System.out.print("Digite la opcion que desea: ");
        return v.ValidarEntero(sc, 1, 5);
    }

    public void modificarDatoCliente(Scanner sc, Cliente cliente, int opcion) {
        switch (opcion) {
            case 1:
                System.out.print("Nuevo nombre: ");
                cliente.setNombre(v.ValidarSoloLetras(sc, 30));
                break;
            case 2:
                System.out.print("Nuevo apellido: ");
                cliente.setApellido(v.ValidarSoloLetras(sc, 30));
                break;
            case 3:
                System.out.print("Nuevo teléfono: ");
                cliente.setTelefono(v.ValidarSoloNumeros(sc, 10));
                break;
            case 4:
                System.out.print("Nueva dirección: ");
                cliente.setDireccion(v.ValidarTexto(sc, 50));
                break;
            case 5:
                System.out.print("Nueva licencia de conducción: ");
                cliente.setLicenciaConduccion(v.ValidarAlfanumerico(sc, 15));
                break;
            default:
                break;
        }
    }

    public String modificarCliente(Scanner sc, GestionEstructuras gestion) {
        String cedula = pedirCedula(sc);
        Cliente cliente = gestion.buscarCliente(cedula);
        if (cliente == null) {
            return "No existe un cliente con la cédula " + cedula;
        }
        System.out.println(informes.imprimir(cliente));
        int opcion = mostrarOpcionesCliente(sc);
        modificarDatoCliente(sc, cliente, opcion);
        gestion.guardarDatos();
        return "Cliente modificado:\n" + informes.imprimir(cliente);
    }

    public String eliminarCliente(Scanner sc, GestionEstructuras gestion) {
        String cedula = pedirCedula(sc);
        return gestion.eliminarCliente(cedula);
    }

    public String buscarCliente(Scanner sc, GestionEstructuras gestion) {
        String cedula = pedirCedula(sc);
        Cliente cliente = gestion.buscarCliente(cedula);
        if (cliente == null) {
            return "No existe un cliente con la cédula " + cedula;
        }
        String resultado = informes.imprimir(cliente);
        ContratoRenting contrato = gestion.buscarContratoActivoCliente(cedula);
        if (contrato != null) {
            resultado = resultado + "\nContrato activo: " + informes.imprimir(contrato);
        } else {
            resultado = resultado + "\nContrato activo: Ninguno";
        }
        return resultado;
    }

    public int pedirTipoVehiculo(Scanner sc) {
        System.out.println("Tipo de vehículo:");
        System.out.println("1. Sedán");
        System.out.println("2. Camioneta SUV");
        System.out.print("Digite la opcion que desea: ");
        return v.ValidarEntero(sc, 1, 2);
    }

    public String pedirPlaca(Scanner sc) {
        System.out.print("Placa del vehículo: ");
        return v.ValidarAlfanumerico(sc, 6).toUpperCase();
    }

    public String pedirMarca(Scanner sc) {
        System.out.print("Marca: ");
        return v.ValidarSoloLetras(sc, 20);
    }

    public int pedirModelo(Scanner sc) {
        System.out.print("Modelo (año): ");
        return v.ValidarEntero(sc, 1990, LocalDate.now().getYear() + 1);
    }

    public float pedirPrecioDiario(Scanner sc) {
        System.out.print("Precio diario: ");
        return v.ValidarDecimalPositivo(sc);
    }

    public String pedirCombustible(Scanner sc) {
        System.out.println("Tipo de combustible:");
        System.out.println("1. Gasolina");
        System.out.println("2. Diésel");
        System.out.println("3. Eléctrico");
        System.out.print("Digite la opcion que desea: ");
        int opcion = v.ValidarEntero(sc, 1, 3);
        if (opcion == 1) {
            return "Gasolina";
        } else if (opcion == 2) {
            return "Diésel";
        }
        return "Eléctrico";
    }

    public String pedirTransmision(Scanner sc) {
        System.out.println("Transmisión:");
        System.out.println("1. Automática");
        System.out.println("2. Manual");
        System.out.print("Digite la opcion que desea: ");
        int opcion = v.ValidarEntero(sc, 1, 2);
        if (opcion == 1) {
            return "Automática";
        }
        return "Manual";
    }

    public String pedirTraccion(Scanner sc) {
        System.out.println("Tracción:");
        System.out.println("1. 4x2");
        System.out.println("2. 4x4");
        System.out.print("Digite la opcion que desea: ");
        int opcion = v.ValidarEntero(sc, 1, 2);
        if (opcion == 1) {
            return "4x2";
        }
        return "4x4";
    }

    public float pedirCapacidadMaletero(Scanner sc) {
        System.out.print("Capacidad del maletero (litros): ");
        return v.ValidarDecimalPositivo(sc);
    }

    public Vehiculo crearVehiculo(Scanner sc, int tipo, String placa) {
        String marca = pedirMarca(sc);
        int modelo = pedirModelo(sc);
        float precioDiario = pedirPrecioDiario(sc);
        if (tipo == 1) {
            String combustible = pedirCombustible(sc);
            String transmision = pedirTransmision(sc);
            return new CarroSedan(combustible, transmision, "Disponible", marca, modelo, placa, precioDiario);
        }
        String traccion = pedirTraccion(sc);
        float capacidad = pedirCapacidadMaletero(sc);
        return new CamionetaSUV(capacidad, traccion, "Disponible", marca, modelo, placa, precioDiario);
    }

    public String registrarVehiculo(Scanner sc, GestionEstructuras gestion) {
        int tipo = pedirTipoVehiculo(sc);
        String placa = pedirPlaca(sc);
        if (gestion.buscarVehiculo(placa) != null) {
            return "Ya existe un vehículo con la placa " + placa;
        }
        Vehiculo vehiculo = crearVehiculo(sc, tipo, placa);
        return gestion.agregarVehiculo(vehiculo);
    }

    public int mostrarOpcionesVehiculo(Scanner sc, Vehiculo vehiculo) {
        System.out.println("¿Qué dato desea modificar?");
        System.out.println("1. Marca");
        System.out.println("2. Modelo");
        System.out.println("3. Precio diario");
        if (vehiculo instanceof CarroSedan) {
            System.out.println("4. Tipo de combustible");
            System.out.println("5. Transmisión");
        } else {
            System.out.println("4. Tracción");
            System.out.println("5. Capacidad del maletero");
        }
        System.out.print("Digite la opcion que desea: ");
        return v.ValidarEntero(sc, 1, 5);
    }

    public void modificarDatoSedan(Scanner sc, CarroSedan sedan, int opcion) {
        if (opcion == 4) {
            sedan.setTipoCombustible(pedirCombustible(sc));
        } else {
            sedan.setTransmision(pedirTransmision(sc));
        }
    }

    public void modificarDatoSUV(Scanner sc, CamionetaSUV suv, int opcion) {
        if (opcion == 4) {
            suv.setTraccion(pedirTraccion(sc));
        } else {
            suv.setCapacidadMaletero(pedirCapacidadMaletero(sc));
        }
    }

    public void modificarDatoVehiculo(Scanner sc, Vehiculo vehiculo, int opcion) {
        if (opcion == 1) {
            vehiculo.setMarca(pedirMarca(sc));
        } else if (opcion == 2) {
            vehiculo.setModelo(pedirModelo(sc));
        } else if (opcion == 3) {
            vehiculo.setPrecioDiario(pedirPrecioDiario(sc));
        } else if (vehiculo instanceof CarroSedan) {
            modificarDatoSedan(sc, (CarroSedan) vehiculo, opcion);
        } else if (vehiculo instanceof CamionetaSUV) {
            modificarDatoSUV(sc, (CamionetaSUV) vehiculo, opcion);
        }
    }

    public String modificarVehiculo(Scanner sc, GestionEstructuras gestion) {
        String placa = pedirPlaca(sc);
        Vehiculo vehiculo = gestion.buscarVehiculo(placa);
        if (vehiculo == null) {
            return "No existe un vehículo con la placa " + placa;
        }
        System.out.println(informes.imprimir(vehiculo));
        int opcion = mostrarOpcionesVehiculo(sc, vehiculo);
        modificarDatoVehiculo(sc, vehiculo, opcion);
        gestion.guardarDatos();
        return "Vehículo modificado:\n" + informes.imprimir(vehiculo);
    }

    public String eliminarVehiculo(Scanner sc, GestionEstructuras gestion) {
        String placa = pedirPlaca(sc);
        return gestion.eliminarVehiculo(placa);
    }

    public String buscarVehiculo(Scanner sc, GestionEstructuras gestion) {
        String placa = pedirPlaca(sc);
        return informes.imprimir(gestion.buscarVehiculo(placa));
    }

    public String generarIdContrato(GestionEstructuras gestion, int numero) {
        String idContrato = String.format("C%03d", numero);
        if (gestion.buscarContrato(idContrato) != null || gestion.buscarContratoTerminado(idContrato) != null) {
            return generarIdContrato(gestion, numero + 1);
        }
        return idContrato;
    }

    public boolean estaDisponible(Vehiculo vehiculo) {
        return !vehiculo.getEstado().equalsIgnoreCase("Alquilado");
    }

    public Vehiculo buscarSedanDisponible(LinkedList<Vehiculo> vehiculos, String combustible, String transmision, int indice) {
        if (indice >= vehiculos.size()) {
            return null;
        }
        Vehiculo vehiculo = vehiculos.get(indice);
        if (vehiculo instanceof CarroSedan && estaDisponible(vehiculo)) {
            CarroSedan sedan = (CarroSedan) vehiculo;
            if (sedan.getTipoCombustible().equalsIgnoreCase(combustible)
                    && sedan.getTransmision().equalsIgnoreCase(transmision)) {
                return sedan;
            }
        }
        return buscarSedanDisponible(vehiculos, combustible, transmision, indice + 1);
    }

    public Vehiculo buscarSUVDisponible(LinkedList<Vehiculo> vehiculos, String traccion, float capacidadMinima, int indice) {
        if (indice >= vehiculos.size()) {
            return null;
        }
        Vehiculo vehiculo = vehiculos.get(indice);
        if (vehiculo instanceof CamionetaSUV && estaDisponible(vehiculo)) {
            CamionetaSUV suv = (CamionetaSUV) vehiculo;
            if (suv.getTraccion().equalsIgnoreCase(traccion)
                    && suv.getCapacidadMaletero() >= capacidadMinima) {
                return suv;
            }
        }
        return buscarSUVDisponible(vehiculos, traccion, capacidadMinima, indice + 1);
    }

    public Vehiculo buscarVehiculoDisponible(Scanner sc, GestionEstructuras gestion) {
        int tipo = pedirTipoVehiculo(sc);
        if (tipo == 1) {
            String combustible = pedirCombustible(sc);
            String transmision = pedirTransmision(sc);
            return buscarSedanDisponible(gestion.getVehiculos(), combustible, transmision, 0);
        }
        String traccion = pedirTraccion(sc);
        System.out.print("Capacidad mínima del maletero (litros): ");
        float capacidadMinima = v.ValidarDecimalPositivo(sc);
        return buscarSUVDisponible(gestion.getVehiculos(), traccion, capacidadMinima, 0);
    }

    public String pedirFechaFin(Scanner sc, String fechaInicio) {
        System.out.print("Fecha de fin (yyyy-MM-dd): ");
        String fechaFin = v.ValidarFecha(sc);
        if (calcularDias(fechaInicio, fechaFin) <= 0) {
            System.out.println("La fecha de fin debe ser posterior a la fecha de inicio (" + fechaInicio + ")");
            return pedirFechaFin(sc, fechaInicio);
        }
        return fechaFin;
    }

    public ContratoRenting crearContrato(String idContrato, String cedula, Vehiculo vehiculo,
                                         String fechaInicio, String fechaFin) {
        int dias = calcularDias(fechaInicio, fechaFin);
        ContratoRenting contrato = new ContratoRenting();
        contrato.setIdContrato(idContrato);
        contrato.setCedulaCliente(cedula);
        contrato.setPlacaVehiculo(vehiculo.getPlaca());
        contrato.setFechaInicio(fechaInicio);
        contrato.setFechaFin(fechaFin);
        contrato.setTotalDias(dias);
        contrato.setValorTotal(calcularValorTotal(vehiculo, dias));
        return contrato;
    }

    public String registrarContrato(GestionEstructuras gestion, String cedula, Vehiculo vehiculo,
                                    String fechaInicio, String fechaFin) {
        if (gestion.buscarCliente(cedula) == null) {
            return "No existe un cliente con la cédula " + cedula;
        }

        if (gestion.buscarContratoActivoCliente(cedula) != null) {
            return "El cliente ya tiene un vehículo alquilado. No puede alquilar otro.";
        }

        if (vehiculo == null || !estaDisponible(vehiculo)) {
            return "No hay vehículos disponibles con las características solicitadas.";
        }

        if (calcularDias(fechaInicio, fechaFin) <= 0) {
            return "Fechas inválidas. Use el formato yyyy-MM-dd y la fecha fin debe ser posterior a la de inicio.";
        }

        int numero = gestion.getContratos().size() + gestion.getContratosTerminados().size() + 1;
        String idContrato = generarIdContrato(gestion, numero);
        ContratoRenting contrato = crearContrato(idContrato, cedula, vehiculo, fechaInicio, fechaFin);
        gestion.agregarContrato(contrato);

        return "Contrato registrado con éxito:\n" + informes.imprimir(contrato);
    }

    public String registrarContrato(Scanner sc, GestionEstructuras gestion) {
        String cedula = pedirCedula(sc);
        if (gestion.buscarCliente(cedula) == null) {
            return "No existe un cliente con la cédula " + cedula;
        }
        if (gestion.buscarContratoActivoCliente(cedula) != null) {
            return "El cliente ya tiene un vehículo alquilado. No puede alquilar otro.";
        }

        Vehiculo vehiculo = buscarVehiculoDisponible(sc, gestion);
        if (vehiculo == null) {
            return "No hay vehículos disponibles con las características solicitadas.";
        }
        System.out.println("Vehículo asignado: " + informes.imprimir(vehiculo));

        System.out.print("Fecha de inicio (yyyy-MM-dd): ");
        String fechaInicio = v.ValidarFecha(sc);
        String fechaFin = pedirFechaFin(sc, fechaInicio);
        return registrarContrato(gestion, cedula, vehiculo, fechaInicio, fechaFin);
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
            return "[ACTIVO] " + informes.imprimir(contrato);
        }
        contrato = gestion.buscarContratoTerminado(idContrato);
        if (contrato != null) {
            return "[FINALIZADO] " + informes.imprimir(contrato);
        }
        return "No existe el contrato " + idContrato;
    }

    public String consultarContratosPorCliente(GestionEstructuras gestion, String cedula) {
        String resultado = "";
        for (ContratoRenting c : gestion.getContratos()) {
            if (c.getCedulaCliente().equals(cedula)) {
                resultado = resultado + "[ACTIVO] " + informes.imprimir(c) + "\n";
            }
        }
        for (ContratoRenting c : gestion.getContratosTerminados()) {
            if (c.getCedulaCliente().equals(cedula)) {
                resultado = resultado + "[FINALIZADO] " + informes.imprimir(c) + "\n";
            }
        }
        if (resultado.isEmpty()) {
            return "El cliente " + cedula + " no tiene contratos registrados.";
        }
        return resultado;
    }

    public String pedirIdContrato(Scanner sc) {
        System.out.print("Id del contrato: ");
        return v.ValidarAlfanumerico(sc, 10).toUpperCase();
    }

    public String modificarContrato(Scanner sc, GestionEstructuras gestion) {
        String idContrato = pedirIdContrato(sc);
        if (gestion.buscarContrato(idContrato) == null) {
            return "No existe un contrato activo con el Id " + idContrato;
        }
        System.out.println("1. Modificar fechas");
        System.out.println("2. Modificar duración (días)");
        System.out.print("Digite la opcion que desea: ");
        int opcion = v.ValidarEntero(sc, 1, 2);

        if (opcion == 1) {
            System.out.print("Nueva fecha de inicio (yyyy-MM-dd): ");
            String fechaInicio = v.ValidarFecha(sc);
            String fechaFin = pedirFechaFin(sc, fechaInicio);
            return modificarFechasContrato(gestion, idContrato, fechaInicio, fechaFin);
        }
        System.out.print("Nueva cantidad de días: ");
        int dias = v.ValidarEntero(sc, 1, 365);
        return modificarDuracionContrato(gestion, idContrato, dias);
    }

    public String finalizarContrato(Scanner sc, GestionEstructuras gestion) {
        String idContrato = pedirIdContrato(sc);
        return devolverVehiculo(gestion, idContrato);
    }

    public String buscarContrato(Scanner sc, GestionEstructuras gestion) {
        System.out.println("1. Buscar por Id de contrato");
        System.out.println("2. Buscar por cédula del cliente");
        System.out.print("Digite la opcion que desea: ");
        int opcion = v.ValidarEntero(sc, 1, 2);

        if (opcion == 1) {
            return consultarContrato(gestion, pedirIdContrato(sc));
        }
        return consultarContratosPorCliente(gestion, pedirCedula(sc));
    }
}
