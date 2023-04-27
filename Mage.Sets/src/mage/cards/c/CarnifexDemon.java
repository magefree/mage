package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author Loki
 */
public final class CarnifexDemon extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("each other creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public CarnifexDemon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.DEMON);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        this.addAbility(FlyingAbility.getInstance());

        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.M1M1.createInstance(2)),
                "with two -1/-1 counters on it"
        ));

        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new AddCountersAllEffect(
                        CounterType.M1M1.createInstance(),
                        filter
                ), new ManaCostsImpl<>("{B}")
        );
        ability.addCost(new RemoveCountersSourceCost(CounterType.M1M1.createInstance()));
        this.addAbility(ability);
    }

    private CarnifexDemon(final CarnifexDemon card) {
        super(card);
    }

    @Override
    public CarnifexDemon copy() {
        return new CarnifexDemon(this);
    }
}
