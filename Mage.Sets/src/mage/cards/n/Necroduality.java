package mage.cards.n;

import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Necroduality extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent(SubType.ZOMBIE, "a nontoken Zombie");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public Necroduality(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}");

        // Whenever a nontoken Zombie enters the battlefield under your control, create a token that's a copy of that creature.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD,
                new CreateTokenCopyTargetEffect(true).setText("create a token that's a copy of that creature"),
                filter, false, SetTargetPointer.PERMANENT));
    }

    private Necroduality(final Necroduality card) {
        super(card);
    }

    @Override
    public Necroduality copy() {
        return new Necroduality(this);
    }
}
