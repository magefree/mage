

package mage.abilities.abilityword;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CommanderInPlayCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 * @author emerald000
 */

public class LieutenantAbility extends SimpleStaticAbility {

    public LieutenantAbility(ContinuousEffect effect) {
        super(Zone.BATTLEFIELD, new ConditionalContinuousEffect(new BoostSourceEffect(2, 2, Duration.WhileOnBattlefield), CommanderInPlayCondition.instance, "<i>Lieutenant</i> &mdash; As long as you control your commander, {this} gets +2/+2"));
        this.addEffect(new ConditionalContinuousEffect(effect, CommanderInPlayCondition.instance, effect.getText(null)));
    }

    public LieutenantAbility(Effects effects) {
        super(Zone.BATTLEFIELD, new ConditionalContinuousEffect(new BoostSourceEffect(2, 2, Duration.WhileOnBattlefield), CommanderInPlayCondition.instance, "<i>Lieutenant</i> &mdash; As long as you control your commander, {this} gets +2/+2"));
        for (Effect effect : effects) {
            this.addEffect(new ConditionalContinuousEffect((ContinuousEffect) effect, CommanderInPlayCondition.instance, effect.getText(null)));
        }
    }

    protected LieutenantAbility(final LieutenantAbility ability) {
        super(ability);
    }

    @Override
    public LieutenantAbility copy() {
        return new LieutenantAbility(this);
    }
}