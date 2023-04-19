package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.replacement.ModifyCountersAddedEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ConclaveMentor extends CardImpl {

    private static final DynamicValue xValue = new SourcePermanentPowerCount(false);

    public ConclaveMentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}");

        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // If one or more +1/+1 counters would be put on a creature you control, that many plus one +1/+1 counters are put on that creature instead.
        this.addAbility(new SimpleStaticAbility(new ModifyCountersAddedEffect(StaticFilters.FILTER_CONTROLLED_CREATURE, CounterType.P1P1)));

        // When Conclave Mentor dies, you gain life equal to its power.
        this.addAbility(new DiesSourceTriggeredAbility(new GainLifeEffect(xValue, "you gain life equal to its power")));
    }

    private ConclaveMentor(final ConclaveMentor card) {
        super(card);
    }

    @Override
    public ConclaveMentor copy() {
        return new ConclaveMentor(this);
    }
}
