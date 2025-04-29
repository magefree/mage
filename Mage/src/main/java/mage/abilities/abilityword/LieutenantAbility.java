package mage.abilities.abilityword;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.ControlYourCommanderCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.AbilityWord;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 * @author emerald000
 */

public class LieutenantAbility extends SimpleStaticAbility {

    public LieutenantAbility(ContinuousEffect effect, String text) {
        super(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new BoostSourceEffect(2, 2, Duration.WhileOnBattlefield),
                ControlYourCommanderCondition.instance,
                "as long as you control your commander, {this} gets +2/+2"
        ));
        this.setAbilityWord(AbilityWord.LIEUTENANT);
        this.addLieutenantEffect(effect, text);
    }

    public LieutenantAbility addLieutenantEffect(ContinuousEffect effect, String text) {
        this.addEffect(new ConditionalContinuousEffect(effect, ControlYourCommanderCondition.instance, text));
        return this;
    }

    protected LieutenantAbility(final LieutenantAbility ability) {
        super(ability);
    }

    @Override
    public LieutenantAbility copy() {
        return new LieutenantAbility(this);
    }
}
