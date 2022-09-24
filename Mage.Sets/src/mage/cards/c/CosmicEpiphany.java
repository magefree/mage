package mage.cards.c;

import java.util.UUID;

import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author weirddan455
 */
public final class CosmicEpiphany extends CardImpl {

    public CosmicEpiphany(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}{U}");

        // Draw cards equal to the number of instant and sorcery cards in your graveyard.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_INSTANT_AND_SORCERY)));
    }

    private CosmicEpiphany(final CosmicEpiphany card) {
        super(card);
    }

    @Override
    public CosmicEpiphany copy() {
        return new CosmicEpiphany(this);
    }
}
