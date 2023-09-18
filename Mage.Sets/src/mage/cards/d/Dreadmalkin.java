package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Dreadmalkin extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent("another creature or planeswalker");

    static {
        filter.add(Predicates.or(
                CardType.PLANESWALKER.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
        filter.add(AnotherPredicate.instance);
    }

    public Dreadmalkin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.CAT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Menace
        this.addAbility(new MenaceAbility());

        // {2}{B}, Sacrifice another creature or planeswalker: Put two +1/+1 counters on Dreadmalkin.
        Ability ability = new SimpleActivatedAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)), new ManaCostsImpl<>("{2}{B}")
        );
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        this.addAbility(ability);
    }

    private Dreadmalkin(final Dreadmalkin card) {
        super(card);
    }

    @Override
    public Dreadmalkin copy() {
        return new Dreadmalkin(this);
    }
}
