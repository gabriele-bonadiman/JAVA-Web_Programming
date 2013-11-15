/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package classi;
 
import java.io.Serializable;
import java.sql.Date;

public class Gruppo implements Serializable{
    
    private String nome;
    private int proprietario;
    private Date data_creazione;
    private int ID;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public int getProprietario() {
        return proprietario;
    }


    public void setProprietario(int proprietario) {
        this.proprietario = proprietario;
    }


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Date getData_creazione() {
        return data_creazione;
    }

    public void setData_creazione(Date data_creazione) {
        this.data_creazione = data_creazione;
    }

}

