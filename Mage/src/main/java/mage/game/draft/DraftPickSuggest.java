package mage.game.draft;

import java.io.Serializable;
import java.util.UUID;

/**
 * Draft: suggested card to pick from the current booster (shown to a human as an icon, used by autopick on timeout)
 *
 * @author JayDi85
 */
public class DraftPickSuggest implements Serializable {

    private static final long serialVersionUID = 1L;

    private final UUID cardId;
    private final String info;
    private final String source; // name of the suggester
    private final int rank; // 1, 2, 3... - place in the suggester's list

    /**
     * For suggesters: card from the booster and a short reason (example: "rating 87 of 100 by 17Lands FRA")
     */
    public DraftPickSuggest(UUID cardId, String info) {
        this(cardId, info, "", 0);
    }

    private DraftPickSuggest(UUID cardId, String info, String source, int rank) {
        this.cardId = cardId;
        this.info = info == null ? "" : info;
        this.source = source == null ? "" : source;
        this.rank = rank;
    }

    public DraftPickSuggest withSource(String source, int rank) {
        return new DraftPickSuggest(this.cardId, this.info, source, rank);
    }

    public UUID getCardId() {
        return cardId;
    }

    public String getInfo() {
        return info;
    }

    public String getSource() {
        return source;
    }

    public int getRank() {
        return rank;
    }

    @Override
    public String toString() {
        return source + " #" + rank + ": " + info;
    }
}
