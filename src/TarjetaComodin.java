public class TarjetaComodin extends Tarjeta {

    public TarjetaComodin(String rutaImagen, String identificador) {
        super(rutaImagen, identificador);
    }

    @Override
    public void activarEfectoEspecial() {
        // A modo de prueba
        System.out.println("Comodin activo, puede emparejarse con cualquier tarjeta.");
    }

    @Override
    public boolean tieneEfectoEspecial() {
        return true;
    }

    @Override
    public boolean esPareja(Tarjeta otraTarjeta) {
        // Se empareja la tarjeta con cualquiera que no tenga pareja
        return !otraTarjeta.tienePareja();
    }
}

