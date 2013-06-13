package mage.client.util;
import java.io.File;
import java.awt.List;
import javax.sound.sampled.*;


/**
 *
 * @author renli
 */

public class MusicPlayer {
 
    String filepath;
    String filename;
    List filelist = new List();
    static MusicPlayer player = null;
    
    public static String BASE_BGM_PATH =  "G:\\mp3\\东方\\TH13_-_东方神灵庙BGM\\";
    
    public MusicPlayer(){
    	open();
    }
    
    //open file and add list
    private void open(){
    	filepath = BASE_BGM_PATH;
    	filelist.removeAll();
    	File filedir = new File(filepath);
    	File[] fileread = filedir.listFiles();
    	if(fileread.length == 0){System.out.println("No file readed.");}
    	String filename;
    	for(File f:fileread){
    		filename = f.getName().toLowerCase();		
    		if(filename.endsWith(".mp3") || filename.endsWith(".wav")){	
    			filelist.add(filename);
    		}
    	}
    }
    
    public static void playBGM(){
    	if(player == null){
    		player = new MusicPlayer();
    	}
    	player.play();
    }
    
    public void play(){
    	player.stopped = false;
		player.breaked_out = false;
		player.breaked = false;
    	Thread player = new Thread(new playerThread());
    	player.start();
    }
    
    public static void stopBGM(){
    	if(player != null){
    		player.stopped = true;
    		player.breaked_out = true;
    		player.breaked = true;
    		try {
				Thread.sleep(100);
			} catch (Exception e) {
			}
    	}
    	System.out.println("stoped");
    }
    
    /* maximum value 6.0206 */
    public static void addVolume(){
    	try{
    	float v = player.volume.getValue();
    	player.volume.setValue(++v);
    	System.out.println("Volume: " + v);
    	}catch(IllegalArgumentException e){
    		e.printStackTrace();
    	}
    }
    /* minimum value -80 */
    public static void abstructVolume(){
    	try{
        	float v = player.volume.getValue();
        	player.volume.setValue(--v);
        	System.out.println("Volume: " + v);
        	}catch(IllegalArgumentException e){
        		e.printStackTrace();
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
    			e.printStackTrace();
	            System.exit(0);
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
    				System.out.println(filepath + filelist.getItem(it));
    			load(file);
    			Thread PlayThread = new Thread(new PlayThread());
    			PlayThread.start();
    				System.out.println("playing: " + filelist.getItem(it));	
    			while (!(breaked || breaked_out)) {
    				try {
        				Thread.sleep(10);
        			} catch (Exception e) {
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
    			int len;
    			while ((len = audioInputStream.read(tempBuffer, 0,
	                    tempBuffer.length)) != -1){
    				if(breaked_out) break;
    				if(len > 0) sourceDataLine.write(tempBuffer, 0, len);
    			}
    			//breaked or stopped
    			sourceDataLine.drain();
	            sourceDataLine.close();
	            breaked = true;
    		}catch(Exception e){
    			e.printStackTrace();
	            System.exit(0);
    		}
    	}	
    };   
}

