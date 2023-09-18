
package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author Loki
 */
public final class Festercreep extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("all other creatures");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public Festercreep(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Festercreep enters the battlefield with a +1/+1 counter on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(1)), "with a +1/+1 counter on it"));

        // {1}{B}, Remove a +1/+1 counter from Festercreep: All other creatures get -1/-1 until end of turn.
        Ability ability = new SimpleActivatedAbility(new BoostAllEffect(
                -1, -1, Duration.EndOfTurn, filter, false
        ), new ManaCostsImpl<>("{1}{B}"));
        ability.addCost(new RemoveCountersSourceCost(CounterType.P1P1.createInstance(1)));
        this.addAbility(ability);
    }

    private Festercreep(final Festercreep card) {
        super(card);
    }

    @Override
    public Festercreep copy() {
        return new Festercreep(this);
    }
}
