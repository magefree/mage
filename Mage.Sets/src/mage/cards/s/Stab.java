package mage.cards.s;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Stab extends CardImpl {

    public Stab(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Target creature gets -2/-2 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(-2, -2));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Stab(final Stab card) {
        super(card);
    }

    @Override
    public Stab copy() {
        return new Stab(this);
    }
}
