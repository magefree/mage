
package mage.cards.p;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 *
 * @author fireshoes
 */
public final class PiecesOfThePuzzle extends CardImpl {

    private static final FilterCard FILTER = new FilterCard("up to two instant and/or sorcery cards");

    static {
        FILTER.add(Predicates.or(new CardTypePredicate(CardType.INSTANT), new CardTypePredicate(CardType.SORCERY)));
    }

    public PiecesOfThePuzzle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{U}");

        // Reveal the top five cards of your library. Put up to two instant and/or sorcery cards from among them into your hand and the rest into your graveyard.
        Effect effect = new LookLibraryAndPickControllerEffect(new StaticValue(5), false, new StaticValue(2), FILTER, Zone.GRAVEYARD, false, true, true, Zone.HAND, false);
        effect.setText("Reveal the top five cards of your library. Put up to two instant and/or sorcery cards from among them into your hand and the rest into your graveyard");
        this.getSpellAbility().addEffect(effect);
    }

    public PiecesOfThePuzzle(final PiecesOfThePuzzle card) {
        super(card);
    }

    @Override
    public PiecesOfThePuzzle copy() {
        return new PiecesOfThePuzzle(this);
    }
}
