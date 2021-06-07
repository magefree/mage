package mage.abilities.condition.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.CardType;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */
public class TargetHasCardTypeCondition implements Condition {

    private final CardType cardType;

    public TargetHasCardTypeCondition(CardType cardType) {
        this.cardType = cardType;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!source.getTargets().isEmpty()) {
            MageObject mageObject = game.getObject(source.getFirstTarget());
            if (mageObject != null) {
                return mageObject.getCardType(game).contains(cardType);
            }
        }
        return false;
    }
}
