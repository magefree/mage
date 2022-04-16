package mage.cards.u;

import java.util.UUID;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect.PutCards;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;

/**
 *
 * @author LevelX2
 */


public final class UncoveredClues extends CardImpl {

    private static final FilterCard filter = new FilterInstantOrSorceryCard("instant and/or sorcery cards");

    public UncoveredClues(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{U}");

        // Look at the top four cards of your library. You may reveal up to two instant and/or sorcery cards from among them and put the revealed cards into your hand. Put the rest on the bottom of your library in any order.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(4, 2, filter, PutCards.HAND, PutCards.BOTTOM_ANY));
    }

    private UncoveredClues(final UncoveredClues card) {
        super(card);
    }

    @Override
    public UncoveredClues copy() {
        return new UncoveredClues(this);
    }
}
