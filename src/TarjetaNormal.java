
class TarjetaNormal extends Tarjeta {
    private String tipoEmparejamiento;

    public TarjetaNormal(String rutaImagen, String identificador) {
        super(rutaImagen, identificador);
        this.tipoEmparejamiento = extraerTipoDesdeIdentificador(identificador);
    }

    // Se obtiene el identificador del tipo de tarjeta
    private String extraerTipoDesdeIdentificador(String id) {
        String[] partes = id.split("_");
        return partes[partes.length - 1];
    }

    @Override
    public boolean tieneEfectoEspecial() {
        return false;
    }

    @Override
    public void activarEfectoEspecial() {
        // No tiene efecto especial
    }

    @Override
    public boolean esPareja(Tarjeta otraTarjeta) {
        // Solo compararemos TarjetasPorTipo
        if (otraTarjeta instanceof TarjetaNormal) {
            TarjetaNormal otra = (TarjetaNormal) otraTarjeta;

            // Aseguramos que sean del mismo tipo y no de la misma imagen (identificador)
            return this.tipoEmparejamiento.equals(otra.tipoEmparejamiento) &&
                    !this.identificador.equals(otra.identificador);
        }
        return false;
    }
}