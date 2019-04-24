
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;

/**
 *
 * @author emerald000
 */
public final class MummyParamount extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("another Zombie");
    static {
        filter.add(new SubtypePredicate(SubType.ZOMBIE));
        filter.add(new AnotherPredicate());
    }

    public MummyParamount(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever another Zombie enters the battlefield under your control, Mummy Paramount gets +1/+1 until end of turn.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(new BoostSourceEffect(1, 1, Duration.EndOfTurn), filter));
    }

    public MummyParamount(final MummyParamount card) {
        super(card);
    }

    @Override
    public MummyParamount copy() {
        return new MummyParamount(this);
    }
}
