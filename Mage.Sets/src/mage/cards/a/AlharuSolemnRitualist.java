package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.PartnerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.SpiritWhiteToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AlharuSolemnRitualist extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("other creatures");
    private static final FilterPermanent filter2 = new FilterControlledCreaturePermanent(
            "a nontoken creature you control with a +1/+1 counter on it"
    );

    static {
        filter.add(AnotherPredicate.instance);
        filter2.add(TokenPredicate.FALSE);
        filter2.add(CounterType.P1P1.getPredicate());
    }

    public AlharuSolemnRitualist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Alharu, Solemn Ritualist enters the battlefield, put a +1/+1 counter on each of up to two other target creatures.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance())
                        .setText("put a +1/+1 counter on each of up to two other target creatures")
        );
        ability.addTarget(new TargetPermanent(0, 2, filter, false));
        this.addAbility(ability);

        // Whenever a nontoken creature you control with a +1/+1 counter on it dies, create a 1/1 white Spirit creature token with flying.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new CreateTokenEffect(new SpiritWhiteToken()), false, filter2
        ));

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private AlharuSolemnRitualist(final AlharuSolemnRitualist card) {
        super(card);
    }

    @Override
    public AlharuSolemnRitualist copy() {
        return new AlharuSolemnRitualist(this);
    }
}
