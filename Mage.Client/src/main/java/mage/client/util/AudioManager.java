package mage.client.util;

import mage.client.constants.Constants;
import org.apache.log4j.Logger;

import javax.sound.sampled.*;
import java.io.File;

/**
 * Manager class for playing audio files.
 *
 * @author nantuko
 */
public class AudioManager {

    private static final Logger log = Logger.getLogger(AudioManager.class);

    /**
     * AudioManager singleton.
     */
    private static AudioManager audioManager = null;


    public static AudioManager getManager() {
        if (audioManager == null) {
            audioManager = new AudioManager();
            audioManager.nextPageClip = audioManager.loadClip(Constants.BASE_SOUND_PATH + "OnPrevPage.wav"); //sounds better than OnNextPage
            audioManager.prevPageClip = audioManager.loadClip(Constants.BASE_SOUND_PATH + "OnPrevPage.wav");
            audioManager.nextPhaseClip = audioManager.loadClip(Constants.BASE_SOUND_PATH + "OnNextPhase.wav");
            audioManager.endTurnClip = audioManager.loadClip(Constants.BASE_SOUND_PATH + "OnEndTurn.wav");
            audioManager.tapPermanentClip = audioManager.loadClip(Constants.BASE_SOUND_PATH + "OnTapPermanent.wav");
            audioManager.summonClip = audioManager.loadClip(Constants.BASE_SOUND_PATH + "OnSummon.wav");
            audioManager.drawClip = audioManager.loadClip(Constants.BASE_SOUND_PATH + "OnDraw.wav");
            audioManager.buttonOkClip = audioManager.loadClip(Constants.BASE_SOUND_PATH + "OnButtonOk.wav");
            audioManager.buttonCancelClip = audioManager.loadClip(Constants.BASE_SOUND_PATH + "OnButtonCancel.wav");
            audioManager.attackClip = audioManager.loadClip(Constants.BASE_SOUND_PATH + "OnAttack.wav");
            audioManager.blockClip = audioManager.loadClip(Constants.BASE_SOUND_PATH + "OnBlock.wav");
            audioManager.addPermanentClip = audioManager.loadClip(Constants.BASE_SOUND_PATH + "OnAddPermanent.wav");
            audioManager.addArtifactClip = audioManager.loadClip(Constants.BASE_SOUND_PATH + "OnAddArtifact.wav");
            audioManager.updateStackClip = audioManager.loadClip(Constants.BASE_SOUND_PATH + "OnStackNew.wav");
            audioManager.onHover = audioManager.loadClip(Constants.BASE_SOUND_PATH + "OnHover.wav");
        }
        return audioManager;
    }

    public static void playNextPage() {
        checkAndPlayClip(getManager().nextPageClip);
    }

    public static void playPrevPage() {
        checkAndPlayClip(getManager().prevPageClip);
    }

    public static void playNextPhase() {
        checkAndPlayClip(getManager().nextPhaseClip);
    }

    public static void playEndTurn() {
        checkAndPlayClip(getManager().endTurnClip);
    }

    public static void playTapPermanent() {
        checkAndPlayClip(getManager().tapPermanentClip);
    }

    public static void playSummon() {
        checkAndPlayClip(getManager().summonClip);
    }

    public static void playDraw() {
        checkAndPlayClip(getManager().drawClip);
    }

    public static void playButtonOk() {
        checkAndPlayClip(getManager().buttonOkClip);
    }

    public static void playButtonCancel() {
        checkAndPlayClip(getManager().buttonCancelClip);
    }

    public static void playAttack() {
        checkAndPlayClip(getManager().attackClip);
    }

    public static void playBlock() {
        checkAndPlayClip(getManager().blockClip);
    }

    public static void playAddPermanent() {
        checkAndPlayClip(getManager().addPermanentClip);
    }

    public static void playAddArtifact() {
        checkAndPlayClip(getManager().addArtifactClip);
    }

    public static void playStackNew() {
        checkAndPlayClip(getManager().updateStackClip);
    }

    public static void playOnHover() {
        checkAndPlayClip(getManager().onHover);
    }

    private static void checkAndPlayClip(Clip clip) {
        try {
            if (clip != null) {
                audioManager.play(clip);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play(final Clip clip) {
        new Thread(new Runnable() {
            public void run() {
                clip.setFramePosition(0);
                clip.start();
            }
        }).run();
    }

    private Clip loadClip(String filename) {
        try {
            File soundFile = new File(filename);
            AudioInputStream soundIn = AudioSystem
                    .getAudioInputStream(soundFile);
            AudioFormat format = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED, AudioSystem.NOT_SPECIFIED,
                    16, 2, 4, AudioSystem.NOT_SPECIFIED, true);
            DataLine.Info info = new DataLine.Info(Clip.class, format);

            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.open(soundIn);

            return clip;
        } catch (Exception e) {
            //e.printStackTrace();
            log.error("Couldn't load sound: " + filename + ".");
        }

        return null;
    }

    private Clip nextPageClip = null;
    private Clip prevPageClip = null;
    private Clip nextPhaseClip = null;
    private Clip endTurnClip = null;
    private Clip tapPermanentClip = null;
    private Clip summonClip = null;
    private Clip drawClip = null;
    private Clip buttonOkClip = null;
    private Clip buttonCancelClip = null;
    private Clip attackClip = null;
    private Clip blockClip = null;
    private Clip addPermanentClip = null;
    private Clip addArtifactClip = null;
    private Clip updateStackClip = null;
    private Clip onHover = null;
}
