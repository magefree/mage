package mage.cards.m;

import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MaleficScythe extends CardImpl {

    private static final DynamicValue xValue = new CountersSourceCount(CounterType.SOUL);

    public MaleficScythe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{B}");

        this.subtype.add(SubType.EQUIPMENT);

        // Malefic Scythe enters the battlefield with a soul counter on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.SOUL.createInstance(1)),
                "with a soul counter on it"
        ));

        // Equipped creature gets +1/+1 for each soul counter on Malefic Scythe.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(xValue, xValue, Duration.WhileOnBattlefield)));

        // Whenever equipped creature dies, put a soul counter on Malefic Scythe.
        this.addAbility(new DiesAttachedTriggeredAbility(
                new AddCountersSourceEffect(CounterType.SOUL.createInstance()), "equipped creature"
        ));

        // Equip {1}
        this.addAbility(new EquipAbility(1));
    }

    private MaleficScythe(final MaleficScythe card) {
        super(card);
    }

    @Override
    public MaleficScythe copy() {
        return new MaleficScythe(this);
    }
}
