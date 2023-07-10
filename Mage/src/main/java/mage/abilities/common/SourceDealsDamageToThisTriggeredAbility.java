package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 * @author xenohedron
 */
public class SourceDealsDamageToThisTriggeredAbility extends TriggeredAbilityImpl {

    public SourceDealsDamageToThisTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public SourceDealsDamageToThisTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        setTriggerPhrase("Whenever a source deals damage to {this}, ");
    }

    public SourceDealsDamageToThisTriggeredAbility(final SourceDealsDamageToThisTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SourceDealsDamageToThisTriggeredAbility copy() {
        return new SourceDealsDamageToThisTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getTargetId().equals(this.sourceId)) {
            return false;
        }
        int damageAmount = event.getAmount();
        if (damageAmount < 1) {
            return false;
        }
        this.getEffects().setValue("damage", damageAmount);
        Player sourceController = game.getPlayer(game.getControllerId(event.getSourceId()));
        if (sourceController != null) {
            getEffects().setTargetPointer(new FixedTarget(sourceController.getId()));
        }
        return true;
    }
}
