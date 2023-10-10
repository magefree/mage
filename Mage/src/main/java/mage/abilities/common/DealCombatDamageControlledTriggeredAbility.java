package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.DamagedBatchEvent;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author LevelX2
 */
public class DealCombatDamageControlledTriggeredAbility extends TriggeredAbilityImpl {

    private final SetTargetPointer setTargetPointer;
    private final FilterCreaturePermanent filter;

    public DealCombatDamageControlledTriggeredAbility(Effect effect) {
        this(effect, SetTargetPointer.NONE);
    }

    public DealCombatDamageControlledTriggeredAbility(Effect effect, FilterCreaturePermanent filter) {
        this(Zone.BATTLEFIELD, effect, filter, SetTargetPointer.NONE, false);
    }

    public DealCombatDamageControlledTriggeredAbility(Effect effect, SetTargetPointer setTargetPointer) {
        this(Zone.BATTLEFIELD, effect, StaticFilters.FILTER_PERMANENT_CREATURES, setTargetPointer, false);
    }

    public DealCombatDamageControlledTriggeredAbility(Zone zone, Effect effect, FilterCreaturePermanent filter, SetTargetPointer setTargetPointer, boolean optional) {
        super(zone, effect, optional);
        this.setTargetPointer = setTargetPointer;
        this.filter = filter;
        setTriggerPhrase("Whenever one or more " + filter.getMessage() + " you control deal combat damage to a player, ");
    }

    protected DealCombatDamageControlledTriggeredAbility(final DealCombatDamageControlledTriggeredAbility ability) {
        super(ability);
        this.setTargetPointer = ability.setTargetPointer;
        this.filter = ability.filter;
    }

    @Override
    public DealCombatDamageControlledTriggeredAbility copy() {
        return new DealCombatDamageControlledTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_FOR_ONE_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        List<DamagedEvent> events = ((DamagedBatchEvent) event).getEvents().stream()
            .filter(DamagedEvent::isCombatDamage)
            .filter(e -> {
                Permanent permanent = game.getPermanentOrLKIBattlefield(e.getSourceId());
                return permanent != null
                        && filter.match(permanent, game)
                        && permanent.isControlledBy(this.getControllerId());
            })
            .collect(Collectors.toList());
        
        if (events.isEmpty()) {
            return false;
        }
        
        this.getEffects().setValue("damage", events.stream().mapToInt(DamagedEvent::getAmount).sum());
        switch (setTargetPointer) {
            case PLAYER:
                this.getEffects().setTargetPointer(new FixedTarget(event.getPlayerId()));
                break;
            case NONE:
                break;
            default:
                throw new IllegalArgumentException("Invalid SetTargetPointer option");
        }

        return true;
    }
}