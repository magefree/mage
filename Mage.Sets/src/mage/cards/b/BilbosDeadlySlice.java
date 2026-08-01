package mage.cards.b;

import java.util.UUID;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author muz
 */
public final class BilbosDeadlySlice extends CardImpl {

    public BilbosDeadlySlice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}{B}");

        // Destroy target creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private BilbosDeadlySlice(final BilbosDeadlySlice card) {
        super(card);
    }

    @Override
    public BilbosDeadlySlice copy() {
        return new BilbosDeadlySlice(this);
    }
}
