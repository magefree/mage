package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect.PutCards;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX
 */
public final class CommuneWithNature extends CardImpl {

    public CommuneWithNature(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");

        // Look at the top five cards of your library. You may reveal a creature card from among them and put it into your hand.
        // Put the rest on the bottom of your library in any order.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(
                5, 1, StaticFilters.FILTER_CARD_CREATURE_A, PutCards.HAND, PutCards.BOTTOM_ANY));
    }

    private CommuneWithNature(final CommuneWithNature card) {
        super(card);
    }

    @Override
    public CommuneWithNature copy() {
        return new CommuneWithNature(this);
    }
}
