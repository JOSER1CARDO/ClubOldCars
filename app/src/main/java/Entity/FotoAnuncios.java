package Entity;

import java.io.Serializable;
import java.util.List;

public class FotoAnuncios implements Serializable {

    private Anuncio anuncio;
    private List<Foto> fotos;

    public Anuncio getAnuncio() {
        return anuncio;
    }

    public void setAnuncio(Anuncio anuncio) {
        this.anuncio = anuncio;
    }

    public List<Foto> getFotos() {
        return fotos;
    }

    public void setFotos(List<Foto> fotos) {
        this.fotos = fotos;
    }
}
