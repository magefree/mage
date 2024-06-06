package mage.cards.n;

import mage.MageInt;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedGainedLifeValue;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class NykthosParagon extends CardImpl {

    public NykthosParagon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{4}{W}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Whenever you gain life, you may put that many +1/+1 counters on each creature you control. Do this only once each turn.
        this.addAbility(new GainLifeControllerTriggeredAbility(
                new AddCountersAllEffect(
                        CounterType.P1P1.createInstance(),
                        SavedGainedLifeValue.MANY,
                        StaticFilters.FILTER_CONTROLLED_CREATURE
                ), true, true
        ).setDoOnlyOnceEachTurn(true));
    }

    private NykthosParagon(final NykthosParagon card) {
        super(card);
    }

    @Override
    public NykthosParagon copy() {
        return new NykthosParagon(this);
    }
}