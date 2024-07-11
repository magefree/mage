package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author TheElk801
 * TODO: Implement this
 */
public class ExpendTriggeredAbility extends TriggeredAbilityImpl {

    private final int amount;

    public ExpendTriggeredAbility(Effect effect, int amount) {
        this(effect, amount, false);
    }

    public ExpendTriggeredAbility(Effect effect, int amount, boolean optional) {
        this(Zone.BATTLEFIELD, effect, amount, optional);
    }

    public ExpendTriggeredAbility(Zone zone, Effect effect, int amount, boolean optional) {
        super(zone, effect, optional);
        this.amount = amount;
        setTriggerPhrase("Whenever you expend " + amount + ", ");
    }

    private ExpendTriggeredAbility(final ExpendTriggeredAbility ability) {
        super(ability);
        this.amount = ability.amount;
    }

    @Override
    public ExpendTriggeredAbility copy() {
        return new ExpendTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return false;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return false;
    }
}
