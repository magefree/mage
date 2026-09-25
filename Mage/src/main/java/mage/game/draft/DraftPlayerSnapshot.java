package mage.game.draft;

import mage.cards.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Draft data of one player at one moment, copied under draft's players lock,
 * so client views (pack/pick numbers, booster, picks) are consistent with each other
 * 
 * Warning, if you add new lookup fields then see DraftImpl about data sync
 *
 * @author JayDi85
 */
public class DraftPlayerSnapshot {

    private final int boosterNum;
    private final int cardNum;
    private final List<String> playerNames;
    private final List<Card> booster;
    private final List<Card> picks;
    private final boolean picking;

    public DraftPlayerSnapshot(int boosterNum, int cardNum, List<String> playerNames,
                               List<Card> booster, List<Card> picks, boolean picking) {
        this.boosterNum = boosterNum;
        this.cardNum = cardNum;
        this.playerNames = Collections.unmodifiableList(new ArrayList<>(playerNames));
        this.booster = booster == null ? Collections.emptyList() : Collections.unmodifiableList(new ArrayList<>(booster));
        this.picks = Collections.unmodifiableList(new ArrayList<>(picks));
        this.picking = picking;
    }

    public int getBoosterNum() {
        return boosterNum;
    }

    public int getCardNum() {
        return cardNum;
    }

    public List<String> getPlayerNames() {
        return playerNames;
    }

    public List<Card> getBooster() {
        return booster;
    }

    public List<Card> getPicks() {
        return picks;
    }

    public boolean isPicking() {
        return picking;
    }
}
