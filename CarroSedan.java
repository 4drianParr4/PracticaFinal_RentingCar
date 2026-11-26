public class CarroSedan extends Vehiculo {
    private String tipoCombustible;
    private String transmision;

    public CarroSedan(String tipoCombustible, String transmision, String estado, String marca, int modelo, String placa, float precioDiario) {
        super(estado, marca, modelo, placa, precioDiario);
        this.tipoCombustible = tipoCombustible;
        this.transmision = transmision;
    }

    public String getTipoCombustible() {
        return tipoCombustible;
    }

    public void setTipoCombustible(String tipoCombustible) {
        this.tipoCombustible = tipoCombustible;
    }

    public String getTransmision() {
        return transmision;
    }

    public void setTransmision(String transmision) {
        this.transmision = transmision;
    }

    @Override
    public String getTipo() {
        return "Sedan";
    }

    @Override
    public String toString() {
        return "[Sedán] " + super.toString()
                + " | Combustible: " + tipoCombustible
                + " | Transmisión: " + transmision;
    }

}
