
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author North
 */
public final class MidnightGuard extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("another creature");
    static {
        filter.add(AnotherPredicate.instance);
    }

    public MidnightGuard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever another creature enters the battlefield, untap Midnight Guard.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(new UntapSourceEffect(), filter));
    }

    private MidnightGuard(final MidnightGuard card) {
        super(card);
    }

    @Override
    public MidnightGuard copy() {
        return new MidnightGuard(this);
    }
}
