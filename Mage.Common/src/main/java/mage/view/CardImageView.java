package mage.view;

import mage.cards.Card;
import mage.game.command.CommandObject;
import mage.game.permanent.token.Token;
import mage.util.Copyable;

import java.io.Serializable;

/**
 * TODO: delete, no needs?!
 *
 * GUI: card drawing info
 * Can be different from real card name, set code, etc - see morph, copy, etc)
 *
 * @author JayDi85
 */
public class CardImageView implements Serializable, Copyable<CardImageView> {

    private boolean isTokenRepository; // card or token database
    private String cardName; // card or token
    private String setCode; // card or token
    private String cardNumber; // card only, token has "0"
    private Integer imageNumber; // token only
    private boolean isUseVariousArt; // card only

    public CardImageView() {
    }

    public CardImageView(final CardImageView cardImageView) {
        this.isTokenRepository = cardImageView.isTokenRepository;
        this.cardName = cardImageView.cardName;
        this.setCode = cardImageView.setCode;
        this.cardNumber = cardImageView.cardNumber;
        this.imageNumber = cardImageView.imageNumber;
        this.isUseVariousArt = cardImageView.isUseVariousArt;
    }

    public CardImageView fromCard(Card card) {
        this.isTokenRepository = false;
        this.cardName = card.getName();
        this.setCode = card.getExpansionSetCode();
        this.cardNumber = card.getCardNumber();
        this.imageNumber = card.getImageNumber();
        this.isUseVariousArt = card.getUsesVariousArt();
        return this;
    }

    public CardImageView fromToken(Token token) {
        this.isTokenRepository = true;
        this.cardName = token.getName();
        this.setCode = token.getExpansionSetCode();
        this.cardNumber = token.getCardNumber();
        this.imageNumber = token.getImageNumber();
        this.isUseVariousArt = false;
        return this;
    }

    public CardImageView fromCommandObject(CommandObject commandObject) {
        this.isTokenRepository = true;
        this.cardName = commandObject.getName();
        this.setCode = commandObject.getExpansionSetCode();
        this.cardNumber = commandObject.getCardNumber();
        this.imageNumber = commandObject.getImageNumber();
        this.isUseVariousArt = false;
        return this;
    }

    @Override
    public CardImageView copy() {
        return new CardImageView(this);
    }

    public boolean isTokenRepository() {
        return isTokenRepository;
    }

    public String getCardName() {
        return cardName;
    }

    public String getSetCode() {
        return setCode;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public Integer getImageNumber() {
        return imageNumber;
    }

    public boolean isUseVariousArt() {
        return isUseVariousArt;
    }
}
