package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HamletVanguard extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent(SubType.HUMAN);

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TokenPredicate.FALSE);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter, 2);
    private static final Hint hint = new ValueHint(
            "Other nontoken Humans you control", new PermanentsOnBattlefieldCount(filter)
    );

    public HamletVanguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}")));

        // Hamlet Vanguard enters the battlefield with two +1/+1 counters on it for each other nontoken Human you control.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(
                CounterType.P1P1.createInstance(0), xValue, false
        ), "with two +1/+1 counters on it for each other nontoken Human you control").addHint(hint));
    }

    private HamletVanguard(final HamletVanguard card) {
        super(card);
    }

    @Override
    public HamletVanguard copy() {
        return new HamletVanguard(this);
    }
}
