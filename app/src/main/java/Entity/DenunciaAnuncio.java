package Entity;

public class DenunciaAnuncio {

    private int id;
    private Usuario usuario;
    private Anuncio anuncio;
    private String descricao;
    private int ativo;

    public Usuario getUsuario() {
        return usuario;
    }

    public int getAtivo() {
        return ativo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAtivo(int ativo) {
        this.ativo = ativo;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Anuncio getAnuncio() {
        return anuncio;
    }

    public void setAnuncio(Anuncio anuncio) {
        this.anuncio = anuncio;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
