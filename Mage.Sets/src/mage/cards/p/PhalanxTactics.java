package mage.cards.p;

import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PhalanxTactics extends CardImpl {

    public PhalanxTactics(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Target creature you control gets +2/+1 until end of turn. Each other creature you control gets +1/+1 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 0)
                .setText("Target creature you control gets +2/+1 until end of turn."));
        this.getSpellAbility().addEffect(new BoostControlledEffect(1, 1, Duration.EndOfTurn)
                .setText("Each other creature you control gets +1/+1 until end of turn."));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private PhalanxTactics(final PhalanxTactics card) {
        super(card);
    }

    @Override
    public PhalanxTactics copy() {
        return new PhalanxTactics(this);
    }
}
