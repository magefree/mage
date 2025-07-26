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
public final class ScorpionsSting extends CardImpl {

    public ScorpionsSting(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Target creature gets -3/-3 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(-3, -3));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private ScorpionsSting(final ScorpionsSting card) {
        super(card);
    }

    @Override
    public ScorpionsSting copy() {
        return new ScorpionsSting(this);
    }
}
