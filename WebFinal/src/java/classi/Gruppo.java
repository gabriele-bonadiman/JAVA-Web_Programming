package classi;
 
import java.io.Serializable;

public class Gruppo implements Serializable{
    
    private String nome;
    private int proprietario;
    private String data_creazione;
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

    public String getData_creazione() {
        return data_creazione;
    }

    public void setData_creazione(String data_creazione) {
        this.data_creazione = data_creazione;
    }

}

