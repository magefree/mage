package mage.cards.c;

import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect.PutCards;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CommuneWithSpirits extends CardImpl {

    private static final FilterCard filter = new FilterCard("enchantment or land card");

    static {
        filter.add(Predicates.or(
                CardType.ENCHANTMENT.getPredicate(),
                CardType.LAND.getPredicate()
        ));
    }

    public CommuneWithSpirits(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");

        // Look at the top four cards of your library. You may reveal an enchantment or land card from among them and put it into your hand.
        // Put the rest on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(4, 1, filter, PutCards.HAND, PutCards.BOTTOM_RANDOM));
    }

    private CommuneWithSpirits(final CommuneWithSpirits card) {
        super(card);
    }

    @Override
    public CommuneWithSpirits copy() {
        return new CommuneWithSpirits(this);
    }
}
