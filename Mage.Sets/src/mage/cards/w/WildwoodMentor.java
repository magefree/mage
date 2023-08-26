package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class WildwoodMentor extends CardImpl {

    private static final DynamicValue xValue = new SourcePermanentPowerCount();

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another target attacking creature");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(AttackingPredicate.instance);
    }

    public static final FilterPermanent filterToken = new FilterPermanent("a token");

    static {
        filterToken.add(TokenPredicate.TRUE);
    }

    public WildwoodMentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.TREEFOLK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever a token enters the battlefield under your control, put a +1/+1 counter on Wildwood Mentor.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                filterToken
        ));

        // Whenever Wildwood Mentor attacks, another target attacking creature gets +X/+X until end of turn, where X is Wildwood Mentor's power.
        Ability ability = new AttacksTriggeredAbility(
                new BoostTargetEffect(xValue, xValue, Duration.EndOfTurn)
                        .setText("another target attacking creature gets +X/+X until end of turn, where X is {this}'s power"),
                false
        );
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private WildwoodMentor(final WildwoodMentor card) {
        super(card);
    }

    @Override
    public WildwoodMentor copy() {
        return new WildwoodMentor(this);
    }
}
