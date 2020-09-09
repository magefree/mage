package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.CounterPredicate;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SkyclaveShadowcat extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("a creature you control with a +1/+1 counter on it");

    static {
        filter.add(new CounterPredicate(CounterType.P1P1));
    }

    public SkyclaveShadowcat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {1}{B}, Sacrifice another creature: Put a +1/+1 counter on Skyclave Shadowcat.
        Ability ability = new SimpleActivatedAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), new ManaCostsImpl<>("{1}{B}")
        );
        ability.addCost(new SacrificeTargetCost(
                new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE)
        ));
        this.addAbility(ability);

        // Whenever a creature you control with a +1/+1 counter on it dies, draw a card.
        this.addAbility(new DiesCreatureTriggeredAbility(new DrawCardSourceControllerEffect(1), false, filter));
    }

    private SkyclaveShadowcat(final SkyclaveShadowcat card) {
        super(card);
    }

    @Override
    public SkyclaveShadowcat copy() {
        return new SkyclaveShadowcat(this);
    }
}
