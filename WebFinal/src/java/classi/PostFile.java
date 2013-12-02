package classi;

public class PostFile {
    private String data;
    private String path;
    private Utente utente;
    private Gruppo guppo;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public Gruppo getGuppo() {
        return guppo;
    }

    public void setGuppo(Gruppo guppo) {
        this.guppo = guppo;
    }
    
}
