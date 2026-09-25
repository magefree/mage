
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
    protected boolean boosterSent; // server sent current booster
    protected boolean boosterLoaded; // client confirmed current booster (computer do not confirm and always false)
    protected boolean joined = false;
    protected Set<UUID> hiddenCards;
    protected UUID markedCard; // user's choice for autopick on pick timeout, from the current booster only
    protected long pickDeadline; // end time of the current pick (from the first booster send to that player), 0 - not sent yet or unlimited time

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
        booster.remove(card);
        picking = false;
        markedCard = null;
    }

    public void setBoosterAndLoad(List<Card> booster) {
        this.booster = booster;
        this.boosterLoaded = false; // human will receive new pick, computer with choose new pick
    }

    public List<Card> getBooster() {
        if (booster == null) {
            return null;
        }
        synchronized (booster) {
            return new ArrayList<>(booster);
        }
    }

    public void setPickingAndSending() {
        // new round - new booster start to sending
        this.picking = true;
        this.boosterLoaded = false;
        this.boosterSent = false;
        this.markedCard = null;
        this.pickDeadline = 0;
    }

    public UUID getMarkedCard() {
        return markedCard;
    }

    public void setMarkedCard(UUID markedCard) {
        this.markedCard = markedCard;
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

    public boolean isBoosterSent() {
        return boosterSent;
    }

    public void setBoosterSent() {
        this.boosterSent = true;
        this.boosterLoaded = false; // new booster sent, so client must confirmed it
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

    public long getPickDeadline() {
        return pickDeadline;
    }

    public void setPickDeadline(long pickDeadline) {
        this.pickDeadline = pickDeadline;
    }
}
