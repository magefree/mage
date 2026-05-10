package mage.cards.d;

import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.ReturnCardChosenFromGraveyardEffect;
import mage.abilities.keyword.CasualtyAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DigUpTheBody extends CardImpl {

    public DigUpTheBody(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Casualty 1
        this.addAbility(new CasualtyAbility(1));

        // Mill two cards, then you may return a creature card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new MillCardsControllerEffect(2));
        this.getSpellAbility().addEffect(new ReturnCardChosenFromGraveyardEffect(true,
                StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD, PutCards.HAND).concatBy(", then"));    }

    private DigUpTheBody(final DigUpTheBody card) {
        super(card);
    }

    @Override
    public DigUpTheBody copy() {
        return new DigUpTheBody(this);
    }
}
