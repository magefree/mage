package mage.abilities.hint;

import mage.abilities.Ability;
import mage.game.Game;

import java.io.Serializable;

/**
 * @author JayDi85
 */
public interface Hint extends Serializable {

    // It's a constant hint for cards/permanents (e.g. visible all the time)
    //
    // Another solutions are possible:
    // 1. Add direct card hint to gained ability (ability create code);
    // 2. Add constant text: InfoEffect.addInfoToPermanent
    // 3. Add dynamic text: InfoEffect.addCardHintToPermanent
    // 4. Add direct text to the rules: permanent.addInfo, card.addInfo

    // TODO: add card hint for ActivateIfConditionActivatedAbility
    //  * remove my turn condition from cards construction
    //  * test condition texts (add alternative texts to conditions like getHintText?)
    //  * add auto-capitalize of first symbol
    //  * add support of compound conditions
    //  see https://github.com/magefree/mage/issues/5497

    String getText(Game game, Ability ability);

    Hint copy();
}