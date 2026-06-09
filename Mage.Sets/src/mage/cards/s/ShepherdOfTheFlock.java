package mage.cards.s;

import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShepherdOfTheFlock extends AdventureCard {

    public ShepherdOfTheFlock(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.PEASANT}, "{1}{W}",
                "Usher to Safety",
                new CardType[]{CardType.INSTANT}, "{W}");

        // Shepherd of the Flock
        this.getLeftHalfCard().setPT(3, 1);

        // Usher to Safety
        // Return target permanent you control to its owner's hand.
        this.getRightHalfCard().getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetControlledPermanent());

        finalizeCard();
    }

    private ShepherdOfTheFlock(final ShepherdOfTheFlock card) {
        super(card);
    }

    @Override
    public ShepherdOfTheFlock copy() {
        return new ShepherdOfTheFlock(this);
    }
}
