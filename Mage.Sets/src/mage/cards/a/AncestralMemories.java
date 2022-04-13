package mage.cards.a;

import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect.PutCards;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author Quercitron
 */
public final class AncestralMemories extends CardImpl {

    public AncestralMemories(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}{U}{U}");

        // Look at the top seven cards of your library. Put two of them into your hand and the rest into your graveyard.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(7, 2, PutCards.HAND, PutCards.GRAVEYARD));
    }

    private AncestralMemories(final AncestralMemories card) {
        super(card);
    }

    @Override
    public AncestralMemories copy() {
        return new AncestralMemories(this);
    }
}
