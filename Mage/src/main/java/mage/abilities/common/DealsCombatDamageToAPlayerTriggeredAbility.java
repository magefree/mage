package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class DealsCombatDamageToAPlayerTriggeredAbility extends TriggeredAbilityImpl {

    protected final boolean setTargetPointer;
    protected String text;
    protected boolean onlyOpponents;
    private boolean orPlaneswalker = false;

    public DealsCombatDamageToAPlayerTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, false);
    }

    public DealsCombatDamageToAPlayerTriggeredAbility(Effect effect, boolean optional, boolean setTargetPointer) {
        this(effect, optional, setTargetPointer, false);
    }

    public DealsCombatDamageToAPlayerTriggeredAbility(Effect effect, boolean optional, boolean setTargetPointer, boolean onlyOpponents) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.setTargetPointer = setTargetPointer;
        this.onlyOpponents = onlyOpponents;
    }

    public DealsCombatDamageToAPlayerTriggeredAbility(Effect effect, boolean optional, String text, boolean setTargetPointer) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.text = text;
        this.setTargetPointer = setTargetPointer;
    }

    public DealsCombatDamageToAPlayerTriggeredAbility(final DealsCombatDamageToAPlayerTriggeredAbility ability) {
        super(ability);
        this.text = ability.text;
        this.setTargetPointer = ability.setTargetPointer;
        this.onlyOpponents = ability.onlyOpponents;
        this.orPlaneswalker = ability.orPlaneswalker;
    }

    public DealsCombatDamageToAPlayerTriggeredAbility setOrPlaneswalker(boolean orPlaneswalker) {
        this.orPlaneswalker = orPlaneswalker;
        return this;
    }

    @Override
    public DealsCombatDamageToAPlayerTriggeredAbility copy() {
        return new DealsCombatDamageToAPlayerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER
                || (orPlaneswalker && event.getType() == GameEvent.EventType.DAMAGED_PLANESWALKER);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(getSourceId())
                && ((DamagedEvent) event).isCombatDamage()) {
            if (onlyOpponents && event.getType() == GameEvent.EventType.DAMAGED_PLAYER) {
                Player controller = game.getPlayer(getControllerId());
                if (controller == null || !controller.hasOpponent(event.getPlayerId(), game)) {
                    return false;
                }
            }
            if (setTargetPointer) {
                for (Effect effect : this.getAllEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                    effect.setValue("damage", event.getAmount());
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        if (text == null || text.isEmpty()) {
            return "Whenever {this} deals combat damage to "
                    + (onlyOpponents ? "an opponent" : "a player")
                    + (orPlaneswalker ? " or planeswalker" : "")
                    + ", " + super.getRule();
        }
        return text;
    }

}
