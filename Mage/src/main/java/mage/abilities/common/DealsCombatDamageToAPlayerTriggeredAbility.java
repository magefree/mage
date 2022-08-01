package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
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
                || event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getSourceId().equals(getSourceId())
                || !((DamagedEvent) event).isCombatDamage()) {
            return false;
        }
        switch (event.getType()) {
            case DAMAGED_PLAYER:
                if (onlyOpponents && !game.getOpponents(getControllerId()).contains(event.getTargetId())) {
                    return false;
                }
                break;
            case DAMAGED_PERMANENT:
                Permanent permanent = game.getPermanent(event.getTargetId());
                if (permanent == null
                        || !permanent.isPlaneswalker(game)
                        || !orPlaneswalker) {
                    return false;
                }
        }
        getAllEffects().setValue("damage", event.getAmount());
        if (setTargetPointer) {
            getAllEffects().setTargetPointer(new FixedTarget(event.getPlayerId()));
        }
        return true;
    }

    @Override
    public String getRule() {
        if (text == null || text.isEmpty()) {
            return super.getRule();
        }
        return text;
    }

    // TODO: This class needs refactoring to specify onlyOppontns and OrPLaneswalkers in constructor
    @Override
    public String getTriggerPhrase() {
        return "Whenever {this} deals combat damage to "
                + (onlyOpponents ? "an opponent" : "a player")
                + (orPlaneswalker ? " or planeswalker" : "")
                + ", ";
    }
}
