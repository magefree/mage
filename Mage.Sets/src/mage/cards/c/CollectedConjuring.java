package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CollectedConjuring extends CardImpl {

    public CollectedConjuring(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}{R}");

        // Exile the top six cards of your library. You may cast up to two sorcery 
        // cards with converted mana costs 3 or less from among them without paying 
        // their mana cost. Put the exiled cards not cast this way on the bottom 
        // of your library in a random order.
        this.getSpellAbility().addEffect(new CollectedConjuringEffect());
    }

    private CollectedConjuring(final CollectedConjuring card) {
        super(card);
    }

    @Override
    public CollectedConjuring copy() {
        return new CollectedConjuring(this);
    }
}

class CollectedConjuringEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("sorcery cards with mana value 3 or less");

    static {
        filter.add(CardType.SORCERY.getPredicate());
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    CollectedConjuringEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "exile the top six cards of your library. You may cast up to two sorcery spells " +
                "with mana value 3 or less from among them without paying their mana costs. " +
                "Put the exiled cards not cast this way on the bottom of your library in a random order";
    }

    private CollectedConjuringEffect(final CollectedConjuringEffect effect) {
        super(effect);
    }

    @Override
    public CollectedConjuringEffect copy() {
        return new CollectedConjuringEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 6));
        controller.moveCards(cards, Zone.EXILED, source, game);
        CardUtil.castMultipleWithAttributeForFree(controller, source, game, cards, filter, 2);
        controller.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}
