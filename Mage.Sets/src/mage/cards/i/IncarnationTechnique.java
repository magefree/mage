package mage.cards.i;

import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.ReturnCardChosenFromGraveyardEffect;
import mage.abilities.keyword.DemonstrateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IncarnationTechnique extends CardImpl {

    public IncarnationTechnique(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}");

        // Demonstrate
        this.addAbility(new DemonstrateAbility());

        // Mill five cards, then return a creature card from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new MillCardsControllerEffect(5));
        this.getSpellAbility().addEffect(new ReturnCardChosenFromGraveyardEffect(false,
                StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD, PutCards.BATTLEFIELD).concatBy(", then"));    }

    private IncarnationTechnique(final IncarnationTechnique card) {
        super(card);
    }

    @Override
    public IncarnationTechnique copy() {
        return new IncarnationTechnique(this);
    }
}
