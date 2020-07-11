package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AnimalSanctuary extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Bird, Cat, Dog, Goat, Ox, or Snake");

    static {
        filter.add(Predicates.or(
                SubType.BIRD.getPredicate(),
                SubType.CAT.getPredicate(),
                SubType.DOG.getPredicate(),
                SubType.GOAT.getPredicate(),
                SubType.OX.getPredicate(),
                SubType.SNAKE.getPredicate()
        ));
    }

    public AnimalSanctuary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {2}, {T}: Put a +1/+1 counter on target Bird, Cat, Dog, Goat, Ox, or Snake.
        Ability ability = new SimpleActivatedAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()), new GenericManaCost(2)
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private AnimalSanctuary(final AnimalSanctuary card) {
        super(card);
    }

    @Override
    public AnimalSanctuary copy() {
        return new AnimalSanctuary(this);
    }
}
