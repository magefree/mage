package mage.abilities;

import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.Iterator;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class DelayedTriggeredAbilities extends AbilitiesImpl<DelayedTriggeredAbility> {

    public DelayedTriggeredAbilities() {
    }

    protected DelayedTriggeredAbilities(final DelayedTriggeredAbilities abilities) {
        super(abilities);
    }

    @Override
    public DelayedTriggeredAbilities copy() {
        return new DelayedTriggeredAbilities(this);
    }

    public void checkTriggers(GameEvent event, Game game) {
        // TODO: add same integrity checks as TriggeredAbilities?!
        for (Iterator<DelayedTriggeredAbility> it = this.iterator(); it.hasNext(); ) {
            DelayedTriggeredAbility ability = it.next();
            if (ability.getDuration() == Duration.Custom) {
                if (ability.isInactive(game)) {
                    it.remove();
                    continue;
                }
            }
            if (!ability.checkEventType(event, game)) {
                continue;
            }
            if (ability.checkTrigger(event, game)) {
                ability.trigger(game, ability.controllerId, event);
                if (ability.getTriggerOnlyOnce()) {
                    it.remove();
                }
            }
        }
    }

    public void removeEndOfTurnAbilities(Game game) {
        this.removeIf(ability -> ability.getDuration() == Duration.EndOfTurn); // TODO: add Duration.EndOfYourTurn like effects
    }

    public void removeStartOfNewTurn(Game game) {
        this.removeIf(ability -> ability.getDuration() == Duration.UntilYourNextTurn
                && game.getActivePlayerId().equals(ability.getControllerId())
        );
    }

    public void removeEndOfCombatAbilities() {
        this.removeIf(ability -> ability.getDuration() == Duration.EndOfCombat);
    }
}

