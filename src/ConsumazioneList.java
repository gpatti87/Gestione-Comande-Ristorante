import java.util.ArrayList;
import java.util.Date;

public class ConsumazioneList {


    String last_modification;
    private ArrayList<Consumazione> list;


    public ConsumazioneList(){

        list = new ArrayList<>();
    }


    public synchronized void add(Consumazione c){
        last_modification = new Date().toString();
        list.add(c);
    }
    public synchronized String remove(String nome){
        last_modification = new Date().toString();
        String verdetto = null;
        for( Consumazione co : list){
            if( (co.getNome().equals(nome)))
            {
                list.remove(co);
                verdetto = "REMOVE_OK";
                return verdetto;
            }

        }
        System.out.println("Pietanza non presente");
        verdetto = "REMOVE_ERROR";
        return verdetto;

    }


    public ArrayList<Consumazione> getListCopy(){

        ArrayList<Consumazione> a_list = new ArrayList<>();
        a_list.addAll(list);

        return a_list;

    }


    @Override
    public String toString() {
        String s;
        s = "BEGIN_LIST";
        s = s+" MOD_DATE: "+last_modification;
        for (Consumazione c : list){
            s = s+"NOME "+c.getNome();
            s = s+"PREZZO "+c.getPrezzo();
            s = s+"TIPO "+c.getTipo();
        }
        s = s + "END_LIST";
        return s;
    }
}
