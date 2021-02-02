
package mage.cards.o;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.SunburstAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author Plopman
 */
public final class OpalineBracers extends CardImpl {

    public OpalineBracers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");
        this.subtype.add(SubType.EQUIPMENT);

        // Sunburst
        this.addAbility(new SunburstAbility(this));
        // Equipped creature gets +X/+X, where X is the number of charge counters on Opaline Bracers.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(new CountersSourceCount(CounterType.CHARGE), new CountersSourceCount(CounterType.CHARGE))));
        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(2)));
    }

    private OpalineBracers(final OpalineBracers card) {
        super(card);
    }

    @Override
    public OpalineBracers copy() {
        return new OpalineBracers(this);
    }
}
