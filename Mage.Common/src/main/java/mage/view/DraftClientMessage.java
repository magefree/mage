

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
    private String message;

    public DraftClientMessage(DraftView draftView) {
        this.draftView = draftView;
    }

    public DraftClientMessage(DraftPickView draftPickView) {
        this.draftPickView = draftPickView;
    }

    public DraftClientMessage(DraftView draftView, String message) {
        this.message = message;
        this.draftView = draftView;
    }

    public DraftPickView getDraftPickView() {
        return draftPickView;
    }

    public DraftView getDraftView() {
        return draftView;
    }
}
