package mage.abilities.common;

import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeBatchEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;

/**
 * "Whenever one or more {filter} enter the battlefield under {target controller} control,
 *
 * @author Alex-Vasile, xenohedron
 */
public class EntersBattlefieldOneOrMoreTriggeredAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<ZoneChangeEvent> {

    private final FilterPermanent filter;
    private final TargetController targetController;

    public EntersBattlefieldOneOrMoreTriggeredAbility(Effect effect, FilterPermanent filter, TargetController targetController) {
        super(Zone.BATTLEFIELD, effect);
        this.filter = filter;
        this.targetController = targetController;
        setTriggerPhrase(generateTriggerPhrase());
    }

    protected EntersBattlefieldOneOrMoreTriggeredAbility(final EntersBattlefieldOneOrMoreTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.targetController = ability.targetController;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE_BATCH;
    }

    @Override
    public boolean checkEvent(ZoneChangeEvent event, Game game) {
        if (event.getToZone() != Zone.BATTLEFIELD) {
            return false;
        }
        Permanent permanent = event.getTarget();
        if (permanent == null || !filter.match(permanent, getControllerId(), this, game)) {
            return false;
        }
        switch (targetController) {
            case YOU:
                return isControlledBy(permanent.getControllerId());
            case OPPONENT:
                return game.getOpponents(getControllerId()).contains(permanent.getControllerId());
            default:
                throw new IllegalArgumentException("Unsupported TargetController in EntersBattlefieldOneOrMoreTriggeredAbility: " + targetController);
        }
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return !getFilteredEvents((ZoneChangeBatchEvent) event, game).isEmpty();
    }

    @Override
    public EntersBattlefieldOneOrMoreTriggeredAbility copy() {
        return new EntersBattlefieldOneOrMoreTriggeredAbility(this);
    }

    private String generateTriggerPhrase() {
        StringBuilder sb = new StringBuilder("Whenever one or more " + filter.getMessage());
        switch (targetController) {
            case YOU:
                if (filter.getMessage().contains("you control")) {
                    sb.append(" enter, ");
                } else {
                    sb.append(" you control enter, ");
                }
                break;
            case OPPONENT:
                sb.append(" enter under an opponent's control, ");
                break;
            default:
                throw new IllegalArgumentException("Unsupported TargetController in EntersBattlefieldOneOrMoreTriggeredAbility: " + targetController);
        }
        return sb.toString();
    }
}
