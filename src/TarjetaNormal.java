public class TarjetaNormal extends Tarjeta {

    public TarjetaNormal(String rutaImagen, String identificador) {
        super(rutaImagen, identificador);
    }

    @Override
    public boolean tieneEfectoEspecial() {
        return false;
    }

    @Override
    public void activarEfectoEspecial() {
        //No tiene efecto especial
    }

    @Override
    public boolean esPareja(Tarjeta otraTarjeta) {
        // Solo hace pareja si el identificador es igual y ambas son TarjetaNormal
        if (otraTarjeta instanceof TarjetaNormal) {
            return this.identificador.equals(otraTarjeta.getIdentificador());
        }
        return false;
    }
}

