
package mage.util.functions;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class CardTypeApplier extends ApplyToPermanent {

    private final CardType cardType;

    public CardTypeApplier(CardType cardType) {
        this.cardType = cardType;
    }

    @Override
    public boolean apply(Game game, Permanent permanent, Ability source, UUID copyToObjectId) {
        permanent.addCardType(cardType);
        return true;
    }

    @Override
    public boolean apply(Game game, MageObject mageObject, Ability source, UUID copyToObjectId) {
        mageObject.addCardType(cardType);
        return true;
    }
}
