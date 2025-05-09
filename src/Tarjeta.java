public abstract class Tarjeta {

    protected boolean estaDescubierta;
    protected boolean tienePareja;
    protected String rutaImagen;
    protected String identificador;

    public Tarjeta(String rutaImagen, String identificador) {
        this.rutaImagen = rutaImagen;
        this.identificador = identificador;
        this.estaDescubierta = false;
        this.tienePareja = false;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public boolean estaDescubierta() {
        return estaDescubierta;
    }

    public void voltearTarjeta() {
        this.estaDescubierta = !this.estaDescubierta;
    }
    public String getIdentificador() {
        return identificador;
    }

    public void marcarPareja() {
        this.tienePareja = true;
    }

    public boolean tienePareja() {
        return tienePareja;
    }

    public boolean tieneEfectoEspecial(){
        return false;
    }

    public abstract void activarEfectoEspecial();

    public abstract boolean esPareja(Tarjeta otraTarjeta);
}