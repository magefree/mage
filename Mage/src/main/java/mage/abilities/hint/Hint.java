package mage.abilities.hint;

import mage.abilities.Ability;
import mage.game.Game;

import java.io.Serializable;

/**
 * @author JayDi85
 */
public interface Hint extends Serializable {

    // TODO: add card hint for ActivateIfConditionActivatedAbility
    //  * remove my turn condition from cards construction
    //  * test condition texts (add alternative texts to donditions like getHintText?)
    //  * add auto-capitalize of first symbol
    //  * add support of compound conditions
    //  see https://github.com/magefree/mage/issues/5497

    String getText(Game game, Ability ability);

    Hint copy();
}