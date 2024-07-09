package mage.abilities.common;

import mage.abilities.effects.Effect;
import mage.constants.AbilityWord;
import mage.constants.SetTargetPointer;
import mage.constants.TargetController;
import mage.filter.FilterStackObject;

/**
 * @author xenohedron, TheElk801
 */
public class ValiantTriggeredAbility extends BecomesTargetSourceFirstTimeTriggeredAbility {

    private static final FilterStackObject filter = new FilterStackObject("a spell or ability you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public ValiantTriggeredAbility(Effect effect) {
        this(effect, false);
        this.setAbilityWord(AbilityWord.VALIANT);
    }

    public ValiantTriggeredAbility(Effect effect, boolean optional) {
        super(effect, filter, SetTargetPointer.NONE, false);
    }

    private ValiantTriggeredAbility(final ValiantTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ValiantTriggeredAbility copy() {
        return new ValiantTriggeredAbility(this);
    }
}
