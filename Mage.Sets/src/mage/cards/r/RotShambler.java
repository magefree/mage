package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class RotShambler extends CardImpl {

    public RotShambler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.FUNGUS);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever another creature you control dies, put a +1/+1 counter on Rot Shambler.
        this.addAbility(new DiesCreatureTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false, StaticFilters.FILTER_ANOTHER_CREATURE_YOU_CONTROL));

    }

    private RotShambler(final RotShambler card) {
        super(card);
    }

    @Override
    public RotShambler copy() {
        return new RotShambler(this);
    }
}
