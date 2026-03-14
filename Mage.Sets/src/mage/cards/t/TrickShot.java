package mage.cards.t;

import mage.abilities.effects.common.DamageTargetAndTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TrickShot extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("other target creature token");

    static {
        filter.add(new AnotherTargetPredicate(2));
        filter.add(TokenPredicate.TRUE);
    }

    public TrickShot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{R}");

        // Trick Shot deals 6 damage to target creature and 2 damage to up to one other target creature token.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent().setTargetTag(1));
        this.getSpellAbility().addTarget(new TargetPermanent(0, 1, filter).setTargetTag(2));
        this.getSpellAbility().addEffect(new DamageTargetAndTargetEffect(6, 2));
    }

    private TrickShot(final TrickShot card) {
        super(card);
    }

    @Override
    public TrickShot copy() {
        return new TrickShot(this);
    }
}
