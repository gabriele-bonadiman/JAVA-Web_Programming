 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package classi;

import javax.xml.crypto.Data;

/**
 *
 * @author Babol
 */
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
