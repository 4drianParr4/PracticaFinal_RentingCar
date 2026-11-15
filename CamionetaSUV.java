public class CamionetaSUV extends Vehiculo {
    private String traccion;
    private float capacidadMaletero;

    public CamionetaSUV(float capacidadMaletero, String traccion, String estado, String marca, int modelo, String placa, float precioDiario) {
        super(estado, marca, modelo, placa, precioDiario);
        this.capacidadMaletero = capacidadMaletero;
        this.traccion = traccion;
    }

    public String getTraccion() {
        return traccion;
    }

    public void setTraccion(String traccion) {
        this.traccion = traccion;
    }

    public float getCapacidadMaletero() {
        return capacidadMaletero;
    }

    public void setCapacidadMaletero(float capacidadMaletero) {
        this.capacidadMaletero = capacidadMaletero;
    }

}
