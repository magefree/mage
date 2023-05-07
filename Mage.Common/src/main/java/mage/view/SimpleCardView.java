package mage.view;

import com.google.gson.annotations.Expose;
import mage.players.PlayableObjectStats;

import java.io.Serializable;
import java.util.UUID;

/**
 * GUI: basic class for all card/object related views
 *
 * @author BetaSteward_at_googlemail.com
 */
public class SimpleCardView implements Serializable, SelectableObjectView {

    @Expose
    protected UUID id;
    protected String expansionSetCode;
    protected String cardNumber;
    protected boolean usesVariousArt;
    protected boolean gameObject;

    protected boolean isChoosable;
    protected boolean isSelected;
    protected PlayableObjectStats playableStats = new PlayableObjectStats();

    public SimpleCardView(final SimpleCardView view) {
        this.id = view.id;
        this.expansionSetCode = view.expansionSetCode;
        this.cardNumber = view.cardNumber;
        this.usesVariousArt = view.usesVariousArt;
        this.gameObject = view.gameObject;
        this.isChoosable = view.isChoosable;
        this.isSelected = view.isSelected;
        this.playableStats = view.playableStats.copy();
    }

    public SimpleCardView(UUID id, String expansionSetCode, String cardNumber, boolean usesVariousArt) {
        this(id, expansionSetCode, cardNumber, usesVariousArt, false);
    }

    public SimpleCardView(UUID id, String expansionSetCode, String cardNumber, boolean usesVariousArt, boolean isGameObject) {
        this.id = id;
        this.expansionSetCode = expansionSetCode;
        this.cardNumber = cardNumber;
        this.usesVariousArt = usesVariousArt;
        this.gameObject = isGameObject;
    }

    public UUID getId() {
        return id;
    }

    public String getExpansionSetCode() {
        return expansionSetCode;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public boolean getUsesVariousArt() {
        return usesVariousArt;
    }

    public boolean isGameObject() {
        return gameObject;
    }

    @Override
    public boolean isPlayable() {
        return this.playableStats.getPlayableAmount() > 0;
    }

    @Override
    public void setPlayableStats(PlayableObjectStats playableStats) {
        this.playableStats = playableStats;
    }

    @Override
    public PlayableObjectStats getPlayableStats() {
        return this.playableStats;
    }

    @Override
    public boolean isChoosable() {
        return isChoosable;
    }

    @Override
    public void setChoosable(boolean isChoosable) {
        this.isChoosable = isChoosable;
    }

    @Override
    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}
