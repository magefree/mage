package mage.cards.b;

import mage.abilities.effects.common.DamageTargetAndTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterAnyTarget;
import mage.filter.common.FilterPermanentOrPlayer;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetPermanentOrPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BoulderDash extends CardImpl {

    private static final FilterPermanentOrPlayer filter2 = new FilterAnyTarget("any other target");

    static {
        filter2.getPermanentFilter().add(new AnotherTargetPredicate(2));
        filter2.getPlayerFilter().add(new AnotherTargetPredicate(2));
    }

    public BoulderDash(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Boulder Dash deals 2 damage to any target and 1 damage to any other target.
        this.getSpellAbility().addEffect(new DamageTargetAndTargetEffect(2, 1));
        this.getSpellAbility().addTarget(new TargetAnyTarget()
                .withChooseHint("to deal 2 damage").setTargetTag(1));
        this.getSpellAbility().addTarget(new TargetPermanentOrPlayer(filter2)
                .withChooseHint("to deal 1 damage").setTargetTag(2));
    }

    private BoulderDash(final BoulderDash card) {
        super(card);
    }

    @Override
    public BoulderDash copy() {
        return new BoulderDash(this);
    }
}
