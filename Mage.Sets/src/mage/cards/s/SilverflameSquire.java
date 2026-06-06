package mage.cards.s;

import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SilverflameSquire extends AdventureCard {

    public SilverflameSquire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.SOLDIER}, "{1}{W}",
                "On Alert",
                new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Silverflame Squire
        this.getLeftHalfCard().setPT(2, 1);

        // On Alert
        // Target creature gets +2/+2 until end of turn. Untap it.
        this.getRightHalfCard().getSpellAbility().addEffect(new BoostTargetEffect(2, 2, Duration.EndOfTurn));
        this.getRightHalfCard().getSpellAbility().addEffect(new UntapTargetEffect().setText("Untap it"));
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent());

        finalizeCard();
    }

    private SilverflameSquire(final SilverflameSquire card) {
        super(card);
    }

    @Override
    public SilverflameSquire copy() {
        return new SilverflameSquire(this);
    }
}
