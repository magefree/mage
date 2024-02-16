package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.DamagedBatchEvent;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Xanderhall, xenohedron
 */
public class OneOrMoreDealDamageTriggeredAbility extends TriggeredAbilityImpl {

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
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedBatchEvent dEvent = (DamagedBatchEvent) event;
        if (onlyCombat && !dEvent.isCombatDamage()) {
            return false;
        }
        List<DamagedEvent> events = dEvent
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
                })
                .collect(Collectors.toList());

        if (events.isEmpty()) {
            return false;
        }

        this.getAllEffects().setValue("damage", events.stream().mapToInt(DamagedEvent::getAmount).sum());
        switch (setTargetPointer) {
            case PLAYER:
                this.getAllEffects().setTargetPointer(new FixedTarget(event.getPlayerId()));
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
