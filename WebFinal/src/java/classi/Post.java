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
public class Post {
    private Data data;
    private String testo;
    private Utente utente;
    private Gruppo gruppo;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public Gruppo getGruppo() {
        return gruppo;
    }

    public void setGruppo(Gruppo gruppo) {
        this.gruppo = gruppo;
    }
}
