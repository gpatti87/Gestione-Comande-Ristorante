import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Dipendente {

    public void menu(Socket socket) throws IOException {
        PrintWriter pw = new PrintWriter(socket.getOutputStream());
        //from server
        Scanner server_scanner = new Scanner(socket.getInputStream());
        //from user
        Scanner user_scanner = new Scanner(System.in);

        String msg_to_send;
        String msg_received;

        boolean godip = true;
        int choice;
        while (godip) {

            System.out.println("---------------------------");
            System.out.println("0 - LOGIN");
            System.out.println("1 - EXIT");
            System.out.println("---------------------------");
            System.out.println("Enter your choice-->");
            choice = user_scanner.nextInt();
            switch (choice) {
                case 0:

                System.out.println("Inserisci codice fiscale");
                String cf = user_scanner.next();
                cf = cf.toUpperCase();
                msg_to_send = "CODICE " + cf;
                System.out.println("DEBUG: Sending " + msg_to_send);
                pw.println(msg_to_send);
                pw.flush();

                msg_received = server_scanner.nextLine();


                if (msg_received.equals("CUOCO")) {
                    boolean gocuoco = true;
                    while (gocuoco) {
                        System.out.println("---------------------------");
                        System.out.println("0 - Aggiungi Pietanza");
                        System.out.println("1 - Rimuovi Pietanza");
                        System.out.println("2 - Mostra Menù");
                        System.out.println("3 - Mostra Tavoli");
                        System.out.println("4 - Mostra Comanda");
                        System.out.println("5 - QUIT");
                        System.out.println("---------------------------");
                        System.out.println("Enter your choice-->");
                        choice = user_scanner.nextInt();
                        switch (choice) {
                            case 0:
                                System.out.println("Inserisci Nome: ");
                                String nome = user_scanner.next();
                                nome=nome.toUpperCase();
                                System.out.println("Inserisci Prezzo: ");
                                int prezzo = user_scanner.nextInt();
                                System.out.println("Inserisci Tipo: ");
                                String tipo = user_scanner.next();

                                msg_to_send = "ADD_Pietanza " + nome + " " + prezzo + " " + tipo;
                                System.out.println("DEBUG: Sending " + msg_to_send);
                                pw.println(msg_to_send);
                                pw.flush();
                                msg_received = server_scanner.nextLine();
                                if (msg_received.equals("ADD_OK")) {
                                    System.out.println("Pietanza aggiunta al menu");
                                } else if (msg_received.equals("ADD_ERROR")) {
                                    System.out.println("Errore!!! Pietanza non aggiunta");
                                } else {
                                    System.out.println("ERROR: Unknown message-> " + msg_received);
                                }
                                break;
                            case 1:
                                System.out.println("Inserisci il nome della pietanza da eliminare: ");
                                String nomer = user_scanner.next();
                                nomer = nomer.toUpperCase();
                                msg_to_send = "REMOVE_Pietanza " + nomer;
                                System.out.println("DEBUG: Sending " + msg_to_send);
                                pw.println(msg_to_send);
                                pw.flush();

                                msg_received = server_scanner.nextLine();

                                if (msg_received.equals("REMOVE_OK")) {
                                    System.out.println("Pietanza eliminata!");
                                } else if (msg_received.equals("REMOVE_ERROR")) {
                                    System.out.println("Errore!!!");
                                } else {
                                    System.out.println("ERROR: Unknown message-> " + msg_received);
                                }
                                break;
                            case 2:
                                msg_to_send = "LIST_MENU";
                                pw.println(msg_to_send);
                                pw.flush();

                                msg_received = server_scanner.next();
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
                                msg_to_send = "LIST_TAVOLI";
                                pw.println(msg_to_send);
                                pw.flush();

                                msg_received = server_scanner.next();
                                boolean listingt = true;
                                if (msg_received.equals("BEGIN")) {
                                    System.out.println("Receiving list...");
                                    while (listingt) {
                                        msg_received = server_scanner.nextLine();
                                        if (msg_received.equals("END")) {
                                            listingt = false;
                                            System.out.println("List ended");
                                        } else {//printing the person
                                            System.out.println(msg_received);
                                        }
                                    }
                                } else {
                                    System.out.println("Unknown Response: " + msg_received);
                                }
                                break;
                            case 4:
                                System.out.println("Inserisci ID Tavolo ");
                                int idt = user_scanner.nextInt();
                                msg_to_send = "SHOW_Comanda " + idt;

                                System.out.println("DEBUG: Sending " + msg_to_send);
                                pw.println(msg_to_send);
                                pw.flush();

                                msg_received = server_scanner.next();
                                boolean listingc = true;
                                if (msg_received.equals("BEGIN")) {
                                    System.out.println("Receiving list...");
                                    while (listingc) {
                                        msg_received = server_scanner.nextLine();
                                        if (msg_received.equals("END")) {
                                            listingc = false;
                                            System.out.println("List ended");
                                        } else {//printing the person
                                            System.out.println(msg_received);
                                        }
                                    }
                                } else {
                                    System.out.println("Unknown Response: " + msg_received);
                                }
                                break;
                            case 5:
                                gocuoco = false;
                                System.out.println("Torna indietro");

                                break;
                        }
                    }
                } else if (msg_received.equals("CAMERIERE")) {
                    boolean gocameriere = true;
                    while (gocameriere) {
                        System.out.println("---------------------------");
                        System.out.println("0 - Crea Tavolo");
                        System.out.println("1 - Aggiungi Pietanza al Tavolo");
                        System.out.println("2 - Conto");
                        System.out.println("3 - Mostra Menù");
                        System.out.println("4 - Mostra Comanda");
                        System.out.println("5 - Elimina Tavolo");
                        System.out.println("6 - QUIT");
                        System.out.println("---------------------------");
                        System.out.println("Enter your choice-->");
                        choice = user_scanner.nextInt();
                        switch (choice) {
                            case 0:
                                System.out.println("Inserisci ID Tavolo: ");
                                int id = user_scanner.nextInt();
                                System.out.println("Inserisci Numero Coperti: ");
                                int coperti = user_scanner.nextInt();
                                msg_to_send = "TAVOLO " + id + " " + coperti;
                                System.out.println("DEBUG: Sending " + msg_to_send);
                                pw.println(msg_to_send);
                                pw.flush();

                                msg_received = server_scanner.nextLine();
                                if (msg_received.equals("ADD_OK")) {
                                    System.out.println("Tavolo Aggiunto");
                                } else if (msg_received.equals("ADD_ERROR")) {
                                    System.out.println("Errore!!! Tavolo non aggiunto");
                                } else {
                                    System.out.println("ERROR: Unknown message-> " + msg_received);
                                }

                                break;
                            case 1:
                                System.out.println("Inserisci ID Tavolo ");
                                int idt = user_scanner.nextInt();
                                System.out.println("Inserisci Nome Pietanza: ");
                                String nome = user_scanner.next();
                                nome = nome.toUpperCase();

                                msg_to_send = "ADD_Piet_Tav " + idt + " " + nome;

                                System.out.println("DEBUG: Sending " + msg_to_send);
                                pw.println(msg_to_send);
                                pw.flush();

                                msg_received = server_scanner.nextLine();
                                if (msg_received.equals("ADD_OK")) {
                                    System.out.println("Pietanza aggiunta al tavolo");
                                } else if (msg_received.equals("ADD_ERROR")) {
                                    System.out.println("Errore!!! Pietanza non aggiunta");
                                } else {
                                    System.out.println("ERROR: Unknown message-> " + msg_received);
                                }

                                break;
                            case 2:
                                System.out.println("Inserire ID del tavolo");
                                int idT = user_scanner.nextInt();
                                msg_to_send = "CONTO " + idT;
                                pw.println(msg_to_send);
                                pw.flush();


                                msg_received = server_scanner.next();
                                System.out.println("Il conto del tavolo "+idT+" e': "+msg_received);

                                break;
                            case 3:
                                msg_to_send = "LIST_MENU";
                                pw.println(msg_to_send);
                                pw.flush();

                                msg_received = server_scanner.next();
                                boolean listing = true;
                                if (msg_received.equals("BEGIN")) {
                                    System.out.println("Receiving list...");
                                    while (listing) {
                                        msg_received = server_scanner.nextLine();
                                        if (msg_received.equals("END")) {
                                            listing = false;
                                            System.out.println("List ended");
                                        } else {
                                            System.out.println(msg_received);
                                        }
                                    }
                                } else {
                                    System.out.println("Unknown Response: " + msg_received);
                                }
                                break;
                            case 4:
                                System.out.println("Inserisci ID Tavolo ");
                                int idtt = user_scanner.nextInt();
                                msg_to_send = "SHOW_Comanda " + idtt;

                                System.out.println("DEBUG: Sending " + msg_to_send);
                                pw.println(msg_to_send);
                                pw.flush();

                                msg_received = server_scanner.next();
                                boolean listingc = true;
                                if (msg_received.equals("BEGIN")) {
                                    System.out.println("Receiving list...");
                                    while (listingc) {
                                        msg_received = server_scanner.nextLine();
                                        if (msg_received.equals("END")) {
                                            listingc = false;
                                            System.out.println("List ended");
                                        } else {//printing the person
                                            System.out.println(msg_received);
                                        }
                                    }
                                } else {
                                    System.out.println("Unknown Response: " + msg_received);
                                }
                                break;
                            case 5:
                                System.out.println("Inserire ID del tavolo da eliminare");
                                int idTa = user_scanner.nextInt();
                                msg_to_send = "REMOVE_Tavolo " + idTa;
                                pw.println(msg_to_send);
                                pw.flush();

                                msg_received = server_scanner.nextLine();

                                if (msg_received.equals("REMOVE_OK")) {
                                    System.out.println("Tavolo Eliminato!");
                                } else if (msg_received.equals("REMOVE_ERROR")) {
                                    System.out.println("Errore!!!");
                                } else {
                                    System.out.println("ERROR: Unknown message-> " + msg_received);
                                }
                            case 6:
                                gocameriere = false;
                                System.out.println("Torna Indietro");
                                break;

                        }
                    }
                }
                break;
                case 1:
                    godip=false;
                    System.out.println("Torno indietro");
            }
        }
    }
}
