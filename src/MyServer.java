import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {

    ServerSocket socket;
    Socket client_socket;
    private int port;
    int client_id = 0;

    UserList Ulist = new UserList();
    TavoloList ltavolo = new TavoloList();
    ConsumazioneList lconsumazione = new ConsumazioneList();
    Menu menu = new Menu();

    public static void main(String args[]){

        if(args.length!=1){
            System.out.println("Usage java MyServer <port>");
            return;

        }

        MyServer server = new MyServer(Integer.parseInt(args[0]));
        server.start();

    }

    public MyServer(int port){
        //qui possiamo mettere i controlli sulla porta
        System.out.println("Initializing server with port "+port);
        this.port=port;
    }

    public void start(){
        try {
            System.out.println("Starting server on port "+port);
            socket = new ServerSocket(port);
            System.out.println("Started server on port "+port);
            while (true) {
                System.out.println("Listening on port " + port);
                client_socket = socket.accept();
                System.out.println("Accepted connection from " + client_socket.getRemoteSocketAddress());

                ClientManager cm = new ClientManager(client_socket, Ulist,ltavolo,lconsumazione,menu);
                Thread t = new Thread(cm,"client_"+client_id);
                client_id++;
                t.start();


            }

        } catch (IOException e) {
            System.out.println("Could not start server on port "+port);
            e.printStackTrace();
        }
    }


}
