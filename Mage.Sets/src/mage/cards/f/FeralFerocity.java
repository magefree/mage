package mage.cards.f;

import java.util.UUID;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author muz
 */
public final class FeralFerocity extends CardImpl {

    public FeralFerocity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Target creature gets +4/+4 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(4, 4));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private FeralFerocity(final FeralFerocity card) {
        super(card);
    }

    @Override
    public FeralFerocity copy() {
        return new FeralFerocity(this);
    }
}
