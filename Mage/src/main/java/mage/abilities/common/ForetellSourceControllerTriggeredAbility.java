package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.cards.Card;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 * @author jeffwadsworth
 */
public class ForetellSourceControllerTriggeredAbility extends TriggeredAbilityImpl {

    public ForetellSourceControllerTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
        setTriggerPhrase("Whenever you foretell a card, ");
    }

    public ForetellSourceControllerTriggeredAbility(final ForetellSourceControllerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.FORETELL;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Card card = game.getCard(event.getTargetId());
        Player player = game.getPlayer(event.getPlayerId());
        return (card != null
                && player != null
                && isControlledBy(player.getId()));
    }

    @Override
    public ForetellSourceControllerTriggeredAbility copy() {
        return new ForetellSourceControllerTriggeredAbility(this);
    }

}
