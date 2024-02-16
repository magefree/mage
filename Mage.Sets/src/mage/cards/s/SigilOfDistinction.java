
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 * @author Loki
 */
public final class SigilOfDistinction extends CardImpl {

    public SigilOfDistinction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{X}");
        this.subtype.add(SubType.EQUIPMENT);

        // Sigil of Distinction enters the battlefield with X charge counters on it.
        this.addAbility(new EntersBattlefieldAbility(new EntersBattlefieldWithXCountersEffect(CounterType.CHARGE.createInstance())));

        // Equipped creature gets +1/+1 for each charge counter on Sigil of Distinction.
        BoostEquippedEffect effect = new BoostEquippedEffect(new CountersSourceCount(CounterType.CHARGE), new CountersSourceCount(CounterType.CHARGE));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

        // Equip—Remove a charge counter from Sigil of Distinction.
        this.addAbility(new EquipAbility(Outcome.AddAbility, new RemoveCountersSourceCost(CounterType.CHARGE.createInstance()), false));

    }

    private SigilOfDistinction(final SigilOfDistinction card) {
        super(card);
    }

    @Override
    public SigilOfDistinction copy() {
        return new SigilOfDistinction(this);
    }
}
