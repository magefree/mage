package mage.abilities.common;

import mage.abilities.effects.Effect;
import mage.constants.PhaseStep;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author LevelX2
 */
public class DealsDamageToOneOrMoreCreaturesTriggeredAbility extends DealsDamageToACreatureTriggeredAbility {

    public DealsDamageToOneOrMoreCreaturesTriggeredAbility(Effect effect, boolean combatOnly, boolean optional, boolean setTargetPointer) {
        super(effect, combatOnly, optional, setTargetPointer);
        setTriggerPhrase("Whenever {this} deals " + (combatOnly ? "combat " : "") + "damage to one or more creatures, ");
    }

    public DealsDamageToOneOrMoreCreaturesTriggeredAbility(DealsDamageToOneOrMoreCreaturesTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            // check that combat damage does only once trigger also if multiple creatures were damaged because they block or were blocked by source
            if (game.getTurnStepType() == PhaseStep.COMBAT_DAMAGE
                    || game.getTurnStepType() == PhaseStep.FIRST_COMBAT_DAMAGE) {
                String stepHash = (String) game.getState().getValue("damageStep" + getOriginalId());
                String newStepHash = game.getTurnStepType().toString() + game.getTurnNum();
                if (!newStepHash.equals(stepHash)) {
                    // this ability did not trigger during this damage step
                    game.getState().setValue("damageStep" + getOriginalId(), game.getTurnStepType().toString() + game.getTurnNum());
                    return true;
                }
            } else {
                game.getState().setValue("damageStep" + getOriginalId(), null);
                return true;
            }
            // TODO: check that if the source did non combat damage to multiple targets at the same time, it may only trigger one time
            // I don't know currently how this can happen for a source creature that this has not already build in
        }
        return false;
    }

    @Override
    public DealsDamageToOneOrMoreCreaturesTriggeredAbility copy() {
        return new DealsDamageToOneOrMoreCreaturesTriggeredAbility(this);
    }
}
