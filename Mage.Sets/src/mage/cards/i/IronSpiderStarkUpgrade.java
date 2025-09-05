package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCounterCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IronSpiderStarkUpgrade extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent("creature and/or Vehicle you control");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                SubType.VEHICLE.getPredicate()
        ));
    }

    public IronSpiderStarkUpgrade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIDER);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // {T}: Put a +1/+1 counter on each artifact creature and/or Vehicle you control.
        this.addAbility(new SimpleActivatedAbility(
                new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter), new TapSourceCost()
        ));

        // {2}, Remove two +1/+1 counters from among artifacts you control: Draw a card.
        Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new GenericManaCost(2));
        ability.addCost(new RemoveCounterCost(new TargetPermanent(
                1, 2, StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT
        ), CounterType.P1P1, 2).setText("remove two +1/+1 counters from among creatures you control"));
        this.addAbility(ability);
    }

    private IronSpiderStarkUpgrade(final IronSpiderStarkUpgrade card) {
        super(card);
    }

    @Override
    public IronSpiderStarkUpgrade copy() {
        return new IronSpiderStarkUpgrade(this);
    }
}
