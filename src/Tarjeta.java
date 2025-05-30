
abstract class Tarjeta {
    protected String rutaImagen;
    protected String identificador;
    protected boolean descubierta;
    protected boolean tienePareja;
    private boolean resuelta = false;

    public Tarjeta(String rutaImagen, String identificador) {
        this.rutaImagen = rutaImagen;
        this.identificador = identificador;
        this.descubierta = false;
        this.tienePareja = false;

    }
    public void voltear() {
        this.descubierta = false;
    }
    public void setResuelta(boolean resuelta) {
        this.resuelta = resuelta;
    }

    public boolean isResuelta() {
        return resuelta;
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

    public boolean puedeVoltearse() {
        return !estaDescubierta() && !tienePareja() && !isResuelta();
    }
    public void setDescubierta(boolean descubierta) {
        this.descubierta = descubierta;
    }
    //metodo abstracto a definirse en cada clase
    public abstract boolean tieneEfectoEspecial();
    //se indica la presencia de un efecto especial
    public abstract void activarEfectoEspecial();
    //se comparan tarjetas dependiendo el modo de juego
    public abstract boolean esPareja(Tarjeta otraTarjeta);
}