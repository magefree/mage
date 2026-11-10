package mage.cards.p;

import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.BeamMeUpAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;

import java.util.UUID;

/**
 * @author muz
 */
public final class PatternBufferPreservation extends CardImpl {

    public PatternBufferPreservation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Look at the top two cards of your library. Put one of them into your hand and the other into your graveyard.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(2, 1, PutCards.HAND, PutCards.GRAVEYARD));

        // Beam me up {1}{B}
        this.addAbility(new BeamMeUpAbility(this, "{1}{B}"));
    }

    private PatternBufferPreservation(final PatternBufferPreservation card) {
        super(card);
    }

    @Override
    public PatternBufferPreservation copy() {
        return new PatternBufferPreservation(this);
    }
}
