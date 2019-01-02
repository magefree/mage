

package mage.view;

import java.io.Serializable;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class DraftClientMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    private DraftView draftView;
    private DraftPickView draftPickView;

    public DraftClientMessage(DraftView draftView, DraftPickView draftPickView) {
        this.draftView = draftView;
        this.draftPickView = draftPickView;
    }

    public DraftPickView getDraftPickView() {
        return draftPickView;
    }

    public DraftView getDraftView() {
        return draftView;
    }
}
