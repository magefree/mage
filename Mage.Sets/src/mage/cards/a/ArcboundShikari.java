package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.ModularAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArcboundShikari extends CardImpl {

    private static final FilterPermanent filter
            = new FilterArtifactCreaturePermanent("other artifact creature you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public ArcboundShikari(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{R}{W}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // First Strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // When Arcbound Shikari enters the battlefield, put a +1/+1 counter on each other artifact creature you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter)
        ));

        // Modular 2
        this.addAbility(new ModularAbility(this, 2));
    }

    private ArcboundShikari(final ArcboundShikari card) {
        super(card);
    }

    @Override
    public ArcboundShikari copy() {
        return new ArcboundShikari(this);
    }
}
