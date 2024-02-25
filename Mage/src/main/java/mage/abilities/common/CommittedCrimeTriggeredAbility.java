package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.cards.Card;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;

import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public class CommittedCrimeTriggeredAbility extends TriggeredAbilityImpl {

    public CommittedCrimeTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
    }

    private CommittedCrimeTriggeredAbility(final CommittedCrimeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CommittedCrimeTriggeredAbility copy() {
        return new CommittedCrimeTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return isControlledBy(getCriminal(event, game));
    }

    public static UUID getCriminal(GameEvent event, Game game) {
        UUID controllerId = game.getControllerId(event.getSourceId());
        if (controllerId == null) {
            return null;
        }
        Set<UUID> opponents = game.getOpponents(controllerId);
        if (opponents.contains(event.getTargetId())) {
            return controllerId;
        }
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null) {
            if (opponents.contains(permanent.getControllerId())) {
                return controllerId;
            }
            return null;
        }
        Spell spell = game.getSpell(event.getTargetId());
        if (spell != null) {
            if (opponents.contains(spell.getControllerId())) {
                return controllerId;
            }
            return null;
        }
        Card card = game.getSpell(event.getTargetId());
        if (card == null
                || game.getState().getZone(event.getTargetId()) == Zone.EXILED
                || !opponents.contains(card.getOwnerId())) {
            return null;
        }
        return controllerId;
    }
}
