
package mage.game.draft;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.cards.Card;
import mage.cards.decks.Deck;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class DraftPlayer {

    protected UUID id;
    protected Player player;
    protected Deck deck;
    protected List<Card> booster;
    protected boolean picking;
    protected boolean boosterLoaded;
    protected boolean joined = false;
    protected Set<UUID> hiddenCards;

    public DraftPlayer(Player player) {
        id = UUID.randomUUID();
        this.player = player;
        this.deck = new Deck();
        hiddenCards = new HashSet<>();
    }

    public UUID getId() {
        return id;
    }

    public Player getPlayer() {
        return player;
    }

    public void prepareDeck() {
        if (!hiddenCards.isEmpty()) {
            Set<Card> cardsToDeck = new HashSet<>();
            for (Card card : deck.getSideboard()) {
                if (!hiddenCards.contains(card.getId())) {
                    cardsToDeck.add(card);
                }
            }
            for (Card card : cardsToDeck) {
                deck.getSideboard().remove(card);
                deck.getCards().add(card);
            }
        }
    }

    public Deck getDeck() {
        return deck;
    }

    public void addPick(Card card, Set<UUID> hiddenCards) {
        deck.getSideboard().add(card);
        if (hiddenCards != null) {
            this.hiddenCards = hiddenCards;
        }
        synchronized (booster) {
            booster.remove(card);
        }
        picking = false;
    }

    public void setBooster(List<Card> booster) {
        this.booster = booster;
    }

    public List<Card> getBooster() {
        if (booster == null) {
            return null;
        }
        synchronized (booster) {
            return new ArrayList<>(booster);
        }
    }

    public void setPicking() {
        picking = true;
    }

    public boolean isPicking() {
        return picking;
    }

    public boolean isJoined() {
        return joined;
    }

    public void setJoined() {
        this.joined = true;
    }
    
    public void setBoosterLoaded() {
        boosterLoaded = true;
    }
    
    public void setBoosterNotLoaded() {
        boosterLoaded = false;
    }
    
    public boolean isBoosterLoaded() {
        return boosterLoaded;
    }

}
