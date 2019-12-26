package mage.view;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class SimpleCardView implements Serializable, SelectableObjectView {
    @Expose
    protected UUID id;
    protected String expansionSetCode;
    protected String tokenSetCode;
    protected String tokenDescriptor;
    protected String cardNumber;
    protected boolean usesVariousArt;
    protected boolean gameObject;

    protected boolean isPlayable;
    protected boolean isChoosable;
    protected boolean isSelected;
    protected int playableAmount; // playable abilities count on object

    public SimpleCardView(final SimpleCardView view) {
        this.id = view.id;
        this.expansionSetCode = view.expansionSetCode;
        this.tokenSetCode = view.tokenSetCode;
        this.tokenDescriptor = view.tokenDescriptor;
        this.cardNumber = view.cardNumber;
        this.usesVariousArt = view.usesVariousArt;
        this.gameObject = view.gameObject;

        this.isPlayable = view.isPlayable;
        this.isChoosable = view.isChoosable;
        this.isSelected = view.isSelected;
        this.playableAmount = view.playableAmount;
    }

    public SimpleCardView(UUID id, String expansionSetCode, String cardNumber, boolean usesVariousArt, String tokenSetCode, String tokenDescriptor) {
        this(id, expansionSetCode, cardNumber, usesVariousArt, tokenSetCode, false, tokenDescriptor);
    }

    public SimpleCardView(UUID id, String expansionSetCode, String cardNumber, boolean usesVariousArt, String tokenSetCode, boolean isGameObject, String tokenDescriptor) {
        this.id = id;
        this.expansionSetCode = expansionSetCode;
        this.tokenDescriptor = tokenDescriptor;
        this.cardNumber = cardNumber;
        this.usesVariousArt = usesVariousArt;
        this.tokenSetCode = tokenSetCode;
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

    public String getTokenSetCode() {
        return tokenSetCode;
    }

    public String getTokenDescriptor() {
        return tokenDescriptor;
    }

    public boolean isGameObject() {
        return gameObject;
    }

    @Override
    public boolean isPlayable() {
        return isPlayable;
    }

    @Override
    public void setPlayable(boolean isPlayable) {
        this.isPlayable = isPlayable;
    }

    @Override
    public void setPlayableAmount(int playableAmount) {
        this.playableAmount = playableAmount;
    }

    @Override
    public int getPlayableAmount() {
        return playableAmount;
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
