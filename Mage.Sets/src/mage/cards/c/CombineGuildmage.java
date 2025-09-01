package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.EntersWithCountersControlledEffect;
import mage.abilities.effects.common.counter.MoveCounterTargetsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CombineGuildmage extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("another target creature you control");

    static {
        filter.add(new AnotherTargetPredicate(2));
    }

    public CombineGuildmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {1}{G}, {T}: This turn, each creature you control enters the battlefield with an additional +1/+1 counter on it.
        Ability ability = new SimpleActivatedAbility(new EntersWithCountersControlledEffect(
                Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURE,
                CounterType.P1P1.createInstance(), false
        ), new ManaCostsImpl<>("{1}{G}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {1}{U}, {T}: Move a +1/+1 counter from target creature you control onto another target creature you control.
        ability = new SimpleActivatedAbility(new MoveCounterTargetsEffect(CounterType.P1P1), new ManaCostsImpl<>("{1}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetControlledCreaturePermanent().withChooseHint("to remove a counter from").setTargetTag(1));
        ability.addTarget(new TargetPermanent(filter).withChooseHint("to move a counter to").setTargetTag(2));
        this.addAbility(ability);
    }

    private CombineGuildmage(final CombineGuildmage card) {
        super(card);
    }

    @Override
    public CombineGuildmage copy() {
        return new CombineGuildmage(this);
    }
}
