
package mage.abilities.effects.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TurnPhase;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.turn.TurnMod;

/**
 *
 * @author LevelX2
 */
public class AddCombatAndMainPhaseEffect extends OneShotEffect {

    public AddCombatAndMainPhaseEffect() {
        super(Outcome.Benefit);
        staticText = "After this main phase, there is an additional combat phase followed by an additional main phase";
    }

    public AddCombatAndMainPhaseEffect(final AddCombatAndMainPhaseEffect effect) {
        super(effect);
    }

    @Override
    public AddCombatAndMainPhaseEffect copy() {
        return new AddCombatAndMainPhaseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // 15.07.2006 If it's somehow not a main phase when Fury of the Horde resolves, all it does is untap all creatures that attacked that turn. No new phases are created.
        if (game.getTurnPhaseType() == TurnPhase.PRECOMBAT_MAIN
                || game.getTurnPhaseType() == TurnPhase.POSTCOMBAT_MAIN) {
            // we can't add two turn modes at once, will add additional post combat on delayed trigger resolution
            TurnMod combat = new TurnMod(source.getControllerId()).withExtraPhase(TurnPhase.COMBAT, TurnPhase.POSTCOMBAT_MAIN);
            game.getState().getTurnMods().add(combat);
            DelayedAddMainPhaseAbility delayedTriggeredAbility = new DelayedAddMainPhaseAbility();
            delayedTriggeredAbility.setConnectedTurnMod(combat.getId());
            game.addDelayedTriggeredAbility(delayedTriggeredAbility, source);
            return true;
        }
        return false;
    }

}

class DelayedAddMainPhaseAbility extends DelayedTriggeredAbility {

    private UUID connectedTurnMod;
    private boolean enabled;

    public DelayedAddMainPhaseAbility() {
        super(null, Duration.EndOfTurn);
        this.usesStack = false; // don't show this to the user
    }

    public DelayedAddMainPhaseAbility(DelayedAddMainPhaseAbility ability) {
        super(ability);
        this.connectedTurnMod = ability.connectedTurnMod;
        this.enabled = ability.enabled;
    }

    @Override
    public DelayedAddMainPhaseAbility copy() {
        return new DelayedAddMainPhaseAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PHASE_CHANGED || event.getType() == GameEvent.EventType.COMBAT_PHASE_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.PHASE_CHANGED && this.connectedTurnMod.equals(event.getSourceId())) {
            enabled = true;
        }
        if (event.getType() == GameEvent.EventType.COMBAT_PHASE_PRE && enabled) {
            // add additional post combat main phase after that - after phase == null because add it after this combat
            game.getState().getTurnMods().add(new TurnMod(getControllerId()).withExtraPhase(TurnPhase.POSTCOMBAT_MAIN));
            enabled = false;
        }
        return false;
    }

    public void setConnectedTurnMod(UUID connectedTurnMod) {
        this.connectedTurnMod = connectedTurnMod;
    }

    @Override
    public String getRule() {
        return "add additional post combat main phase";
    }
}
