package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.keyword.ForetellAbility;
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
    }

    public ForetellSourceControllerTriggeredAbility(final ForetellSourceControllerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAKEN_SPECIAL_ACTION;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Card card = game.getCard(event.getSourceId());
        Player player = game.getPlayer(event.getPlayerId());
        if (card == null || player == null) {
            return false;
        }

        if (!isControlledBy(player.getId())) {
            return false;
        }

        return card.getAbilities(game).containsClass(ForetellAbility.class);
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever you foretell a card, " ;
    }

    @Override
    public ForetellSourceControllerTriggeredAbility copy() {
        return new ForetellSourceControllerTriggeredAbility(this);
    }

}
