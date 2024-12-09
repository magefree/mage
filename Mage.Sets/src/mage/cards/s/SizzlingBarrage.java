package mage.cards.s;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.BlockedThisTurnPredicate;
import mage.target.TargetPermanent;
import mage.watchers.common.BlockedThisTurnWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SizzlingBarrage extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature that blocked this turn");

    static {
        filter.add(BlockedThisTurnPredicate.instance);
    }

    public SizzlingBarrage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Sizzling Barrage deals 4 damage to target creature that blocked this turn.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addWatcher(new BlockedThisTurnWatcher());
    }

    private SizzlingBarrage(final SizzlingBarrage card) {
        super(card);
    }

    @Override
    public SizzlingBarrage copy() {
        return new SizzlingBarrage(this);
    }
}
