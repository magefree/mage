package mage.cards.c;

import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Cowabunga extends CardImpl {

    private static final FilterCard filter = new FilterCard("Mutant, Ninja, Turtle, or land card");

    static {
        filter.add(Predicates.or(
                SubType.MUTANT.getPredicate(),
                SubType.NINJA.getPredicate(),
                SubType.TURTLE.getPredicate(),
                CardType.LAND.getPredicate()
        ));
    }

    public Cowabunga(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");

        // Look at the top four cards of your library. You may reveal a Mutant, Ninja, Turtle, or land card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(
                4, 1, filter, PutCards.HAND, PutCards.BOTTOM_RANDOM
        ));
    }

    private Cowabunga(final Cowabunga card) {
        super(card);
    }

    @Override
    public Cowabunga copy() {
        return new Cowabunga(this);
    }
}
