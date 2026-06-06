package mage.cards.e;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetArtifactPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EmberethShieldbreaker extends AdventureCard {

    public EmberethShieldbreaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.KNIGHT}, "{1}{R}",
                "Battle Display",
                new CardType[]{CardType.SORCERY}, "{R}");

        // Embereth Shieldbreaker
        this.getLeftHalfCard().setPT(2, 1);

        // Battle Display
        // Destroy target artifact.
        this.getRightHalfCard().getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetArtifactPermanent());

        finalizeCard();
    }

    private EmberethShieldbreaker(final EmberethShieldbreaker card) {
        super(card);
    }

    @Override
    public EmberethShieldbreaker copy() {
        return new EmberethShieldbreaker(this);
    }
}
