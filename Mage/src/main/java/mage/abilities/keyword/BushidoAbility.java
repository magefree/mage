package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.common.BlocksOrBlockedSourceTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;

/**
 * @author awjackson
 */
public class BushidoAbility extends BlocksOrBlockedSourceTriggeredAbility {

    private final DynamicValue value;
    private final String rule;

    public BushidoAbility(int value) {
        this(StaticValue.get(value));
    }

    public BushidoAbility(DynamicValue value) {
        super(new BoostSourceEffect(value, value, Duration.EndOfTurn, true));
        this.value = value;
        rule = (
                value instanceof StaticValue ?
                "Bushido " + value.toString() :
                "{this} has bushido X, where X is " + value.getMessage()
        ) + getReminder(value.toString());
    }

    static String getReminder(String xValue) {
        return "  <i>(Whenever this creature blocks or becomes blocked, it gets +" + xValue + "/+" + xValue + " until end of turn.)</i>";
    }

    public BushidoAbility(final BushidoAbility ability) {
        super(ability);
        this.value = ability.value;
        this.rule = ability.rule;
    }

    @Override
    public BushidoAbility copy() {
        return new BushidoAbility(this);
    }

    public int getValue(Ability source, Game game, Effect effect) {
        return value.calculate(game, source, effect);
    }

    @Override
    public String getRule() {
        return rule;
    }
}
