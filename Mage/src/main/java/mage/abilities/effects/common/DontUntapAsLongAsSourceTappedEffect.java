

package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.decorator.ConditionalContinuousRuleModifyingEffect;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author LoneFox
 */

public class DontUntapAsLongAsSourceTappedEffect extends ConditionalContinuousRuleModifyingEffect {

    public DontUntapAsLongAsSourceTappedEffect() {
        super(new DontUntapInControllersUntapStepTargetEffect(Duration.Custom), SourceTappedCondition.TAPPED);
        staticText = "It doesn't untap during its controller's untap step for as long as {this} remains tapped.";
    }

    public DontUntapAsLongAsSourceTappedEffect(final DontUntapAsLongAsSourceTappedEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if(event.getType() == GameEvent.EventType.UNTAP && event.getTargetId().equals(source.getSourceId())) {
            effect.discard();
        }
        return super.applies(event, source, game);
    }

    @Override
    public DontUntapAsLongAsSourceTappedEffect copy() {
        return new DontUntapAsLongAsSourceTappedEffect(this);
    }
}
