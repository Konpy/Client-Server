
import java.net.*;
import java.io.*;
import java.util.Scanner;
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
public class Client 
{
    public static void main(String[] args) throws IOException
    {
        try
        {
           //Commienza una connection del cliente al server
           Scanner scn = new Scanner(System.in);
           Socket sock = new Socket ("127.0.0.1",6013);
           System.out.println("Cliente Connectado");
           
           /*Tomo un numero entero del terminal como un string guardado en DataInput
           y mando ese numero al Server en DataOutput*/
           DataInputStream input = new DataInputStream(sock.getInputStream());      
           DataOutputStream output = new DataOutputStream(sock.getOutputStream());
           
               //Output de mensage de que hacer desde el server luego guarda el valor
               //entrado a tosend para mandarlo al server
               System.out.println(input.readUTF());
               String tosend = scn.nextLine();
               output.writeUTF(tosend);
               
               //Verifica si no quieres utilizar el progama
               if (tosend.equals("Exit"))
               {
                  System.out.println("Closing this connection : " + sock);
                  sock.close();
                  System.out.println("Connection closed");                  
               }
               
               //Datos recibidos del servidor que se han calculado
               int received = input.readInt();
               System.out.println("For " + tosend + " The prime numbers between it are: " + received);
               
               //Cierro mis connecciones
               scn.close();
               input.close();
               output.close();
               sock.close();
        } 
        catch(UnknownHostException u)
        {
            System.out.println(u);
        }
        catch(IOException i)
        {
            System.out.println(i);
        }    
    }    
}
