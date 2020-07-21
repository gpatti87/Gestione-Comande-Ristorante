import java.util.ArrayList;
import java.util.Date;

public class TavoloList {


    String last_modification;
    private ArrayList<Tavolo> list;


    public TavoloList(){

        list = new ArrayList<>();
    }

    //could also return a boolean to check the validity
    public synchronized void add(Tavolo t){
        last_modification = new Date().toString();
        list.add(t);
    }
    public synchronized String remove(Tavolo t){
        last_modification = new Date().toString();
        String verdetto=null;
        for( Tavolo ta : list){
            if( (ta.getID()==t.getID()))
            {
                list.remove(ta);
                verdetto = "REMOVE_OK";
                return verdetto;
            }

        }
        System.out.println("Tavolo non presente");
        verdetto = "REMOVE_ERROR";
        return verdetto;

    }


    public ArrayList<Tavolo> getListCopy(){

        ArrayList<Tavolo> a_list = new ArrayList<>();
        a_list.addAll(list);

        return a_list;
    }


    @Override
    public String toString() {
        String s;
        s = "BEGIN_LIST";
        s = s+" MOD_DATE: "+last_modification;
        for (Tavolo t : list){
            s = s+"ID "+t.getID();
            s = s+"COPERTI "+t.getCoperti();
            s = s+"SALDO "+t.getSaldo();
        }
        s = s + "END_LIST";
        return s;
    }
}
