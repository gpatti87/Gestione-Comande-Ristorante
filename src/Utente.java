import java.io.Serializable;

public class Utente implements Serializable {

    private String CF;
    private String Nome;
    private String Cognome;
    private String Qualifica;
    private int age;

    public Utente(String CF, String nome, String cognome, String qualifica, int age) {
        this.CF = CF;
        Nome = nome;
        Cognome = cognome;
        Qualifica = qualifica;
        this.age = age;
    }

    public String getCF() {
        return CF;
    }

    public void setCF(String CF) {
        this.CF = CF;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getCognome() {
        return Cognome;
    }

    public void setCognome(String cognome) {
        Cognome = cognome;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getQualifica() {
        return Qualifica;
    }

    public void setQualifica(String qualifica) {
        Qualifica = qualifica;
    }

    @Override
    public String toString() {
        return "Utente{" +
                "CF='" + CF + '\'' +
                ", Nome='" + Nome + '\'' +
                ", Cognome='" + Cognome + '\'' +
                ", Qualifica='" + Qualifica + '\'' +
                ", età=" + age +
                '}';
    }
}
