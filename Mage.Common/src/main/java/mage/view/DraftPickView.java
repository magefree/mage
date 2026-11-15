

package mage.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import mage.game.draft.DraftPickSuggest;
import mage.game.draft.DraftPlayerSnapshot;

/**
 * Warning, if you add new lookup fields then see DraftImpl about data sync
 * 
 * @author BetaSteward_at_googlemail.com
 */
public class DraftPickView implements Serializable {
    private static final long serialVersionUID = 1L;

    protected SimpleCardsView booster;
    protected SimpleCardsView picks;
    protected boolean picking;
    protected int timeout;
    protected List<DraftPickSuggest> pickSuggests; // suggested cards from the booster (all sources, best first)

    public DraftPickView(DraftPlayerSnapshot snapshot, int timeout) {
        this.booster = new SimpleCardsView(snapshot.getBooster(), false);
        this.picks = new SimpleCardsView(snapshot.getPicks(), false);
        this.picking = snapshot.isPicking();
        this.timeout = timeout;
        this.pickSuggests = snapshot.isPicking() ? new ArrayList<>(snapshot.getPickSuggests()) : new ArrayList<>();
    }

    public SimpleCardsView getBooster() {
        return booster;
    }

    public SimpleCardsView getPicks() {
        return picks;
    }

    public boolean isPicking() {
        return this.picking;
    }

    public int getTimeout() {
        return timeout;
    }

    public List<DraftPickSuggest> getPickSuggests() {
        return pickSuggests;
    }
}
