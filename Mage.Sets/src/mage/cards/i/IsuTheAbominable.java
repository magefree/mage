package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.abilities.effects.common.continuous.PlayTheTopCardEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IsuTheAbominable extends CardImpl {

    private static final FilterCard filter = new FilterCard("play snow lands and cast snow spells");
    private static final FilterPermanent filter2 = new FilterPermanent("another snow permanent");

    static {
        filter.add(SuperType.SNOW.getPredicate());
        filter2.add(SuperType.SNOW.getPredicate());
        filter2.add(AnotherPredicate.instance);
    }

    public IsuTheAbominable(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.addSuperType(SuperType.SNOW);
        this.subtype.add(SubType.YETI);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // You may look at the top card of your library any time.
        this.addAbility(new SimpleStaticAbility(new LookAtTopCardOfLibraryAnyTimeEffect()));

        // You may play snow lands and cast snow spells from the top of your library.
        this.addAbility(new SimpleStaticAbility(new PlayTheTopCardEffect(
                TargetController.YOU, filter, false
        )));

        // Whenever another snow permanent enters the battlefield under your control, you may pay {G}, {W}, or {U}. If you do, put a +1/+1 counter on Isu the Abominable.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new DoIfCostPaid(
                        new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                        new OrCost(
                                "pay {G}, {W}, or {U}",
                                new ManaCostsImpl<>("{G}"),
                                new ManaCostsImpl<>("{W}"),
                                new ManaCostsImpl<>("{U}")
                        )
                ), filter2
        ));
    }

    private IsuTheAbominable(final IsuTheAbominable card) {
        super(card);
    }

    @Override
    public IsuTheAbominable copy() {
        return new IsuTheAbominable(this);
    }
}
