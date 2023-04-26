package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.DamagedPlayerBatchEvent;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

/**
 * A triggered ability for whenever one or more creatures deal combat damage to
 * you. Has an optional component for setting the target pointer to the opponent
 * whose creatures dealt combat damage to you.
 * 
 * @author alexander-novo
 */
public class CombatDamageDealtToYouTriggeredAbility extends TriggeredAbilityImpl {

    // Whether or not the ability should set a target targetting the opponent who
    // controls the creatures who dealt damage to you
    private final boolean setTarget;

    /**
     * @param effect The effect that should happen when the ability resolves
     */
    public CombatDamageDealtToYouTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    /**
     * @param effect    The effect that should happen when the ability resolves
     * @param setTarget Whether or not the ability should set a target targetting
     *                  the opponent who controls the creatures who dealt damage to
     *                  you
     */
    public CombatDamageDealtToYouTriggeredAbility(Effect effect, boolean setTarget) {
        this(Zone.BATTLEFIELD, effect, setTarget, false);
    }

    /**
     * @param zone      Which zone the ability shoudl take effect in
     * @param effect    The effect that should happen when the ability resolves
     * @param setTarget Whether or not the ability should set a target targetting
     *                  the opponent who controls the creatures who dealt damage to
     *                  you
     * @param optional  Whether or not the ability is optional
     */
    public CombatDamageDealtToYouTriggeredAbility(Zone zone, Effect effect, boolean setTarget,
            boolean optional) {
        super(zone, effect, optional);

        this.setTarget = setTarget;

        setTriggerPhrase(generateTriggerPhrase());
    }

    private CombatDamageDealtToYouTriggeredAbility(final CombatDamageDealtToYouTriggeredAbility ability) {
        super(ability);

        this.setTarget = ability.setTarget;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER_BATCH;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerBatchEvent damageEvent = (DamagedPlayerBatchEvent) event;
        boolean check = damageEvent.getEvents()
                .stream()
                .anyMatch(c -> c.isCombatDamage() && c.getPlayerId() == this.controllerId);

        if (check) {
            if (this.setTarget) {
                this.getEffects().setTargetPointer(
                        new FixedTarget(game.getPermanent(damageEvent.getSourceId()).getControllerId()));
            }

            return true;
        }
        return false;
    }

    private String generateTriggerPhrase() {
        if (setTarget) {
            return "Whenever one or more creatures an opponent controls deal combat damage to you, ";
        } else {
            return "Whenever one or more creatures deal combat damage to you, ";
        }
    }

    @Override
    public CombatDamageDealtToYouTriggeredAbility copy() {
        return new CombatDamageDealtToYouTriggeredAbility(this);
    }
}