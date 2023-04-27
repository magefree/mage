

package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.delayed.AtTheBeginOfNextCleanupDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent.EventType;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;

/**
 *
 * @author Lonefox
 */
public class SacrificeIfCastAtInstantTimeTriggeredAbility extends TriggeredAbilityImpl {

    public SacrificeIfCastAtInstantTimeTriggeredAbility() {
        super(Zone.STACK, new CreateDelayedTriggeredAbilityEffect(new AtTheBeginOfNextCleanupDelayedTriggeredAbility(new SacrificeSourceEffect())));
    }

    public SacrificeIfCastAtInstantTimeTriggeredAbility(final SacrificeIfCastAtInstantTimeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SacrificeIfCastAtInstantTimeTriggeredAbility copy() {
        return new SacrificeIfCastAtInstantTimeTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        // TODO: The sacrifice should occur only if you cast it using its own ability. If you cast it using some
        // other effect (for instance, if it gained flash from Vedalken Orrery), then it shouldn't be sacrificed.
        // see https://github.com/magefree/mage/issues/9512
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell == null || !spell.getSourceId().equals(getSourceId())) {
            return false;
        }
        // TODO: this is a hack and doesn't handle all other ways a spell could be cast as though it had flash
        if (Boolean.TRUE.equals(game.getState().getValue("PlayFromNotOwnHandZone" + getSourceId()))) {
            return false;
        }
        return !(game.isMainPhase() && game.isActivePlayer(event.getPlayerId()) && game.getStack().size() == 1);
    }

    @Override
    public String getRule() {
        return "If you cast it any time a sorcery couldn't have been cast, the controller of the permanent it becomes sacrifices it at the beginning of the next cleanup step.";
    }
}

