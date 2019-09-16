package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterObject;
import mage.filter.predicate.mageobject.MulticoloredPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StonecoilSerpent extends CardImpl {

    private static final FilterObject filter = new FilterObject("multicolored");

    static {
        filter.add(MulticoloredPredicate.instance);
    }

    public StonecoilSerpent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{X}");

        this.subtype.add(SubType.SNAKE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Protection from multicolored
        this.addAbility(new ProtectionAbility(filter));

        // Stonecoil Serpent enters the battlefield with X +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new EntersBattlefieldWithXCountersEffect(CounterType.P1P1.createInstance())));
    }

    private StonecoilSerpent(final StonecoilSerpent card) {
        super(card);
    }

    @Override
    public StonecoilSerpent copy() {
        return new StonecoilSerpent(this);
    }
}
