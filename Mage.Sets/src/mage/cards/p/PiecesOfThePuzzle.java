package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.RevealLibraryPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;

/**
 *
 * @author fireshoes
 */
public final class PiecesOfThePuzzle extends CardImpl {

    private static final FilterCard filter = new FilterCard("instant and/or sorcery cards");

    static {
        filter.add(Predicates.or(CardType.INSTANT.getPredicate(), CardType.SORCERY.getPredicate()));
    }

    public PiecesOfThePuzzle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{U}");

        // Reveal the top five cards of your library. Put up to two instant and/or sorcery cards from among them into your hand and the rest into your graveyard.
        Effect effect = new RevealLibraryPickControllerEffect(5, 2, filter, PutCards.HAND, PutCards.GRAVEYARD, false);
        effect.setText("reveal the top five cards of your library. " +
                "Put up to two instant and/or sorcery cards from among them into your hand and the rest into your graveyard");
        this.getSpellAbility().addEffect(effect);
    }

    private PiecesOfThePuzzle(final PiecesOfThePuzzle card) {
        super(card);
    }

    @Override
    public PiecesOfThePuzzle copy() {
        return new PiecesOfThePuzzle(this);
    }
}
