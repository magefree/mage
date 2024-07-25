package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeBatchEvent;
import mage.players.Player;

import java.util.UUID;

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
        return event.getType() == GameEvent.EventType.ZONE_CHANGE_BATCH;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {

        Player controller = game.getPlayer(this.controllerId);
        if (controller == null) {
            return false;
        }

        ZoneChangeBatchEvent zEvent = (ZoneChangeBatchEvent) event;
        return zEvent.getEvents().stream()
                .filter(z -> z.getToZone() == Zone.BATTLEFIELD)
                .filter(z -> filterPermanent.match(z.getTarget(), this.controllerId, this, game))
                .anyMatch(z -> {
                    UUID enteringPermanentControllerID = z.getTarget().getControllerId();
                    switch (this.targetController) {
                        case YOU:
                            return enteringPermanentControllerID.equals(this.controllerId);
                        case OPPONENT:
                            return controller.hasOpponent(enteringPermanentControllerID, game);
                        default:
                            throw new IllegalArgumentException("Unsupported target: " + this.targetController);
                    }
                });
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
