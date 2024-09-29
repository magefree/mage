package mage.cards.e;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.TurnedFaceUpAllTriggeredAbility;
import mage.abilities.dynamicvalue.common.TargetPermanentPowerCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.constants.SubType;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.DisguiseAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.FilterPermanentThisOrAnother;
import mage.filter.StaticFilters;

/**
 * @author Cguy7777
 */
public final class ExperimentTwelve extends CardImpl {

    private static final FilterPermanentThisOrAnother filter = new FilterPermanentThisOrAnother(StaticFilters.FILTER_CONTROLLED_CREATURE, true);

    public ExperimentTwelve(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.LIZARD);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Experiment Twelve or another creature you control is turned face up, put +1/+1 counters on that creature equal to its power.
        Effect effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance(0), TargetPermanentPowerCount.instance)
                .setText("put +1/+1 counters on that creature equal to its power");
        this.addAbility(new TurnedFaceUpAllTriggeredAbility(effect, filter, true));

        // Disguise {4}{G}
        this.addAbility(new DisguiseAbility(this, new ManaCostsImpl<>("{4}{G}")));

    }

    private ExperimentTwelve(final ExperimentTwelve card) {
        super(card);
    }

    @Override
    public ExperimentTwelve copy() {
        return new ExperimentTwelve(this);
    }
}
