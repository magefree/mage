package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author PurpleCrowbar
 */
public class SurveilTriggeredAbility extends TriggeredAbilityImpl {

    public SurveilTriggeredAbility(Effect effect) {
        this(Zone.BATTLEFIELD, effect);
    }

    public SurveilTriggeredAbility(Zone zone, Effect effect) {
        super(zone, effect);
        setTriggerPhrase("Whenever you surveil, " + (zone == Zone.GRAVEYARD ? "if {this} is in your graveyard, " : ""));
    }

    private SurveilTriggeredAbility(final SurveilTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SurveilTriggeredAbility copy() {
        return new SurveilTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SURVEILED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (isControlledBy(event.getPlayerId())) {
            this.getEffects().setValue("amount", event.getAmount());
            return true;
        }
        return false;
    }
}
