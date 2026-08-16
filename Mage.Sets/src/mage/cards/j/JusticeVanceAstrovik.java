package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.ZoneChangeAllTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author muz
 */
public final class JusticeVanceAstrovik extends CardImpl {

    private static final FilterPermanent filter = new FilterNonlandPermanent("nonland, nontoken permanent");
    private static final FilterPermanent filter2 = new FilterNonlandPermanent("another nonland permanent you control");

    static {
        filter.add(TokenPredicate.FALSE);
        filter2.add(AnotherPredicate.instance);
        filter2.add(TargetController.YOU.getControllerPredicate());
    }

    public JusticeVanceAstrovik(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.HERO);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Justice enters, return up to one target nonland, nontoken permanent to its owner's hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect());
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability);

        // Whenever another nonland permanent you control is returned to its owner's hand, put a +1/+1 counter on Justice.
        this.addAbility(new ZoneChangeAllTriggeredAbility(
            Zone.BATTLEFIELD, Zone.BATTLEFIELD, Zone.HAND,
            new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
            filter2, "Whenever another nonland permanent you control is returned to its owner's hand, ", false
        ));
    }

    private JusticeVanceAstrovik(final JusticeVanceAstrovik card) {
        super(card);
    }

    @Override
    public JusticeVanceAstrovik copy() {
        return new JusticeVanceAstrovik(this);
    }
}
