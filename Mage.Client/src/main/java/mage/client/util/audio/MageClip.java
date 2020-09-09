

package mage.client.util.audio;

import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author LevelX2
 */
public class MageClip {
    private static final Logger log = Logger.getLogger(MageClip.class);

    private final AudioGroup audioGroup;
    private final String filename;
    private final byte[] buf;

    public MageClip(String filename, AudioGroup audioGroup) {
        this.filename = filename;
        this.audioGroup = audioGroup;
        this.buf = loadStream();
    }

    private byte[] loadStream() {
        File file = new File(filename);
        try {
            AudioInputStream soundIn = AudioSystem.getAudioInputStream(file);
            ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
            copy(soundIn, bytesOut);
            return bytesOut.toByteArray();
        } catch (UnsupportedAudioFileException | IOException e) {
            log.warn("Failed to read " + filename, e);
            return null;
        }
    }

    private static void copy(InputStream source, OutputStream sink) throws IOException {
        byte[] buf = new byte[1024];
        int n;
        while ((n = source.read(buf)) > 0) {
            sink.write(buf, 0, n);
        }
    }

    public AudioGroup getAudioGroup() {
        return audioGroup;
    }

    public byte[] getBuffer() {
        return buf;
    }

    public String getFilename() {
        return filename;
    }

}
