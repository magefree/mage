package mage.cards.r;

import mage.abilities.effects.common.DoIfClashWonEffect;
import mage.abilities.effects.common.ReturnToHandSpellEffect;
import mage.abilities.effects.common.RevealCardsFromLibraryUntilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class RecrossThePaths extends CardImpl {

    public RecrossThePaths(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Reveal cards from the top of your library until you reveal a land card. Put that card onto the battlefield and the rest on the bottom of your library in any order.
        this.getSpellAbility().addEffect(new RevealCardsFromLibraryUntilEffect(
                StaticFilters.FILTER_CARD_LAND, PutCards.BATTLEFIELD, PutCards.BOTTOM_ANY
        ));

        // Clash with an opponent. If you win, return Recross the Paths to its owner's hand.
        this.getSpellAbility().addEffect(new DoIfClashWonEffect(ReturnToHandSpellEffect.getInstance()));
    }

    private RecrossThePaths(final RecrossThePaths card) {
        super(card);
    }

    @Override
    public RecrossThePaths copy() {
        return new RecrossThePaths(this);
    }
}
