package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.LookLibraryControllerEffect.PutCards;
import mage.abilities.effects.common.RevealLibraryPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;

/**
 *
 * @author awjackson
 */
public final class CommuneWithTheGods extends CardImpl {

    private static final FilterCard filter = new FilterCard("a creature or enchantment card");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()
        ));
    }

    public CommuneWithTheGods(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Reveal the top five cards of your library. You may put a creature or enchantment card from among them into your hand.
        // Put the rest into your graveyard.
        this.getSpellAbility().addEffect(new RevealLibraryPickControllerEffect(5, 1, filter, PutCards.HAND, PutCards.GRAVEYARD));
    }

    private CommuneWithTheGods(final CommuneWithTheGods card) {
        super(card);
    }

    @Override
    public CommuneWithTheGods copy() {
        return new CommuneWithTheGods(this);
    }
}
