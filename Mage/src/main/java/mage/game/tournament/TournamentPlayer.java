package mage.game.tournament;

import mage.cards.decks.Deck;
import mage.constants.TournamentPlayerState;
import mage.game.result.ResultProtos.TourneyPlayerProto;
import mage.game.result.ResultProtos.TourneyQuitStatus;
import mage.players.Player;
import mage.players.PlayerType;
import mage.util.DeckBuildUtils;
import mage.util.TournamentUtil;
import org.apache.log4j.Logger;

import java.util.Set;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class TournamentPlayer {

    private static final Logger logger = Logger.getLogger(TournamentPlayer.class);

    protected int points;
    protected PlayerType playerType;
    protected TournamentPlayerState state;
    protected String stateInfo;
    protected String disconnectInfo;
    protected Player player;
    protected Deck deck = null;
    protected String results;
    protected boolean eliminated = false;
    protected boolean quit = false;
    protected boolean doneConstructing;
    protected boolean joined = false;
    protected TourneyQuitStatus quitStatus = TourneyQuitStatus.NO_TOURNEY_QUIT;
    protected TournamentPlayer replacedTournamentPlayer;

    public TournamentPlayer(Player player, PlayerType playerType) {
        this.player = player;
        this.playerType = playerType;
        this.state = TournamentPlayerState.JOINED;
        this.stateInfo = "";
        this.disconnectInfo = "";
        this.results = "";
    }

    public Player getPlayer() {
        return player;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public Deck getDeck() {
        return deck;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean isEliminated() {
        return eliminated;
    }

    public void setEliminated() {
        this.setDisconnectInfo("");
        this.setState(TournamentPlayerState.ELIMINATED);
        this.eliminated = true;
    }

    public boolean isJoined() {
        return joined;
    }

    public void setJoined() {
        this.joined = true;
    }

    public void setConstructing() {
        this.setState(TournamentPlayerState.CONSTRUCTING);
        this.doneConstructing = false;
    }

    public void submitDeck(Deck deck) {
        this.deck = deck; // Check if player manipulated available cards???
        this.doneConstructing = true;
        this.setState(TournamentPlayerState.WAITING);
    }

    public boolean updateDeck(Deck deck, boolean ignoreMainBasicLands) {
        // used for auto-save deck

        // make sure it's the same deck (player do not add or remove something)
        boolean isGood = (this.getDeck().getDeckHash(ignoreMainBasicLands) == deck.getDeckHash(ignoreMainBasicLands));
        if (!isGood) {
            logger.error("Found cheating tourney player " + player.getName()
                    + " with changed deck, main " + deck.getCards().size() + ", side " + deck.getSideboard().size());
            deck.getCards().clear();
            deck.getSideboard().clear();
        }
        this.deck = deck;
        return isGood;
    }

    /**
     * If user fails to submit deck on time, submit deck as is if meets minimum size,
     * else add basic lands per suggested land counts
     */
    public Deck generateDeck(int minDeckSize) {
        if (deck.getMaindeckCards().size() < minDeckSize) {
            int[] lands = DeckBuildUtils.landCountSuggestion(minDeckSize, deck.getMaindeckCards());
            Set<String> landSets = TournamentUtil.getLandSetCodeForDeckSets(deck.getExpansionSetCodes());
            deck.getCards().addAll(TournamentUtil.getLands("Plains", lands[0], landSets));
            deck.getCards().addAll(TournamentUtil.getLands("Island", lands[1], landSets));
            deck.getCards().addAll(TournamentUtil.getLands("Swamp", lands[2], landSets));
            deck.getCards().addAll(TournamentUtil.getLands("Mountain", lands[3], landSets));
            deck.getCards().addAll(TournamentUtil.getLands("Forest", lands[4], landSets));
        }
        this.doneConstructing = true;
        this.setState(TournamentPlayerState.WAITING);
        return deck;
    }

    public boolean isDoneConstructing() {
        return this.doneConstructing;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public String getResults() {
        return this.results;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public TournamentPlayerState getState() {
        return state;
    }

    public void setState(TournamentPlayerState state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public String getDisconnectInfo() {
        return disconnectInfo;
    }

    public void setDisconnectInfo(String disconnectInfo) {
        this.disconnectInfo = disconnectInfo;
    }

    public boolean hasQuit() {
        return quit;
    }

    public void setQuit(String info, TourneyQuitStatus status) {
        setEliminated();
        this.setState(TournamentPlayerState.CANCELED);
        this.setStateInfo(info);
        this.quit = true;
        this.doneConstructing = true;
        this.quitStatus = status;
    }

    /**
     * Free resources no longer needed if tournament has ended
     */
    public void cleanUpOnTournamentEnd() {
        this.deck = null;
    }

    public void setStateAtTournamentEnd() {
        if (this.getState() == TournamentPlayerState.DRAFTING
                || this.getState() == TournamentPlayerState.CONSTRUCTING
                || this.getState() == TournamentPlayerState.DUELING
                || this.getState() == TournamentPlayerState.SIDEBOARDING
                || this.getState() == TournamentPlayerState.WAITING) {
            this.setState(TournamentPlayerState.FINISHED);
        }
    }

    public boolean isInTournament() {
        return !(this.getState() == TournamentPlayerState.CANCELED)
                && !(this.getState() == TournamentPlayerState.ELIMINATED)
                && !(this.getState() == TournamentPlayerState.FINISHED);
    }

    public TournamentPlayer getReplacedTournamentPlayer() {
        return this.replacedTournamentPlayer;
    }

    public void setReplacedTournamentPlayer(TournamentPlayer player) {
        this.replacedTournamentPlayer = player;
    }

    public TourneyPlayerProto toProto() {
        return TourneyPlayerProto.newBuilder()
                .setName(this.player.getName())
                .setPlayerType(this.playerType.toString())
                .setQuit(this.quitStatus)
                .build();
    }
}
