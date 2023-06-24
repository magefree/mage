package mage.abilities.common;

import mage.MageItem;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeGroupEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * "Whenever one or more {filter} enter the battlefield under {target controller} control,
 *
 * @author Alex-Vasile
 */
public class EntersBattlefieldOneOrMoreTriggeredAbility extends TriggeredAbilityImpl {

    private final FilterPermanent filterPermanent;
    private final TargetController targetController;

    public EntersBattlefieldOneOrMoreTriggeredAbility(Effect effect, FilterPermanent filter, TargetController targetController) {
        super(Zone.BATTLEFIELD, effect);
        this.filterPermanent = filter;
        this.targetController = targetController;
        setTriggerPhrase(generateTriggerPhrase());
    }

    private EntersBattlefieldOneOrMoreTriggeredAbility(final EntersBattlefieldOneOrMoreTriggeredAbility ability) {
        super(ability);
        this.filterPermanent = ability.filterPermanent;
        this.targetController = ability.targetController;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE_GROUP;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeGroupEvent zEvent = (ZoneChangeGroupEvent) event;
        Player controller = game.getPlayer(this.controllerId);
        if (zEvent.getToZone() != Zone.BATTLEFIELD || controller == null) {
            return false;
        }

        switch (this.targetController) {
            case YOU:
                if (!controller.getId().equals(zEvent.getPlayerId())) {
                    return false;
                }
                break;
            case OPPONENT:
                if (!controller.hasOpponent(zEvent.getPlayerId(), game)) {
                    return false;
                }
                break;
        }

        return Stream.concat(
                zEvent.getTokens().stream(),
                zEvent.getCards().stream()
                        .map(MageItem::getId)
                        .map(game::getPermanent)
                        .filter(Objects::nonNull)
        ).anyMatch(permanent -> filterPermanent.match(permanent, this.controllerId, this, game));
    }

    @Override
    public EntersBattlefieldOneOrMoreTriggeredAbility copy() {
        return new EntersBattlefieldOneOrMoreTriggeredAbility(this);
    }

    private String generateTriggerPhrase() {
        StringBuilder sb = new StringBuilder("Whenever one or more " + this.filterPermanent.getMessage() + " enter the battlefield under ");
        switch (targetController) {
            case YOU:
                sb.append("your control, ");
                break;
            case OPPONENT:
                sb.append("an opponent's control, ");
                break;
            default:
                throw new UnsupportedOperationException();
        }
        return sb.toString();
    }
}
