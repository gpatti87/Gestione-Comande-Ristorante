import java.io.*;
import java.util.ArrayList;
import java.util.Date;

public class Menu {

    String last_modification;
    private ArrayList<Consumazione> list;


    public Menu(){

        list = new ArrayList<>();
    }


    public synchronized void add(Consumazione c){
        last_modification = new Date().toString();
        list.add(c);
        saveMenu(list);
    }
    public synchronized void remove(String nome){
        last_modification = new Date().toString();
        for( Consumazione co : list){
            if( (co.getNome().equals(nome)))
            {
                list.remove(co);
                saveMenu(list);
                break;
            }

        }
        System.out.println("Pietanza non presente nel Menu'");


    }

    public ArrayList<Consumazione> showMenu() {

        ObjectInputStream is;


        try {
            is = new ObjectInputStream(new FileInputStream("menu.ser"));
            list = (ArrayList<Consumazione>) is.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return list;
    }


        public void saveMenu (ArrayList list)

        {
            try {
                var oos = new ObjectOutputStream(new FileOutputStream("menu.ser"));
                oos.writeObject(list);
                oos.close();

                System.out.println("list saved correctly");
            } catch (IOException e) {
                System.out.println("Save_Error");
                e.printStackTrace();
            }
        }



}
