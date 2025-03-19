package mage.abilities.common;

import mage.abilities.effects.Effect;
import mage.constants.AbilityWord;
import mage.constants.TargetController;
import mage.constants.Zone;

/**
 * @author TheElk801
 */
public class FlurryAbility extends CastSecondSpellTriggeredAbility {

    public FlurryAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, TargetController.YOU, false);
        this.setAbilityWord(AbilityWord.FLURRY);
    }

    private FlurryAbility(final FlurryAbility ability) {
        super(ability);
    }

    @Override
    public FlurryAbility copy() {
        return new FlurryAbility(this);
    }
}
