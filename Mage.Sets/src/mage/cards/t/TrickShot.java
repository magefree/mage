package mage.cards.t;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

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
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 1, filter, false).setTargetTag(2));
        this.getSpellAbility().addEffect(new DamageTargetEffect(6, true, "", true));
        this.getSpellAbility().addEffect(
                new DamageTargetEffect(2, true, "", true)
                        .setTargetPointer(new SecondTargetPointer())
                        .setText("and 2 damage to up to one other target creature token")
        );
    }

    private TrickShot(final TrickShot card) {
        super(card);
    }

    @Override
    public TrickShot copy() {
        return new TrickShot(this);
    }
}
