package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.LookLibraryControllerEffect.PutCards;
import mage.abilities.effects.common.RevealLibraryPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author awjackson
 */
public final class ScoutTheBorders extends CardImpl {

    public ScoutTheBorders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Reveal the top five cards of your library. You may put a creature or land card from among them into your hand. Put the rest into your graveyard.
        this.getSpellAbility().addEffect(new RevealLibraryPickControllerEffect(
                5, 1, StaticFilters.FILTER_CARD_CREATURE_OR_LAND, PutCards.HAND, PutCards.GRAVEYARD));
    }

    private ScoutTheBorders(final ScoutTheBorders card) {
        super(card);
    }

    @Override
    public ScoutTheBorders copy() {
        return new ScoutTheBorders(this);
    }
}
