package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ConductorOfCacophony extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("other creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public ConductorOfCacophony(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Conductor of Cacophony enters the battlefield with two +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)),
                "with two +1/+1 counters on it"
        ));

        // {B}, Remove a +1/+1 counter from Conductor of Cacophony: It deals 1 damage to each other creature and each player.
        Ability ability = new SimpleActivatedAbility(
                new DamageAllEffect(1, "it", filter), new ManaCostsImpl<>("{B}")
        );
        ability.addCost(new RemoveCountersSourceCost(CounterType.P1P1.createInstance()));
        ability.addEffect(new DamagePlayersEffect(1).setText("and each player"));
        this.addAbility(ability);
    }

    private ConductorOfCacophony(final ConductorOfCacophony card) {
        super(card);
    }

    @Override
    public ConductorOfCacophony copy() {
        return new ConductorOfCacophony(this);
    }
}
