package mage.abilities.keyword;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.AddCardTypeSourceEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 * @author TheElk801
 */
public class LivingMetalAbility extends SimpleStaticAbility {

    public LivingMetalAbility() {
        super(new ConditionalContinuousEffect(new AddCardTypeSourceEffect(
                Duration.WhileOnBattlefield, CardType.ARTIFACT, CardType.CREATURE
        ), MyTurnCondition.instance, ""));
        this.addHint(MyTurnHint.instance);
    }

    public LivingMetalAbility(final LivingMetalAbility ability) {
        super(ability);
    }

    @Override
    public LivingMetalAbility copy() {
        return new LivingMetalAbility(this);
    }

    @Override
    public String getRule() {
        return "Living metal <i>(As long as it's your turn, this Vehicle is also a creature.)</i>";
    }
}
