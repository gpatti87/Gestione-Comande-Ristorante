import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientManager implements Runnable {

    private Socket client_socket;
    private UserList lutente;
    private TavoloList ltavolo;
    private ConsumazioneList lconsumazione;
    private Menu menu;

    public ClientManager(Socket client_socket, UserList lutente, TavoloList ltavolo, ConsumazioneList lconsumazione, Menu menu) {
        this.client_socket = client_socket;
        this.lutente = lutente;
        this.ltavolo = ltavolo;
        this.lconsumazione = lconsumazione;
        this.menu = menu;
    }

    @Override
    public void run() {
        String tid = Thread.currentThread().getName();
        System.out.println(tid+"=>Accepted connection from " + client_socket.getRemoteSocketAddress());

        Scanner client_scanner = null;
        PrintWriter pw = null;

        try {
            client_scanner = new Scanner(client_socket.getInputStream());
            pw = new PrintWriter(client_socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        ObjectInputStream is;
        try {
            is = new ObjectInputStream(new FileInputStream("ut.ser"));
            lutente = (UserList) is.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        boolean go = true;
        while (go){
            String message = client_scanner.nextLine();
            System.out.println("Server: Received "+message);
            Scanner msg_scanner = new Scanner(message);

            String cmd = msg_scanner.next();
            System.out.println("Received Command: "+cmd);
            if (cmd.equals("ADD_Utente")){
                String cf = msg_scanner.next();
                cf.toUpperCase();
                String nome = msg_scanner.next();
                String cognome = msg_scanner.next();
                String qualifica = msg_scanner.next();
                qualifica.toUpperCase();
                int age = msg_scanner.nextInt();
                Utente u = new Utente(cf,nome,cognome,qualifica,age);

                lutente.add(u);
                System.out.println("SERVER LOG: Added "+u);

                pw.println("ADD_OK");
                pw.flush();
            }
            else if (cmd.equals("REMOVE_Utente")){
                String cf = msg_scanner.next();

                String verdetto = lutente.remove(cf);

                pw.println(verdetto);
                pw.flush();

            }
            else if (cmd.equals("LIST_UTENTI")){
                pw.println("BEGIN");
                pw.flush();
                ArrayList<Utente> tmp;
                tmp = lutente.getListCopy();

                for (Utente u: tmp){
                    pw.println(u);
                    pw.flush();
                }
                pw.println("END");
                pw.flush();
            }
            else if(cmd.equals("SAVE")){
                try{
                    var oos = new ObjectOutputStream(new FileOutputStream("ut.ser"));
                    oos.writeObject(lutente);
                    oos.close();
                    pw.println("SAVE_OK");
                    pw.flush();
                    System.out.println("SERVER LOG: list saved correctly");
                } catch (IOException e) {
                    pw.println("SAVE_ERROR");
                    pw.flush();
                    e.printStackTrace();
                }
            }
            else if(cmd.equals("CARICA")){

                try {
                    is = new ObjectInputStream(new FileInputStream("ut.ser"));
                    lutente = (UserList) is.readObject();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else if(cmd.equals("QUIT")){
                System.out.println("Server: Closing connection to "+client_socket.getRemoteSocketAddress());
                try {
                    client_socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                go = false;
            }
            else if(cmd.equals("CODICE")){
                ArrayList<Utente> tmp;
                tmp = lutente.getListCopy();
                String cf = msg_scanner.next();

                for (Utente u: tmp){
                    if(cf.equals(u.getCF())){
                        if(u.getQualifica().equals("CUOCO")||u.getQualifica().equals("cuoco")){
                            System.out.println("Qualifica: "+u.getQualifica());
                            pw.println("CUOCO");
                            pw.flush();
                            break;

                        }else if(u.getQualifica().equals("CAMERIERE")||u.getQualifica().equals("cameriere")){
                            System.out.println("Qualifica: "+u.getQualifica());
                            pw.println("CAMERIERE");
                            pw.flush();
                            break;
                        }
                    }
                }

            }

            else if(cmd.equals("ADD_Pietanza")){
                String nome = msg_scanner.next();
                int prezzo = msg_scanner.nextInt();
                String tipo = msg_scanner.next();

                Consumazione con = new Consumazione(nome,prezzo,tipo);

                menu.add(con);
                System.out.println("SERVER LOG: Added "+con);

                pw.println("ADD_OK");
                pw.flush();
            }
            else if (cmd.equals("REMOVE_Pietanza")){
                String nome = msg_scanner.next();
                menu.remove(nome);

                pw.println("REMOVE_OK");
                pw.flush();

            }
            else if (cmd.equals("LIST_MENU")){
                pw.println("BEGIN");
                pw.flush();
                ArrayList<Consumazione> tmp;
                tmp = menu.showMenu();

                for (Consumazione c: tmp){
                    pw.println(c);
                    pw.flush();
                }
                pw.println("END");
                pw.flush();
            }
            else if (cmd.equals("LIST_TAVOLI")){
                pw.println("BEGIN");
                pw.flush();
                ArrayList<Tavolo> tmp;
                tmp = ltavolo.getListCopy();

                for (Tavolo u: tmp){
                    pw.println(u);
                    pw.flush();
                }
                pw.println("END");
                pw.flush();
            }else if (cmd.equals("SHOW_Comanda")){
                int id = msg_scanner.nextInt();

                ArrayList<Tavolo> tmp;
                tmp = ltavolo.getListCopy();
                ArrayList<Consumazione> tmpc;

                for (Tavolo ta: tmp){
                    if(id==ta.getID()){
                        tmpc=ta.getListaCons();
                        pw.println("BEGIN");
                        pw.flush();

                        for (Consumazione c: tmpc){
                            pw.println(c);
                            pw.flush();
                        }
                        pw.println("END");
                        pw.flush();
                        break;

                    }
                }
            }
            else if(cmd.equals("TAVOLO")) {
                int id = msg_scanner.nextInt();
                int coperti = msg_scanner.nextInt();


                Tavolo ta = new Tavolo(id, coperti);
                ltavolo.add(ta);
                pw.println("ADD_OK");
                pw.flush();
            }
            else if(cmd.equals("ADD_Piet_Tav")) {
                int id =msg_scanner.nextInt();
                String nome = msg_scanner.next();

                int prezzo=0;
                String tipo=null;

                ArrayList<Consumazione> tmpC;
                tmpC=menu.showMenu();

                for( Consumazione co : tmpC) {
                    if ((co.getNome().equals(nome))) {
                        prezzo = co.getPrezzo();
                        tipo = co.getTipo();

                    }
                }

                Consumazione con = new Consumazione(nome,prezzo,tipo);

                ArrayList<Tavolo> tmp;
                tmp = ltavolo.getListCopy();

                for (Tavolo ta: tmp){
                    if(id==ta.getID()){
                        ta.insCons(con);
                        pw.println("ADD_OK");
                        pw.flush();
                    }
                }



            }
            else if(cmd.equals("CONTO")) {

                ArrayList<Tavolo> tmp;
                tmp = ltavolo.getListCopy();
                int id = msg_scanner.nextInt();
                int conto;
                for (Tavolo ta: tmp){
                    if(id==ta.getID()){
                        conto=ta.getSaldo();
                        pw.println(conto);
                        pw.flush();
                    }
                }

            }

        }

    }
}
