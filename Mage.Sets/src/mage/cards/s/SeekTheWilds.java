package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class SeekTheWilds extends CardImpl {

    public SeekTheWilds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{G}");

        // Look at the top four cards of your library. You may reveal a creature or land card from among them and put it into your hand. Put the rest on the bottom of your library in any order.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(
                4, 1, StaticFilters.FILTER_CARD_CREATURE_OR_LAND, PutCards.HAND, PutCards.BOTTOM_ANY));
    }

    private SeekTheWilds(final SeekTheWilds card) {
        super(card);
    }

    @Override
    public SeekTheWilds copy() {
        return new SeekTheWilds(this);
    }
}
