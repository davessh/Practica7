
abstract class Tarjeta {
    protected String rutaImagen;
    protected String identificador;
    protected boolean descubierta;
    protected boolean tienePareja;

    public Tarjeta(String rutaImagen, String identificador) {
        this.rutaImagen = rutaImagen;
        this.identificador = identificador;
        this.descubierta = false;
        this.tienePareja = false;
    }

    public void voltearTarjeta() {
        this.descubierta = !this.descubierta;
    }

    public boolean estaDescubierta() {
        return descubierta;
    }

    public void marcarPareja() {
        this.tienePareja = true;
        this.descubierta = true;
    }

    public boolean tienePareja() {
        return tienePareja;
    }

    public String getIdentificador() {
        return identificador;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public abstract boolean tieneEfectoEspecial();

    public abstract void activarEfectoEspecial();

    public abstract boolean esPareja(Tarjeta otraTarjeta);
}