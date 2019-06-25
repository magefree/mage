package mage.cards.d;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DarkRemedy extends CardImpl {

    public DarkRemedy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Target creature gets +1/+3 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 3, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private DarkRemedy(final DarkRemedy card) {
        super(card);
    }

    @Override
    public DarkRemedy copy() {
        return new DarkRemedy(this);
    }
}
