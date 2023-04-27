package mage.cards.l;

import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author North
 */
public final class LeadTheStampede extends CardImpl {

    public LeadTheStampede(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Look at the top five cards of your library. You may reveal any number of creature cards from among them and put the revealed cards into your hand. Put the rest on the bottom of your library in any order.
        Effect effect = new LookLibraryAndPickControllerEffect(
                5, Integer.MAX_VALUE, StaticFilters.FILTER_CARD_CREATURES, PutCards.HAND, PutCards.BOTTOM_ANY);
        effect.setText("look at the top five cards of your library. " +
                "You may reveal any number of creature cards from among them and put the revealed cards into your hand. " +
                "Put the rest on the bottom of your library in any order");
        this.getSpellAbility().addEffect(effect);
    }

    private LeadTheStampede(final LeadTheStampede card) {
        super(card);
    }

    @Override
    public LeadTheStampede copy() {
        return new LeadTheStampede(this);
    }
}
