package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;

/**
 * @author TheElk801
 */
public class EntersBattlefieldFromGraveyardTriggeredAbility extends TriggeredAbilityImpl {

    public EntersBattlefieldFromGraveyardTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
        setTriggerPhrase("When {this} enters the battlefield from a graveyard, ");
    }

    public EntersBattlefieldFromGraveyardTriggeredAbility(final EntersBattlefieldFromGraveyardTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getTargetId().equals(getSourceId())
                && ((EntersTheBattlefieldEvent) event).getFromZone() == Zone.GRAVEYARD;
    }

    @Override
    public EntersBattlefieldFromGraveyardTriggeredAbility copy() {
        return new EntersBattlefieldFromGraveyardTriggeredAbility(this);
    }
}
