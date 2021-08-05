package mage.cards.l;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LashOfMalice extends CardImpl {

    public LashOfMalice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Target creature gets +2/-2 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, -2));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private LashOfMalice(final LashOfMalice card) {
        super(card);
    }

    @Override
    public LashOfMalice copy() {
        return new LashOfMalice(this);
    }
}
