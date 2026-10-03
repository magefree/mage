

package mage.view;

import java.io.Serializable;
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

    public DraftPickView(DraftPlayerSnapshot snapshot, int timeout) {
        this.booster = new SimpleCardsView(snapshot.getBooster(), false);
        this.picks = new SimpleCardsView(snapshot.getPicks(), false);
        this.picking = snapshot.isPicking();
        this.timeout = timeout;
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
}
