
package mage.abilities.condition.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.cards.Card;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */

public enum FaceDownSourceCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject mageObject = game.getObject(source);
        if (mageObject != null) {
            if (mageObject instanceof Permanent) {
                return ((Permanent)mageObject).isFaceDown(game);
            }
            if (mageObject instanceof Card) {
                return ((Card)mageObject).isFaceDown(game);
            }
        }
        return false;
    }
}
