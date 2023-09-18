package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.KithkinSoldierToken;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class KinsbaileBorderguard extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(SubType.KITHKIN.getPredicate());
    }

    public KinsbaileBorderguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");
        this.subtype.add(SubType.KITHKIN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Kinsbaile Borderguard enters the battlefield with a +1/+1 counter on it for each other Kithkin you control.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(0),
                new PermanentsOnBattlefieldCount(filter), true), "with a +1/+1 counter on it for each other Kithkin you control"));
        // When Kinsbaile Borderguard dies, create a 1/1 white Kithkin Soldier creature token for each counter on it.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new KithkinSoldierToken(), new AllCountersCount())));
    }

    private KinsbaileBorderguard(final KinsbaileBorderguard card) {
        super(card);
    }

    @Override
    public KinsbaileBorderguard copy() {
        return new KinsbaileBorderguard(this);
    }
}

class AllCountersCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(sourceAbility.getSourceId());
        if (sourcePermanent != null) {
            int total = 0;
            for (Counter counter : sourcePermanent.getCounters(game).values()) {
                total += counter.getCount();
            }
            return total;
        }
        return 0;
    }

    @Override
    public AllCountersCount copy() {
        return new AllCountersCount();
    }

    @Override
    public String getMessage() {
        return "counter on it";
    }

    @Override
    public String toString() {
        return "1";
    }
}
