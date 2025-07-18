package mage.cards.d;

import mage.MageInt;
import mage.abilities.condition.common.TwoTappedCreaturesCondition;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DawnstrikeVanguard extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("creature you control other than this creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public DawnstrikeVanguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // At the beginning of your end step, if you control two or more tapped creatures, put a +1/+1 counter on each creature you control other than this creature.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter)
        ).withInterveningIf(TwoTappedCreaturesCondition.instance).addHint(TwoTappedCreaturesCondition.getHint()));
    }

    private DawnstrikeVanguard(final DawnstrikeVanguard card) {
        super(card);
    }

    @Override
    public DawnstrikeVanguard copy() {
        return new DawnstrikeVanguard(this);
    }
}
