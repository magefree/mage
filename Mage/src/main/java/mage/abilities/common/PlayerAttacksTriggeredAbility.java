package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

/**
 * A triggered ability for whenever a player attacks. Has an optional component
 * for setting the target pointer on effects to that attacking player.
 * 
 * @author alexander-novo
 */
public class PlayerAttacksTriggeredAbility extends TriggeredAbilityImpl {

    // Whether or not the ability should set a target targetting the player who
    // attacked
    private final boolean setTarget;

    /**
     * @param effect The effect that should happen when the ability resolves
     */
    public PlayerAttacksTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    /**
     * @param effect    The effect that should happen when the ability resolves
     * @param setTarget Whether or not the ability should set a target targetting
     *                  the player who attacked
     */
    public PlayerAttacksTriggeredAbility(Effect effect, boolean setTarget) {
        this(Zone.BATTLEFIELD, effect, setTarget, false);
    }

    /**
     * @param zone      Which zone the ability shoudl take effect in
     * @param effect    The effect that should happen when the ability resolves
     * @param setTarget Whether or not the ability should set a target targetting
     *                  the player who attacked
     * @param optional  Whether or not the ability is optional
     */
    public PlayerAttacksTriggeredAbility(Zone zone, Effect effect, boolean setTarget,
            boolean optional) {
        super(zone, effect, optional);

        this.setTarget = setTarget;

        setTriggerPhrase(generateTriggerPhrase());
    }

    private PlayerAttacksTriggeredAbility(final PlayerAttacksTriggeredAbility ability) {
        super(ability);

        this.setTarget = ability.setTarget;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!game.getCombat().getAttackers().isEmpty()) {
            if (this.setTarget) {
                this.getEffects().setTargetPointer(
                        new FixedTarget(event.getPlayerId()));
            }
            return true;
        }

        return false;
    }

    private String generateTriggerPhrase() {
        return "Whenever a player attacks, ";
    }

    @Override
    public PlayerAttacksTriggeredAbility copy() {
        return new PlayerAttacksTriggeredAbility(this);
    }
}