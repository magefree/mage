package mage.cards.b;

import mage.abilities.common.DealsCombatDamageEquippedTriggeredAbility;
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
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author North
 */
public final class BansheesBlade extends CardImpl {

    private static final CountersSourceCount chargeCountersCount = new CountersSourceCount(CounterType.CHARGE);

    public BansheesBlade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+1 for each charge counter on Banshee's Blade.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(chargeCountersCount, chargeCountersCount)));

        // Whenever equipped creature deals combat damage, put a charge counter on Banshee's Blade.
        this.addAbility(new DealsCombatDamageEquippedTriggeredAbility(
                new AddCountersSourceEffect(CounterType.CHARGE.createInstance(1))
        ));

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2)));
    }

    private BansheesBlade(final BansheesBlade card) {
        super(card);
    }

    @Override
    public BansheesBlade copy() {
        return new BansheesBlade(this);
    }
}
