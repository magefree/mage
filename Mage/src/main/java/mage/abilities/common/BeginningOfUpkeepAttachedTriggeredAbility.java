package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author TheElk801
 */
public class BeginningOfUpkeepAttachedTriggeredAbility extends TriggeredAbilityImpl {

    public BeginningOfUpkeepAttachedTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public BeginningOfUpkeepAttachedTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
    }

    private BeginningOfUpkeepAttachedTriggeredAbility(final BeginningOfUpkeepAttachedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BeginningOfUpkeepAttachedTriggeredAbility copy() {
        return new BeginningOfUpkeepAttachedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = getSourcePermanentOrLKI(game);
        if (enchantment == null || !game.isActivePlayer(enchantment.getAttachedTo())) {
            return false;
        }
        this.getEffects().setTargetPointer(new FixedTarget(enchantment.getAttachedTo()));
        return true;
    }

    @Override
    public String getRule() {
        return "At the beginning of enchanted player's upkeep, " + super.getRule();
    }
}
