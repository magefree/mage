
package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.cards.Card;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.MilledCardEvent;

import java.util.UUID;

/**
 * @author Susucr
 */
public class MillTriggeredAbility extends TriggeredAbilityImpl {

    private final TargetController targetController;
    private final FilterCard filter;

    public MillTriggeredAbility(Zone zone, Effect effect, TargetController targetController, FilterCard filter, boolean optional) {
        super(zone, effect, optional);
        this.targetController = targetController;
        this.filter = filter;
        setTriggerPhrase(generateTriggerPhrase());
    }

    private MillTriggeredAbility(final MillTriggeredAbility ability) {
        super(ability);
        this.targetController = ability.targetController;
        this.filter = ability.filter;
    }

    @Override
    public MillTriggeredAbility copy() {
        return new MillTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.MILLED_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        UUID playerId = event.getPlayerId();
        switch (targetController) {
            case ANY:
                // no check.
                break;
            case OPPONENT:
                if (!game.getOpponents(getControllerId()).contains(playerId)) {
                    return false;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong code usage. targetController not yet supported: " + targetController);
        }
        Card card = ((MilledCardEvent) event).getCard();
        return card != null && filter.match(card, getControllerId(), this, game);
    }

    private String generateTriggerPhrase() {
        String text = "Whenever ";
        switch (targetController) {
            case ANY:
                text += "a player ";
                break;
            case OPPONENT:
                text += "an opponent ";
                break;
            default:
                throw new IllegalArgumentException("Wrong code usage. targetController not yet supported: " + targetController);
        }
        return text + "mills a " + filter.getMessage() + ", ";
    }
}
