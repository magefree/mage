

package mage.view;

import java.io.Serializable;
import mage.game.draft.DraftPlayer;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class DraftPickView implements Serializable {
    private static final long serialVersionUID = 1L;

    protected SimpleCardsView booster;
    protected SimpleCardsView picks;
    protected boolean picking;
    protected int timeout;

    public DraftPickView(DraftPlayer player, int timeout) {
        this.booster = new SimpleCardsView(player.getBooster(), false);
        this.picks = new SimpleCardsView(player.getDeck().getSideboard(), false);
        this.picking = player.isPicking();
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
