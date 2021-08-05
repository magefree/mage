
package mage.util.functions;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.constants.CardType;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */
public class CardTypeCopyApplier extends CopyApplier {

    private final CardType cardType;

    public CardTypeCopyApplier(CardType cardType) {
        this.cardType = cardType;
    }

    @Override
    public boolean apply(Game game, MageObject blueprint, Ability source, UUID copyToObjectId) {
        blueprint.addCardType(cardType);
        return true;
    }
}
