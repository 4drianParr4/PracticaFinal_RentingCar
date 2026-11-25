public class ContratoRenting {
        private String IdContrato;
        private String CedulaCliente;
        private String PlacaVehiculo;
        private String FechaInicio;
        private String FechaFin;
        private int TotalDias;
        private float ValorTotal;

    public ContratoRenting() {
        
    }

    public String getIdContrato() {
        return IdContrato;
    }

    public String getCedulaCliente() {
        return CedulaCliente;
    }

    public String getPlacaVehiculo() {
        return PlacaVehiculo;
    }

    public String getFechaInicio() {
        return FechaInicio;
    }

    public String getFechaFin() {
        return FechaFin;
    }

    public int getTotalDias() {
        return TotalDias;
    }

    public float getValorTotal() {
        return ValorTotal;
    }

    public void setIdContrato(String idContrato) {
        IdContrato = idContrato;
    }

    public void setCedulaCliente(String cedulaCliente) {
        CedulaCliente = cedulaCliente;
    }

    public void setPlacaVehiculo(String placaVehiculo) {
        PlacaVehiculo = placaVehiculo;
    }

    public void setFechaInicio(String fechaInicio) {
        FechaInicio = fechaInicio;
    }

    public void setFechaFin(String fechaFin) {
        FechaFin = fechaFin;
    }

    public void setTotalDias(int totalDias) {
        TotalDias = totalDias;
    }

    public void setValorTotal(float valorTotal) {
        ValorTotal = valorTotal;
    }

    @Override
    public String toString() {
        return "Contrato: " + IdContrato
                + " | Cliente: " + CedulaCliente
                + " | Vehículo: " + PlacaVehiculo
                + " | Desde: " + FechaInicio
                + " | Hasta: " + FechaFin
                + " | Días: " + TotalDias
                + " | Valor: $" + String.format("%,.2f", ValorTotal);
    }

}
