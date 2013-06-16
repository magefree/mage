package mage.client.util;
import java.io.File;
import java.awt.List;
import javax.sound.sampled.*;
import mage.client.constants.Constants;
import mage.client.dialog.PreferencesDialog;
import org.apache.log4j.Logger;

/**
 *
 * @author renli
 */

public class MusicPlayer {
    private static final Logger log = Logger.getLogger(AudioManager.class);
    String filepath;
    String filename;
    List filelist = new List();
    static MusicPlayer player = null;
    
    
    //open file and add list
    private boolean open(){
    	filepath = Constants.BASE_MUSICS_PATH;
    	filelist.removeAll();
    	File filedir = new File(filepath);
    	File[] fileread = filedir.listFiles();
        if(fileread == null) return false;
    	if(fileread.length == 0)return false;
    	String filename;
    	for(File f:fileread){
    		filename = f.getName().toLowerCase();		
    		if(filename.endsWith(".mp3") || filename.endsWith(".wav")){	
    			filelist.add(filename);
    		}
    	}
        if(filelist.getItemCount() == 0) return false;
        return true;
    }
    
    public static void playBGM(){
        stopBGM();
    	if(player == null){
    		player = new MusicPlayer();
    	}       
        if(player.open()){
            player.play();
        }
    }
    
    public void play(){
        String soundsOn = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_MUSICS_ON, "true");
        if(soundsOn.equals("true")){
            player.breaked = false;
            player.breaked_out = false;
            player.stopped = false;
            Thread player = new Thread(new playerThread());
            player.start();
        }
    }
    
    public static void stopBGM(){
    	if(player != null){
    		player.stopped = true;
    		player.breaked_out = true;
    		player.breaked = true;
    		try {
		    Thread.sleep(100);
                } catch (Exception e) {
                    log.error("Thread error: " + e);
                }
    	}
    }

    	public volatile boolean breaked = false;
    	public volatile boolean breaked_out = false;
        public volatile boolean stopped = false;
        public volatile FloatControl volume;
    	AudioInputStream audioInputStream;
        AudioFormat audioFormat;
        SourceDataLine sourceDataLine;
  
       
      class playerThread extends Thread{  
    	private void load(File file){
    		try{
    			audioInputStream = AudioSystem.getAudioInputStream(file);
    			audioFormat = audioInputStream.getFormat();
    			// mp3 decode
    			if (audioFormat.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
    				audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
    					audioFormat.getSampleRate(), 16, audioFormat.getChannels(), audioFormat.getChannels() * 2,
                        audioFormat.getSampleRate(), false);
    				audioInputStream = AudioSystem.getAudioInputStream(audioFormat, audioInputStream);
    			}
    			//output
    			DataLine.Info dataLineInfo = new DataLine.Info(
                   SourceDataLine.class, audioFormat,
                   AudioSystem.NOT_SPECIFIED);
    			sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
    			sourceDataLine.open(audioFormat);
    			volume = (FloatControl)sourceDataLine.getControl(FloatControl.Type.MASTER_GAIN);
    			sourceDataLine.start();
    		}catch(Exception e){	
    			log.error("Couldn't load file: " + file + " " + e);
    		}
    		
    	}
        
    	public void run(){
    		try {
				Thread.sleep(100);
			} catch (Exception e) {
			}
    		while(!stopped){
    			int it = (int)Math.abs(Math.random()*(filelist.getItemCount()));
    			File file = new File(filepath + filelist.getItem(it));
    			load(file);
    			Thread PlayThread = new Thread(new PlayThread());
    			PlayThread.start();
    			while (!(breaked || breaked_out)) {
    				try {
        				Thread.sleep(10);
        			} catch (Exception e) {
                                    log.error("Thread error: " + e);
        			}
        		}
    			breaked = false;
    		}
    	}
      };
    	
    class PlayThread extends Thread{
    	byte tempBuffer[] = new byte[320];  	
    	public void run(){
    		try{
                    sourceDataLine.flush();   
                    int len;
                    while ((len = audioInputStream.read(tempBuffer, 0,
	                tempBuffer.length)) != -1){
    			if(breaked_out) break;
    			if(len > 0) sourceDataLine.write(tempBuffer, 0, len);
    			}
    			//breaked or stopped
                    sourceDataLine.flush();
	            sourceDataLine.close();
	            breaked = true;
    		}catch(Exception e){
                    log.error("Thread error: " + e);
    		}
    	}	
    };   
}

