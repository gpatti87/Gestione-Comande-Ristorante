import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class UserList implements Serializable {


    String archive_id;
    String last_modification;
    private ArrayList<Utente> list;


    public UserList(){

        list = new ArrayList<>();
    }


    public synchronized void add(Utente p){
        last_modification = new Date().toString();
        list.add(p);
    }
    public synchronized void remove(String cf){
        last_modification = new Date().toString();
        for( Utente ut : list){
            if( (ut.getCF().equals(cf))){
                list.remove(ut);

            }
            else{
                System.out.println("Utente non presente");
            }
        }
    }


    public ArrayList<Utente> getListCopy(){


            ArrayList<Utente> a_list = new ArrayList<>();
            a_list.addAll(list);
            return a_list;
        }


    @Override
    public String toString() {
        String s;
        s = "BEGIN_LIST";
        s = s+" MOD_DATE: "+last_modification;
        for (Utente u : list){
            s = s+"CODICE FISCALE "+u.getCF();
            s = s+"NOME "+u.getNome();
            s = s+"COGNOME "+u.getCognome();
            s = s+"QUALIFICA "+u.getQualifica();
            s = s+"ETA' "+u.getAge();
        }
        s = s + "END_LIST";
        return s;
    }
}
