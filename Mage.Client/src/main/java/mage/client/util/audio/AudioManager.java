package mage.client.util.audio;

import java.io.File;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import mage.client.constants.Constants;
import mage.client.dialog.PreferencesDialog;
import org.apache.log4j.Logger;

/**
 * Manager class for playing audio files.
 *
 * @author nantuko
 */
public class AudioManager {

    private static final Logger log = Logger.getLogger(AudioManager.class);

    private MageClip nextPageClip = null;
    private MageClip prevPageClip = null;
    private MageClip anotherTabClip = null;
    private MageClip nextPhaseClip = null;
    private MageClip endTurnClip = null;
    private MageClip tapPermanentClip = null;
    private MageClip summonClip = null;
    private MageClip diedCreatureClip = null;
    private MageClip drawClip = null;
    private MageClip buttonOkClip = null;
    private MageClip buttonCancelClip = null;
    private MageClip attackClip = null;
    private MageClip blockClip = null;
    private MageClip addPermanentClip = null;
    private MageClip addArtifactClip = null;
    private MageClip updateStackClip = null;
    private MageClip onHover = null;

    private MageClip onSkipButton = null;
    private MageClip onSkipButtonCancel = null;

    private MageClip onCountdown1 = null;
    private MageClip onDraftSelect = null;

    private MageClip tournamentStarted = null;
    private MageClip yourGameStarted = null;
    private MageClip playerJoinedTable = null;
    private MageClip playerSubmittedDeck = null;
    private MageClip playerWhispered = null;
    private MageClip playerLeft = null;
    private MageClip playerQuitTournament = null;
    private MageClip playerWon = null;
    private MageClip playerLost = null;
    /**
     * AudioManager singleton.
     */
    private static final AudioManager audioManager = new AudioManager();
    private final LinePool linePool = new LinePool();

    public static AudioManager getManager() {
        return audioManager;
    }

    public static void playNextPage() {
        if (audioManager.nextPageClip == null) {
            audioManager.nextPageClip = new MageClip(Constants.BASE_SOUND_PATH + "OnPrevPage.wav", AudioGroup.OtherSounds);
        }
        checkAndPlayClip(getManager().nextPageClip);
    }

    public static void playPrevPage() {
        if (audioManager.prevPageClip == null) {
            audioManager.prevPageClip = new MageClip(Constants.BASE_SOUND_PATH + "OnPrevPage.wav", AudioGroup.OtherSounds);
        }
        checkAndPlayClip(getManager().prevPageClip);
    }

    public static void playAnotherTab() {
        if (audioManager.anotherTabClip == null) {
            audioManager.anotherTabClip = new MageClip(Constants.BASE_SOUND_PATH + "OnNextPage.wav", AudioGroup.OtherSounds);
        }
        checkAndPlayClip(getManager().anotherTabClip);
    }

    public static void playNextPhase() {
        if (audioManager.nextPhaseClip == null) {
            audioManager.nextPhaseClip = new MageClip(Constants.BASE_SOUND_PATH + "OnNextPhase.wav", AudioGroup.GameSounds);
        }
        checkAndPlayClip(getManager().nextPhaseClip);
    }

    public static void playEndTurn() {
        if (audioManager.endTurnClip == null) {
            audioManager.endTurnClip = new MageClip(Constants.BASE_SOUND_PATH + "OnEndTurn.wav", AudioGroup.GameSounds);
        }
        checkAndPlayClip(getManager().endTurnClip);
    }

    public static void playTapPermanent() {
        if (audioManager.tapPermanentClip == null) {
            audioManager.tapPermanentClip = new MageClip(Constants.BASE_SOUND_PATH + "OnTapPermanent.wav", AudioGroup.GameSounds);
        }
        checkAndPlayClip(getManager().tapPermanentClip);
    }

    public static void playSummon() {
        if (audioManager.summonClip == null) {
            audioManager.summonClip = new MageClip(Constants.BASE_SOUND_PATH + "OnSummon.wav", AudioGroup.GameSounds);
        }
        checkAndPlayClip(getManager().summonClip);
    }

    public static void playDiedCreature() {
        if (audioManager.diedCreatureClip == null) {
            audioManager.diedCreatureClip = new MageClip(Constants.BASE_SOUND_PATH + "OnSummon-.wav", AudioGroup.GameSounds);
        }
        checkAndPlayClip(getManager().diedCreatureClip);
    }

    public static void playDraw() {
        if (audioManager.drawClip == null) {
            audioManager.drawClip = new MageClip(Constants.BASE_SOUND_PATH + "OnDraw.wav", AudioGroup.GameSounds);
        }
        checkAndPlayClip(getManager().drawClip);
    }

    public static void playButtonOk() {
        if (audioManager.buttonOkClip == null) {
            audioManager.buttonOkClip = new MageClip(Constants.BASE_SOUND_PATH + "OnButtonOk.wav", AudioGroup.GameSounds);
        }
        checkAndPlayClip(getManager().buttonOkClip);
    }

    public static void playButtonCancel() {
        if (audioManager.buttonCancelClip == null) {
            audioManager.buttonCancelClip = new MageClip(Constants.BASE_SOUND_PATH + "OnButtonCancel.wav", AudioGroup.SkipSounds);

        }
        checkAndPlayClip(getManager().buttonCancelClip);
    }

    public static void playAttack() {
        if (audioManager.attackClip == null) {
            audioManager.attackClip = new MageClip(Constants.BASE_SOUND_PATH + "OnAttack.wav", AudioGroup.GameSounds);
        }
        checkAndPlayClip(getManager().attackClip);
    }

    public static void playBlock() {
        if (audioManager.blockClip == null) {
            audioManager.blockClip = new MageClip(Constants.BASE_SOUND_PATH + "OnBlock.wav", AudioGroup.GameSounds);
        }
        checkAndPlayClip(getManager().blockClip);
    }

    public static void playAddPermanent() {
        if (audioManager.addPermanentClip == null) {
            audioManager.addPermanentClip = new MageClip(Constants.BASE_SOUND_PATH + "OnAddPermanent.wav", AudioGroup.GameSounds);
        }
        checkAndPlayClip(getManager().addPermanentClip);
    }

    public static void playAddArtifact() {
        if (audioManager.addArtifactClip == null) {
            audioManager.addArtifactClip = new MageClip(Constants.BASE_SOUND_PATH + "OnAddArtifact.wav", AudioGroup.GameSounds);
        }
        checkAndPlayClip(getManager().addArtifactClip);
    }

    public static void playStackNew() {
        if (audioManager.updateStackClip == null) {
            audioManager.updateStackClip = new MageClip(Constants.BASE_SOUND_PATH + "OnStackNew.wav", AudioGroup.GameSounds);
        }
        checkAndPlayClip(getManager().updateStackClip);
    }

    public static void playOnHover() {
        if (audioManager.onHover == null) {
            audioManager.onHover = new MageClip(Constants.BASE_SOUND_PATH + "OnHover.wav", AudioGroup.GameSounds);
        }
        checkAndPlayClip(getManager().onHover);
    }

    public static void playOnCountdown1() {
        if (audioManager.onCountdown1 == null) {
            audioManager.onCountdown1 = new MageClip(Constants.BASE_SOUND_PATH + "OnCountdown1.wav", AudioGroup.DraftSounds);
        }
        checkAndPlayClip(getManager().onCountdown1);
    }

    public static void playOnDraftSelect() {
        if (audioManager.onDraftSelect == null) {
            audioManager.onDraftSelect = new MageClip(Constants.BASE_SOUND_PATH + "OnDraftSelect.wav", AudioGroup.DraftSounds);
        }
        checkAndPlayClip(getManager().onDraftSelect);
    }

    public static void playOnSkipButton() {
        if (audioManager.onSkipButton == null) {
            audioManager.onSkipButton = new MageClip(Constants.BASE_SOUND_PATH + "OnSkipButton.wav", AudioGroup.SkipSounds);
        }
        checkAndPlayClip(getManager().onSkipButton);
    }

    public static void playOnSkipButtonCancel() {
        if (audioManager.onSkipButtonCancel == null) {
            audioManager.onSkipButtonCancel = new MageClip(Constants.BASE_SOUND_PATH + "OnSkipButtonCancel.wav", AudioGroup.SkipSounds);
        }
        checkAndPlayClip(getManager().onSkipButtonCancel);
    }

    public static void playPlayerJoinedTable() {
        if (audioManager.playerJoinedTable == null) {
            audioManager.playerJoinedTable = new MageClip(Constants.BASE_SOUND_PATH + "OnPlayerJoined.wav", AudioGroup.OtherSounds);
        }
        checkAndPlayClip(getManager().playerJoinedTable);
    }

    public static void playYourGameStarted() {
        if (audioManager.yourGameStarted == null) {
            audioManager.yourGameStarted = new MageClip(Constants.BASE_SOUND_PATH + "OnGameStart.wav", AudioGroup.OtherSounds);
        }
        checkAndPlayClip(getManager().yourGameStarted);
    }

    public static void playTournamentStarted() {
        if (audioManager.tournamentStarted == null) {
            audioManager.tournamentStarted = new MageClip(Constants.BASE_SOUND_PATH + "OnTournamentStart.wav", AudioGroup.OtherSounds);
        }
        checkAndPlayClip(getManager().tournamentStarted);
    }

    public static void playPlayerWhispered() {
        if (audioManager.playerWhispered == null) {
            audioManager.playerWhispered = new MageClip(Constants.BASE_SOUND_PATH + "OnPlayerWhispered.wav", AudioGroup.OtherSounds);
        }
        checkAndPlayClip(getManager().playerWhispered);
    }

    public static void playPlayerSubmittedDeck() {
        if (audioManager.playerSubmittedDeck == null) {
            audioManager.playerSubmittedDeck = new MageClip(Constants.BASE_SOUND_PATH + "OnPlayerSubmittedDeck.wav",
                    AudioGroup.OtherSounds);
        }
        checkAndPlayClip(getManager().playerSubmittedDeck);
    }

    public static void playPlayerLeft() {
        if (audioManager.playerLeft == null) {
            audioManager.playerLeft = new MageClip(Constants.BASE_SOUND_PATH + "OnPlayerLeft.wav", AudioGroup.OtherSounds);
        }
        checkAndPlayClip(getManager().playerLeft);
    }

    public static void playPlayerQuitTournament() {
        if (audioManager.playerQuitTournament == null) {
            audioManager.playerQuitTournament = new MageClip(Constants.BASE_SOUND_PATH + "OnPlayerQuitTournament.wav",
                    AudioGroup.OtherSounds);
        }
        checkAndPlayClip(getManager().playerQuitTournament);
    }

    public static void playPlayerLost() {
        if (audioManager.playerLost == null) {
            audioManager.playerLost = new MageClip(Constants.BASE_SOUND_PATH + "OnPlayerLost.wav", AudioGroup.GameSounds);
        }
        checkAndPlayClip(getManager().playerLost);
    }

    public static void playPlayerWon() {
        if (audioManager.playerWon == null) {
            audioManager.playerWon = new MageClip(Constants.BASE_SOUND_PATH + "OnPlayerWon.wav", AudioGroup.GameSounds);
        }
        checkAndPlayClip(getManager().playerWon);
    }

    private static void checkAndPlayClip(MageClip mageClip) {
        try {
            if (mageClip != null) {
                boolean playSound = false;
                switch (mageClip.getAudioGroup()) {
                case GameSounds:
                    playSound = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_SOUNDS_GAME_ON, "true").equals("true");
                    break;
                case DraftSounds:
                    playSound = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_SOUNDS_DRAFT_ON, "true").equals("true");
                    break;
                case SkipSounds:
                    playSound = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_SOUNDS_SKIP_BUTTONS_ON, "true").equals("true");
                    break;
                case OtherSounds:
                    playSound = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_SOUNDS_OTHER_ON, "true").equals("true");
                }

                if (playSound) {
                    audioManager.play(mageClip);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(AudioManager.class).fatal("Error while playing sound clip.", e);
        }
    }

    public void play(final MageClip mageClip) {
        linePool.playSound(mageClip);
    }

}
