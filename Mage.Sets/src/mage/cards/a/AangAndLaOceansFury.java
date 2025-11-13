package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AangAndLaOceansFury extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("tapped creature you control");

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    public AangAndLaOceansFury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AVATAR);
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);
        this.nightCard = true;

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Aang and La attack, put a +1/+1 counter on each tapped creature you control.
        this.addAbility(new AttacksTriggeredAbility(new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter)));
    }

    private AangAndLaOceansFury(final AangAndLaOceansFury card) {
        super(card);
    }

    @Override
    public AangAndLaOceansFury copy() {
        return new AangAndLaOceansFury(this);
    }
}
