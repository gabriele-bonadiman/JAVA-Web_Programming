/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package classi;

import java.sql.Date;



public class Post {
    
    
    private Date data;
    private String testo;
    private int utente;
    private int gruppo;

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public int getUtente() {
        return utente;
    }

    public void setUtente(int utente) {
        this.utente = utente;
    }

    public int getGruppo() {
        return gruppo;
    }

    public void setGruppo(int gruppo) {
        this.gruppo = gruppo;
    }

    
    
    
}
