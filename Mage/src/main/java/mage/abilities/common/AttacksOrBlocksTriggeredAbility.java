package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

public class AttacksOrBlocksTriggeredAbility extends TriggeredAbilityImpl {

    protected String startText = "Whenever";

    public AttacksOrBlocksTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        if (effect instanceof CreateDelayedTriggeredAbilityEffect) {
            startText = "When";
        }
    }

    public AttacksOrBlocksTriggeredAbility(final AttacksOrBlocksTriggeredAbility ability) {
        super(ability);
        this.startText = ability.startText;
    }

    @Override
    public AttacksOrBlocksTriggeredAbility copy() {
        return new AttacksOrBlocksTriggeredAbility(this);
    }

    @Override
    public String getTriggerPhrase() {
        return startText + " {this} attacks or blocks, " ;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED || event.getType() == GameEvent.EventType.BLOCKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getSourceId().equals(this.getSourceId());
    }
}
