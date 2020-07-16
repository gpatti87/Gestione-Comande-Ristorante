import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class Amministratore {

   public void menu(Socket socket) throws IOException {
       PrintWriter pw = new PrintWriter(socket.getOutputStream());
       //from server
       Scanner server_scanner = new Scanner(socket.getInputStream());
       //from user
       Scanner user_scanner = new Scanner(System.in);

       String msg_to_send;
       String msg_received;

       boolean goamm = true;
       int choice;
       while (goamm) {
           System.out.println("---------------------------");
           System.out.println("0 - Aggiungi Utente");
           System.out.println("1 - Rimuovi Utente");
           System.out.println("2 - Mostra Utenti");
           System.out.println("3 - Salva Utenti");
           System.out.println("4 - Carica Utenti");
           System.out.println("5 - QUIT");
           System.out.println("---------------------------");
           System.out.println("Enter your choice-->");
           choice = user_scanner.nextInt();
           switch (choice) {
               case 0:
                   System.out.println("Inserisci Codice Fiscale: ");
                   String cf = user_scanner.next();
                   cf = cf.toUpperCase();
                   System.out.println("Inserisci Nome: ");
                   String nome = user_scanner.next();
                   System.out.println("Inserisci Cognome: ");
                   String cognome = user_scanner.next();
                   System.out.println("Inserisci Qualifica: ");
                   String qualifica = user_scanner.next();
                   qualifica = qualifica.toUpperCase();
                   System.out.println("Inserisci Età: ");
                   int age = user_scanner.nextInt();

                   msg_to_send = "ADD_Utente " + cf + " " + nome + " " + cognome + " " + qualifica + " " + age;
                   System.out.println("DEBUG: Sending " + msg_to_send);
                   pw.println(msg_to_send);
                   pw.flush();
                   msg_received = server_scanner.nextLine();
                   if (msg_received.equals("ADD_OK")) {
                       System.out.println("Person added correctly!");
                   } else if (msg_received.equals("ADD_ERROR")) {
                       System.out.println("Error adding person!!!");
                   } else {
                       System.out.println("ERROR: Unknown message-> " + msg_received);
                   }
                   break;
               case 1:
                   System.out.println("Inserisci Codice Fiscale dell'utente da eliminare: ");
                   String cfr = user_scanner.next();
                   cfr = cfr.toUpperCase();
                   msg_to_send = "REMOVE_Utente " + cfr;
                   System.out.println("DEBUG: Sending " + msg_to_send);
                   pw.println(msg_to_send);
                   pw.flush();
                   msg_received = server_scanner.nextLine();
                   if (msg_received.equals("REMOVE_OK")) {
                       System.out.println("Utente eliminato!");
                   } else if (msg_received.equals("REMOVE_ERROR")) {
                       System.out.println("Errore!!!");
                   } else {
                       System.out.println("ERROR: Unknown message-> " + msg_received);
                   }
                   break;
               case 2:
                   msg_to_send = "LIST_UTENTI";
                   pw.println(msg_to_send);
                   pw.flush();

                   msg_received = server_scanner.nextLine();
                   boolean listing = true;
                   if (msg_received.equals("BEGIN")) {
                       System.out.println("Receiving list...");
                       while (listing) {
                           msg_received = server_scanner.nextLine();
                           if (msg_received.equals("END")) {
                               listing = false;
                               System.out.println("List ended");
                           } else {//printing the person
                               System.out.println(msg_received);
                           }
                       }
                   } else {
                       System.out.println("Unknown Response: " + msg_received);
                   }
                   break;
               case 3:
                   pw.println("SAVE");
                   pw.flush();
                   msg_received = server_scanner.nextLine();
                   if (msg_received.equals("SAVE_OK")) {
                       System.out.println("File save correctly");
                   } else if (msg_received.equals("SAVE_ERROR")) {
                       System.out.println("Error saving file");
                   } else {
                       System.out.println("Unknown message: " + msg_received);
                   }
                   break;
               case 4:

                   pw.println("CARICA");
                   pw.flush();

                   break;
               case 5:
                   goamm = false;
                   System.out.println("Torno indietro");

                   break;
           }
       }
   }
}

