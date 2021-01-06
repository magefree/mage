package mage.player.ai.RLAgent;


import java.net.*;
import java.io.*;

public class PyConnection {
    PrintWriter writer;
    BufferedReader reader;
    public PyConnection(int port){
        try (Socket socket = new Socket("localhost", port)){
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
            InputStream input = socket.getInputStream();
            reader=new BufferedReader(new InputStreamReader(input));
        }
        catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
            System.exit(-1);
        } catch (IOException ex) {

            System.out.println("I/O error: " + ex.getMessage());
            System.exit(-1);
        }
    }
    void write(RepresentedGame repr){
        String message=repr.asJsonString();
        writer.println(message);
    }
    int read(){
        String message="";
        String line="";
        try{
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                message+=line;
            }
            int result=Integer.parseInt(message);
            return result;
        }
        catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
            return -1;
        }
    }
}
