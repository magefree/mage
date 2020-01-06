
package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

public class BushidoAbility extends TriggeredAbilityImpl {

    private DynamicValue value;
    private String rulesText = null;

    public BushidoAbility(int value) {
        this(StaticValue.get(value));
        rulesText = "Bushido " + value + getReminder(Integer.toString(value));
    }

    public BushidoAbility(DynamicValue value) {
        super(Zone.BATTLEFIELD, new BoostSourceEffect(value, value, Duration.EndOfTurn, true), false);
        if (!(value instanceof StaticValue)) {
            rulesText = "{this} has bushido X, where X is " + value.getMessage() + getReminder(value.toString());
        }
        this.value = value;
    }

    static String getReminder(String xValue) {
        return "  <i>(Whenever this creature blocks or becomes blocked, it gets +" + xValue + "/+" + xValue + " until end of turn.)</i>";
    }

    public BushidoAbility(final BushidoAbility ability) {
        super(ability);
        this.value = ability.value;
        this.rulesText = ability.rulesText;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARE_BLOCKERS_STEP;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent source = game.getPermanent(getSourceId());
        if (source != null) {
            if (source.isBlocked(game)) {
                return true;
            }
            for (CombatGroup group : game.getCombat().getGroups()) {
                if (group.getBlockers().contains(getSourceId())) {
                    return true;
                }
            }
        }
        return false;
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
        return rulesText;
    }
}
