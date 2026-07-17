package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.WaterbendCost;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.continuous.AddCardTypeSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.ExhaustAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionSubmersible extends CardImpl {

    private static final FilterPermanent filter = new FilterNonlandPermanent("other target nonland permanent");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public InvasionSubmersible(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{U}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // When this Vehicle enters, return up to one other target nonland permanent to its owner's hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect());
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability);

        // Exhaust -- Waterbend {3}: This Vehicle becomes an artifact creature. Put three +1/+1 counters on it.
        ability = new ExhaustAbility(new AddCardTypeSourceEffect(Duration.Custom, CardType.ARTIFACT, CardType.CREATURE), new WaterbendCost(3));
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance(3)).setText("Put three +1/+1 counters on it"));
        this.addAbility(ability);
    }

    private InvasionSubmersible(final InvasionSubmersible card) {
        super(card);
    }

    @Override
    public InvasionSubmersible copy() {
        return new InvasionSubmersible(this);
    }
}
