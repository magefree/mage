package mage.abilities.common;

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

import java.util.stream.Stream;

/**
 * @author Xanderhall, xenohedron
 */
// TODO Susucr: rename to DealsDamageOneOrMoreToAPlayerTriggeredAbility after merge for some consistency
public class OneOrMoreDealDamageTriggeredAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<DamagedPlayerEvent> {

    private final SetTargetPointer setTargetPointer;
    private final FilterPermanent filter;
    private final boolean onlyCombat;
    private final boolean onlyControlled;

    public OneOrMoreDealDamageTriggeredAbility(Effect effect, FilterPermanent filter, boolean onlyCombat, boolean onlyControlled) {
        this(Zone.BATTLEFIELD, effect, filter, onlyCombat, onlyControlled, SetTargetPointer.NONE, false);
    }

    public OneOrMoreDealDamageTriggeredAbility(Zone zone, Effect effect, FilterPermanent filter,
                                               boolean onlyCombat, boolean onlyControlled,
                                               SetTargetPointer setTargetPointer, boolean optional) {
        super(zone, effect, optional);
        this.setTargetPointer = setTargetPointer;
        this.filter = filter;
        this.onlyCombat = onlyCombat;
        this.onlyControlled = onlyControlled;
        makeTriggerPhrase();
    }

    protected OneOrMoreDealDamageTriggeredAbility(final OneOrMoreDealDamageTriggeredAbility ability) {
        super(ability);
        this.setTargetPointer = ability.setTargetPointer;
        this.filter = ability.filter;
        this.onlyCombat = ability.onlyCombat;
        this.onlyControlled = ability.onlyControlled;
    }

    @Override
    public OneOrMoreDealDamageTriggeredAbility copy() {
        return new OneOrMoreDealDamageTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_FOR_ONE_PLAYER;
    }

    @Override
    public Stream<DamagedPlayerEvent> filterBatchEvent(GameEvent event, Game game) {
        DamagedBatchForOnePlayerEvent dEvent = (DamagedBatchForOnePlayerEvent) event;
        if (onlyCombat && !dEvent.isCombatDamage()) {
            return Stream.empty();
        }
        return dEvent
                .getEvents()
                .stream()
                .filter(e -> {
                    Permanent permanent = game.getPermanentOrLKIBattlefield(e.getSourceId());
                    if (permanent == null) {
                        return false;
                    }
                    if (onlyControlled && !permanent.isControlledBy(this.getControllerId())) {
                        return false;
                    }
                    return filter.match(permanent, this.getControllerId(), this, game);
                });
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        int amount = filterBatchEvent(event, game)
                .mapToInt(DamagedEvent::getAmount)
                .sum();
        if (amount <= 0) {
            return false;
        }
        this.getAllEffects().setValue("damage", amount);
        switch (setTargetPointer) {
            case PLAYER:
                this.getAllEffects().setTargetPointer(new FixedTarget(event.getTargetId()));
                break;
            case NONE:
                break;
            default:
                throw new IllegalArgumentException("Invalid SetTargetPointer option");
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
