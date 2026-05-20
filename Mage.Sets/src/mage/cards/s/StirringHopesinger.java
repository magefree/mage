package mage.cards.s;

import mage.MageInt;
import mage.abilities.abilityword.ReparteeAbility;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
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
public final class StirringHopesinger extends CardImpl {

    public StirringHopesinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Repartee -- Whenever you cast an instant or sorcery spell that targets a creature, put a +1/+1 counter on each creature you control.
        this.addAbility(new ReparteeAbility(new AddCountersAllEffect(
                CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE
        )));
    }

    private StirringHopesinger(final StirringHopesinger card) {
        super(card);
    }

    @Override
    public StirringHopesinger copy() {
        return new StirringHopesinger(this);
    }
}
