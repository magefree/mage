package mage.cards.s;

import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SharedSummons extends CardImpl {

    public SharedSummons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{G}{G}");

        // Search your library for up to two creature cards with different names, reveal them, put them into your hand, then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(
                new SharedSummonsTarget(), true, true
        ));
    }

    private SharedSummons(final SharedSummons card) {
        super(card);
    }

    @Override
    public SharedSummons copy() {
        return new SharedSummons(this);
    }
}

class SharedSummonsTarget extends TargetCardInLibrary {

    private static final FilterCard filter2 = new FilterCreatureCard("creature cards with different names");

    SharedSummonsTarget() {
        super(0, 2, filter2);
    }

    private SharedSummonsTarget(final SharedSummonsTarget target) {
        super(target);
    }

    @Override
    public SharedSummonsTarget copy() {
        return new SharedSummonsTarget(this);
    }

    @Override
    public boolean canTarget(UUID id, Cards cards, Game game) {
        Card card = cards.get(id, game);
        if (card == null || !card.isCreature()) {
            return false;
        }
        return !this
                .getTargets()
                .stream()
                .map(uuid -> game.getCard(uuid))
                .anyMatch(c -> c != null && c.getName().equals(card.getName()));
    }
}
