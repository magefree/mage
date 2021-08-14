package mage.filter.predicate.permanent;

import mage.constants.CardType;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

public class AttachmentAttachedToCardTypePredicate implements Predicate<Permanent> {

    private final CardType cardType;

    public AttachmentAttachedToCardTypePredicate(CardType cardType) {
        this.cardType = cardType;
    }

    @Override
    public boolean apply(Permanent input, Game game) {
        if (input.getAttachedTo() != null) {
            Permanent attachedTo = game.getPermanent(input.getAttachedTo());
            if (attachedTo != null && attachedTo.getCardType(game).contains(cardType)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "AttachmentAttachedToCardType(" + cardType + ')';
    }
}
