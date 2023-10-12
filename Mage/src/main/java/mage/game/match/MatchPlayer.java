package mage.game.match;

import mage.cards.Card;
import mage.cards.decks.Deck;
import mage.cards.decks.DeckValidator;
import mage.players.Player;

import java.io.Serializable;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class MatchPlayer implements Serializable {

    private int wins;
    private int winsNeeded;
    private boolean matchWinner;

    private Deck deck;
    private Player player;
    private String name;

    private boolean quit;
    private boolean doneSideboarding;
    private int priorityTimeLeft; // keep left time for next game

    public MatchPlayer(Player player, Deck deck, Match match) {
        this.player = player;
        this.deck = deck;
        this.wins = 0;
        this.winsNeeded = match.getWinsNeeded();
        this.doneSideboarding = true;
        this.quit = false;
        this.name = player.getName();
        this.matchWinner = false;
    }

    /**
     * Create match player's copy for simulated/ai games,
     * so game and cards can get access to player's deck
     *
     * @param newPlayer
     * @return
     */
    public MatchPlayer(final MatchPlayer source, Player newPlayer) {
        this.wins = source.wins;
        this.winsNeeded = source.winsNeeded;
        this.matchWinner = source.matchWinner;
        this.deck = source.deck;
        this.player = newPlayer; // new
        this.name = newPlayer.getName(); // new
        this.quit = source.quit;
        this.doneSideboarding = source.doneSideboarding;
        this.priorityTimeLeft = source.priorityTimeLeft;
    }

    public int getPriorityTimeLeft() {
        return priorityTimeLeft;
    }

    public void setPriorityTimeLeft(int priorityTimeLeft) {
        this.priorityTimeLeft = priorityTimeLeft;
    }

    public int getWins() {
        return wins;
    }

    public int getWinsNeeded() {
        return winsNeeded;
    }

    public void addWin() {
        this.wins++;
    }

    public Deck getDeck() {
        return deck;
    }

    public void submitDeck(Deck deck) {
        this.deck = deck;
        this.doneSideboarding = true;
    }

    public void updateDeck(Deck deck) {
        if (this.deck != null) {
            // preserver deck name, important for Tiny Leaders format
            deck.setName(this.getDeck().getName());
            // preserve the original deck hash code before sideboarding to give no information if cards were swapped
            deck.setDeckHashCode(this.getDeck().getDeckHashCode());
        }
        this.deck = deck;
    }

    public Deck generateDeck(DeckValidator deckValidator) {
        // auto complete deck
        while (deck.getMaindeckCards().size() < deckValidator.getDeckMinSize() && !deck.getSideboard().isEmpty()) {
            Card card = deck.getSideboard().iterator().next();
            deck.getCards().add(card);
            deck.getSideboard().remove(card);
        }
        return deck;
    }

    public Player getPlayer() {
        return player;
    }

    public void setSideboarding() {
        this.doneSideboarding = false;
    }

    public boolean isDoneSideboarding() {
        return this.doneSideboarding;
    }

    public boolean hasQuit() {
        return quit;
    }

    public void setQuit(boolean quit) {
        this.doneSideboarding = true;
        this.quit = quit;
    }

    public boolean isMatchWinner() {
        return matchWinner;
    }

    public void setMatchWinner(boolean matchWinner) {
        this.matchWinner = matchWinner;
    }

    public void cleanUpOnMatchEnd() {
        // Free resources that are not needed after match end
        this.deck = null;
        this.player.cleanUpOnMatchEnd();
        // this.player = null;
    }

    public void cleanUp() {
        // Free resources that are not needed after match end
        this.player = null;
    }

    public String getName() {
        return name;
    }

}
