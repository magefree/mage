package mage.cards.m;

import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MerfolkSecretkeeper extends AdventureCard {

    public MerfolkSecretkeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.MERFOLK, SubType.WIZARD}, "{U}",
                "Venture Deeper",
                new CardType[]{CardType.SORCERY}, "{U}");

        // Merfolk Secretkeeper
        this.getLeftHalfCard().setPT(0, 4);

        // Venture Deeper
        // Target player puts the top four cards of their library into their graveyard.
        this.getRightHalfCard().getSpellAbility().addEffect(new MillCardsTargetEffect(4));
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetPlayer());

        finalizeCard();
    }

    private MerfolkSecretkeeper(final MerfolkSecretkeeper card) {
        super(card);
    }

    @Override
    public MerfolkSecretkeeper copy() {
        return new MerfolkSecretkeeper(this);
    }
}
