package mage.cards.c;

import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.ReturnCardChosenFromGraveyardEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class CorpseChurn extends CardImpl {

    public CorpseChurn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Put the top three cards of your library into your graveyard, then you may return a creature card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new MillCardsControllerEffect(3));
        this.getSpellAbility().addEffect(new ReturnCardChosenFromGraveyardEffect(true,
                StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD, PutCards.HAND).concatBy(", then"));
    }

    private CorpseChurn(final CorpseChurn card) {
        super(card);
    }

    @Override
    public CorpseChurn copy() {
        return new CorpseChurn(this);
    }
}
