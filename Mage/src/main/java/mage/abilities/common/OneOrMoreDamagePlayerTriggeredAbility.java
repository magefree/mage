package mage.abilities.common;

import mage.MageObjectReference;
import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.DamagedBatchForOnePlayerEvent;
import mage.game.events.DamagedEvent;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.target.targetpointer.FixedTargets;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Xanderhall, xenohedron
 */
public class OneOrMoreDamagePlayerTriggeredAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<DamagedPlayerEvent> {

    private final SetTargetPointer setTargetPointer;
    private final FilterPermanent filter;
    private final boolean onlyCombat;
    private final boolean onlyControlled;

    public OneOrMoreDamagePlayerTriggeredAbility(Effect effect, FilterPermanent filter, boolean onlyCombat, boolean onlyControlled) {
        this(Zone.BATTLEFIELD, effect, filter, onlyCombat, onlyControlled, SetTargetPointer.NONE, false);
    }

    public OneOrMoreDamagePlayerTriggeredAbility(Zone zone, Effect effect, FilterPermanent filter,
                                                 boolean onlyCombat, boolean onlyControlled,
                                                 SetTargetPointer setTargetPointer, boolean optional) {
        super(zone, effect, optional);
        this.setTargetPointer = setTargetPointer;
        this.filter = filter;
        this.onlyCombat = onlyCombat;
        this.onlyControlled = onlyControlled;
        makeTriggerPhrase();
    }

    protected OneOrMoreDamagePlayerTriggeredAbility(final OneOrMoreDamagePlayerTriggeredAbility ability) {
        super(ability);
        this.setTargetPointer = ability.setTargetPointer;
        this.filter = ability.filter;
        this.onlyCombat = ability.onlyCombat;
        this.onlyControlled = ability.onlyControlled;
    }

    @Override
    public OneOrMoreDamagePlayerTriggeredAbility copy() {
        return new OneOrMoreDamagePlayerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_FOR_ONE_PLAYER;
    }

    @Override
    public boolean checkEvent(DamagedPlayerEvent event, Game game) {
        if (onlyCombat && !event.isCombatDamage()) {
            return false;
        }
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
        if (permanent == null) {
            return false;
        }
        if (onlyControlled && !permanent.isControlledBy(this.getControllerId())) {
            return false;
        }
        return filter.match(permanent, this.getControllerId(), this, game);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        List<DamagedPlayerEvent> events = getFilteredEvents((DamagedBatchForOnePlayerEvent) event, game);
        if (events.isEmpty()) {
            return false;
        }
        this.getAllEffects().setValue("damage", events.stream().mapToInt(DamagedEvent::getAmount).sum());
        switch (setTargetPointer) {
            case PLAYER:
                this.getAllEffects().setTargetPointer(new FixedTarget(event.getTargetId()));
                break;
            case PERMANENT:
                Set<MageObjectReference> attackerSet = events
                        .stream()
                        .map(GameEvent::getSourceId)
                        .map(game::getPermanent)
                        .filter(Objects::nonNull)
                        .map(permanent -> new MageObjectReference(permanent, game))
                        .collect(Collectors.toSet());
                this.getAllEffects().setTargetPointer(new FixedTargets(attackerSet));
                break;
            case NONE:
                break;
            default:
                throw new IllegalArgumentException("Unsupported SetTargetPointer in OneOrMoreDamagePlayerTriggeredAbility");
        }
        return true;
    }

    private void makeTriggerPhrase() {
        String filterMessage = filter.getMessage();
        if (onlyControlled && !filterMessage.contains("you control")) {
            filterMessage += " you control";
        }
        setTriggerPhrase("Whenever one or more " + filterMessage + " deal " + (onlyCombat ? "combat " : "") + "damage to a player, ");
    }

}
