

package mage.view;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class SimpleCardView implements Serializable {
    @Expose
    protected UUID id;
    protected String expansionSetCode;
    protected String tokenSetCode;
    protected String tokenDescriptor;
    protected String cardNumber;
    protected boolean usesVariousArt;
    protected boolean gameObject;

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
}
