import java.io.Serializable;

public class Consumazione implements Serializable {

    private String Nome;
    private int prezzo;
    private String tipo;

    public Consumazione(String nome, int prezzo, String tipo) {
        setNome(nome);
        this.setPrezzo(prezzo);
        this.setTipo(tipo);
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public int getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(int prezzo) {
        this.prezzo = prezzo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Consumazione{" +
                "Nome=" + Nome +
                ", prezzo=" + prezzo +
                ", tipo=" + tipo +
                '}';
    }
}
