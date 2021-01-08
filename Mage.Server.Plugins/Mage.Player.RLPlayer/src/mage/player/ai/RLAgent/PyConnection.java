package mage.player.ai.RLAgent;


import java.net.*;
import java.io.*;
import org.apache.log4j.Logger;
import org.nd4j.shade.wstx.sw.OutputElementBase;

public class PyConnection {
    BufferedReader reader;
    InputStreamReader input;
    DataOutputStream dataout;
    BufferedOutputStream buff;
    Socket socket;
    private static final Logger logger = Logger.getLogger(PyConnection.class);
    public PyConnection(int port){
        try {
            socket = new Socket("localhost", port);
            OutputStream output = socket.getOutputStream();
            buff=new BufferedOutputStream(output);
            dataout=new DataOutputStream(buff);
            input = new InputStreamReader(socket.getInputStream());
            reader=new BufferedReader(input);
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
        try{
            String message=repr.asJsonString();
            long messageLen=message.length();
            dataout.writeLong(messageLen);
            dataout.writeBytes(message);
            buff.flush();
        }catch (IOException ex) {

            System.out.println("I/O error: " + ex.getMessage());
            System.exit(-1);
        }

    }
    int read(){
        String message="";
        try{
            //logger.info("reading");
            message = reader.readLine();
            //logger.info("recieved message: "+message);
            int result=Integer.parseInt(message);
            return result;
        }
        catch (IOException ex) {
            System.out.println("I/O error in read: " + ex.getMessage());
            System.exit(-1);
            return -1;
        }
    }
}
