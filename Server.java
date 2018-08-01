import java.io.*;
import java.net.*;
/**
 *
 * @author Gabriel Fontanez
 * Num Est 842-15-3288
 * Sistemas Operativos
 * Proyecto #1
 * 
 * Descripcion: Este Programa espera para conectar a algun cliente en 
 * la misma computadora y luego de que el cliente se encuenta pide desde
 * el cliente un numero y el servidor devolvera los numeros primos
 * dentro de ese numero entero positivo.
 */

public class Server 
{
    public static void main(String[] args) throws IOException
    {
        //Comienso el servidor desde aqui
            ServerSocket sock = new ServerSocket(6013);
            System.out.println("Servidor Ha Comenzado");
            System.out.println("Esperando Al Cliente");            
            
            /*Aqui espero a encontrar a un cliente valido hasta que se cierre el
            Programa manualmente.*/
            while(true)
            {
                Socket client = null; 
                //Donde connecto el cliente con el servidor al encontrar un cliente  
                try
                {
                    client = sock.accept();
                    
                    /*Indico en el servidor si se connecto un cliente indicando
                    su IP address, Port Num y Localport Num*/
                    System.out.println("A new Client has connected : " + client);
                    
                    /*Guardo el input del usuario sea numero o string el cual lo mandare
                    a una funcion llamada ClientHandler. Luego los datos recolectados
                    se envian desde el ClientHandler*/
                    
                    DataInputStream input = new DataInputStream(client.getInputStream());
                    DataOutputStream output = new DataOutputStream(client.getOutputStream());
                    System.out.println("Assigning new thread for this client");
                   // Creo mis threads aqui
                        Thread t = new ClientHandler(client, input, output);
                        t.start();
                    }
                    catch (Exception e)
                    {
                        sock.close();
                    }
            }
    }
}

class ClientHandler extends Thread
{
    /*Guardan el socket, input del usuario y el output que se mandara
    al cliente luego de hacer los calculos requeridos*/
    protected DataInputStream input;
    protected  DataOutputStream output;
    protected Socket client;

    public ClientHandler(Socket client, DataInputStream input, DataOutputStream output)
    {
        this.client = client;
        this.input = input;
        this.output = output;
    }
 
    int Exp = 0;
    /*Corrida de los threads empiesan aqui*/
    public void run()         
    {
        /*String que guarda el numero recibido del usuario*/
        String received;
            try
            {
                /*Mensaje indicando que hacer con el programa desde el cliente*/
                output.writeUTF("Enter a positive number (Type Exit to close the client)");
                received = input.readUTF();
                
                /*Si el cliente manda un numero negativo cierra el cliente desde el server*/
                if(Integer.parseInt(received) < 0)
                   { 
                       System.out.println("Client " + this.client + " sent a negative number...");
                       System.out.println("Closing this connection.");
                       this.client.close();
                       System.out.println("Connection closed");
                      
                   }
                /* cuenta la cantidad de primos entre 2 y lim de la manera más 
                   ineficiente que se me ocurre. (subrouting creado por el Profesor Jose Sotero)*/
                if(Integer.parseInt(received) >=0 )
                {
                    {
                        int cont = 0;
                        for (int n=2; n <= Integer.parseInt(received); n++)
                        {
                            int div = 2;
                            for (;div < n; div++)
                                if (n % div == 0) break;
                            if (div == n) cont++;
                        }
                        System.out.println("Prime Number Sent from client : " + cont);
                        int toreturn = cont;
                        output.writeInt(toreturn);
                    }
                }    
            }
            catch(IOException e)
            {
               e.printStackTrace();
            }
            //Cierro mis connecciones
            try
            {
                this.input.close();
                this.output.close();
                this.client.close();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }    
    }
}
