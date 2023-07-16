package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class SacrificeAllTriggeredAbility extends TriggeredAbilityImpl {

    private final FilterPermanent filter;
    private final TargetController sacrificingPlayer;

    public SacrificeAllTriggeredAbility(Effect effect, FilterPermanent filter, TargetController sacrificingPlayer, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.filter = filter;
        this.sacrificingPlayer = sacrificingPlayer;
        setTriggerPhrase(generateTriggerPhrase());
    }

    public SacrificeAllTriggeredAbility(final SacrificeAllTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.sacrificingPlayer = ability.sacrificingPlayer;
    }

    @Override
    public SacrificeAllTriggeredAbility copy() {
        return new SacrificeAllTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SACRIFICED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        switch (sacrificingPlayer) {
            case YOU:
                if (!event.getPlayerId().equals(getControllerId())) {
                    return false;
                }
                break;
            case OPPONENT:
                Player controller = game.getPlayer(getControllerId());
                if (controller == null || !controller.hasOpponent(event.getPlayerId(), game)) {
                    return false;
                }
                break;
            case ANY:
                break;
            default:
                return false;
        }
        Permanent sacrificedPermanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        return sacrificedPermanent != null && filter.match(sacrificedPermanent, getControllerId(), this, game);
    }

    private String generateTriggerPhrase() {
        String targetControllerText;
        switch (sacrificingPlayer) {
            case YOU:
                targetControllerText = "you sacrifice ";
                break;
            case OPPONENT:
                targetControllerText = "an opponent sacrifices ";
                break;
            default:
                targetControllerText = "a player sacrifices ";
                break;
        }
        return "Whenever " + targetControllerText + filter.getMessage() + ", ";
    }
}
