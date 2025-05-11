public class TarjetaComodin extends Tarjeta {

    public TarjetaComodin(String rutaImagen, String identificador) {
        super(rutaImagen, identificador);
    }

    @Override
    public void activarEfectoEspecial() {
        // Podrías implementar algo visual o sonoro cuando se revela
        System.out.println("¡Has descubierto un comodín!");
    }

    @Override
    public boolean tieneEfectoEspecial() {
        return true;
    }

    @Override
    public boolean esPareja(Tarjeta otraTarjeta) {
        // El comodín puede hacer pareja con cualquier tarjeta que aún no tenga pareja
        if (otraTarjeta == null || otraTarjeta == this) return false;
        return !otraTarjeta.tienePareja();
    }
}
