package mage.cards.g;

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
public final class GarenbrigCarver extends AdventureCard {

    public GarenbrigCarver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WARRIOR}, "{3}{G}",
                "Shield's Might",
                new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Garenbrig Carver
        this.getLeftHalfCard().setPT(3, 2);

        // Shield's Might
        // Target creature gets +2/+2 until end of turn.
        this.getRightHalfCard().getSpellAbility().addEffect(new BoostTargetEffect(2, 2, Duration.EndOfTurn));
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent());

        finalizeCard();
    }

    private GarenbrigCarver(final GarenbrigCarver card) {
        super(card);
    }

    @Override
    public GarenbrigCarver copy() {
        return new GarenbrigCarver(this);
    }
}
