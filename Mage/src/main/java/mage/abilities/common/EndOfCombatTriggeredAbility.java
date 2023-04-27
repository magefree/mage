package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author LevelX2
 */
public class EndOfCombatTriggeredAbility extends TriggeredAbilityImpl {

    private final String rule;

    public EndOfCombatTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, null);
    }

    public EndOfCombatTriggeredAbility(Effect effect, boolean optional, String rule) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.rule = rule;
        setTriggerPhrase("At end of combat, ");
    }

    public EndOfCombatTriggeredAbility(final EndOfCombatTriggeredAbility ability) {
        super(ability);
        this.rule = ability.rule;
    }

    @Override
    public EndOfCombatTriggeredAbility copy() {
        return new EndOfCombatTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.END_COMBAT_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return true;
    }

    @Override
    public String getRule() {
        if (rule != null) {
            return rule;
        }
        return super.getRule();
    }
}
