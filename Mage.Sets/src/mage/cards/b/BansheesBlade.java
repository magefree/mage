
package mage.cards.b;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

/**
 *
 * @author North
 */
public final class BansheesBlade extends CardImpl {

    public BansheesBlade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+1 for each charge counter on Banshee's Blade.
        CountersSourceCount chargeCountersCount = new CountersSourceCount(CounterType.CHARGE);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(chargeCountersCount, chargeCountersCount)));
        // Whenever equipped creature deals combat damage, put a charge counter on Banshee's Blade.
        this.addAbility(new BansheesBladeAbility());
        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2)));
    }

    public BansheesBlade(final BansheesBlade card) {
        super(card);
    }

    @Override
    public BansheesBlade copy() {
        return new BansheesBlade(this);
    }
}

class BansheesBladeAbility extends TriggeredAbilityImpl {

    private boolean usedInPhase;

    public BansheesBladeAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.CHARGE.createInstance(1)));
        this.usedInPhase = false;
    }

    public BansheesBladeAbility(final BansheesBladeAbility ability) {
        super(ability);
        this.usedInPhase = ability.usedInPhase;
    }

    @Override
    public BansheesBladeAbility copy() {
        return new BansheesBladeAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGED_PLAYER
                || event.getType() == EventType.DAMAGED_CREATURE
                || event.getType() == EventType.DAMAGED_PLANESWALKER
                || event.getType() == EventType.COMBAT_DAMAGE_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event instanceof DamagedEvent && ((DamagedEvent) event).isCombatDamage() && !usedInPhase) {
            Permanent permanent = game.getPermanent(event.getSourceId());
            if (permanent != null && permanent.getAttachments().contains(this.getSourceId())) {
                usedInPhase = true;
                return true;
            }
        }
        if (event.getType() == EventType.COMBAT_DAMAGE_STEP_PRE) {
            usedInPhase = false;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever equipped creature deals combat damage, put a charge counter on {this}.";
    }
}
