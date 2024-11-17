package mage.abilities.common;

import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.SacrificedPermanentBatchEvent;
import mage.game.events.SacrificedPermanentEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTargets;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author TheElk801, xenohedron
 */
public class SacrificeOneOrMorePermanentsTriggeredAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<SacrificedPermanentEvent> {

    private final FilterPermanent filter;
    private final SetTargetPointer setTargetPointer;

    private final TargetController sacrificingPlayer;

    /**
     * Whenever you sacrifice one or more "[filter]", "[effect]".
     * zone = battlefield, setTargetPointer = NONE, optional = false
     */
    public SacrificeOneOrMorePermanentsTriggeredAbility(Effect effect, FilterPermanent filter) {
        this(Zone.BATTLEFIELD, effect, filter, TargetController.YOU, SetTargetPointer.NONE, false);
    }

    public SacrificeOneOrMorePermanentsTriggeredAbility(Zone zone, Effect effect, FilterPermanent filter,
                                                        TargetController sacrificingPlayer,
                                                        SetTargetPointer setTargetPointer, boolean optional) {
        super(zone, effect, optional);
        if (Zone.BATTLEFIELD.match(zone)) {
            setLeavesTheBattlefieldTrigger(true);
        }
        this.filter = filter;
        this.setTargetPointer = setTargetPointer;
        this.sacrificingPlayer = sacrificingPlayer;
        setTriggerPhrase(generateTriggerPhrase());
    }

    protected SacrificeOneOrMorePermanentsTriggeredAbility(final SacrificeOneOrMorePermanentsTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.setTargetPointer = ability.setTargetPointer;
        this.sacrificingPlayer = ability.sacrificingPlayer;
    }

    @Override
    public SacrificeOneOrMorePermanentsTriggeredAbility copy() {
        return new SacrificeOneOrMorePermanentsTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SACRIFICED_PERMANENT_BATCH;
    }

    @Override
    public boolean checkEvent(SacrificedPermanentEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent == null || !filter.match(permanent, getControllerId(), this, game)) {
            return false;
        }
        switch (sacrificingPlayer) {
            case YOU:
                return isControlledBy(event.getPlayerId());
            case OPPONENT:
                return game.getOpponents(getControllerId()).contains(event.getPlayerId());
            case ANY:
                return true;
            default:
                throw new IllegalArgumentException("Unsupported TargetController in SacrificePermanentTriggeredAbility: " + sacrificingPlayer);        }
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        List<Permanent> matchingPermanents = getFilteredEvents((SacrificedPermanentBatchEvent) event, game)
                .stream()
                .map(GameEvent::getTargetId)
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (matchingPermanents.isEmpty()) {
            return false;
        }
        this.getEffects().setValue("sacrificedPermanents", matchingPermanents);
        switch (setTargetPointer) {
            case PERMANENT:
                this.getEffects().setTargetPointer(new FixedTargets(matchingPermanents, game));
                break;
            case NONE:
                break;
            default:
                throw new IllegalArgumentException("Unsupported SetTargetPointer in SacrificePermanentTriggeredAbility: " + setTargetPointer);
        }
        return true;
    }

    private String generateTriggerPhrase() {
        String targetControllerText;
        switch (sacrificingPlayer) {
            case YOU:
                targetControllerText = "you sacrifice one or more ";
                break;
            case OPPONENT:
                targetControllerText = "an opponent sacrifices one or more ";
                break;
            case ANY:
                targetControllerText = "one or more players sacrifice one or more ";
                break;
            default:
                throw new IllegalArgumentException("Unsupported TargetController in SacrificePermanentTriggeredAbility: " + sacrificingPlayer);
        }
        return getWhen() + targetControllerText +  filter.getMessage() + ", ";
    }

}
