package mage.cards.e;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EyesOfTheBeholder extends CardImpl {

    public EyesOfTheBeholder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{B}{B}");

        // Target creature gets -11/-11 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(-11, -11));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private EyesOfTheBeholder(final EyesOfTheBeholder card) {
        super(card);
    }

    @Override
    public EyesOfTheBeholder copy() {
        return new EyesOfTheBeholder(this);
    }
}
