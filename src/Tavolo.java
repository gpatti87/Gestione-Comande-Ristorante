import java.io.Serializable;
import java.util.ArrayList;

public class Tavolo implements Serializable {

    private int ID;
    private int coperti;
    private int saldo;
    private ArrayList<Consumazione> listaCons = new ArrayList<>();

    public Tavolo(int ID, int coperti) {
        this.ID = ID;
        this.coperti = coperti;

    }

    public ArrayList<Consumazione> getListaCons() {
        return listaCons;
    }

    public void insCons(Consumazione co){
        saldo=saldo+co.getPrezzo();
        listaCons.add(co);
    }

    public int calcolaSaldo(){

        /*for(Consumazione x:listaCons){
            saldo+=x.getPrezzo();
        }*/
        return saldo;
    }



    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getCoperti() {
        return coperti;
    }

    public void setCoperti(int coperti) {
        this.coperti = coperti;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return "Tavolo{" +
                "ID=" + ID +
                ", coperti=" + coperti +
                ", saldo=" + saldo +
                '}';
    }
}
