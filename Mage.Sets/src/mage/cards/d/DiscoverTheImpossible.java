package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DiscoverTheImpossible extends CardImpl {

    public DiscoverTheImpossible(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Look at the top five cards of your library. Exile one of them face down and put the rest on the bottom of your library in a random order. You may cast the exiled card without paying its mana cost if it's an instant spell with mana value 2 or less. If you don't, put that card into your hand.
        this.getSpellAbility().addEffect(new DiscoverTheImpossibleEffect());
    }

    private DiscoverTheImpossible(final DiscoverTheImpossible card) {
        super(card);
    }

    @Override
    public DiscoverTheImpossible copy() {
        return new DiscoverTheImpossible(this);
    }
}

class DiscoverTheImpossibleEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(CardType.INSTANT.getPredicate());
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 3));
    }

    DiscoverTheImpossibleEffect() {
        super(Outcome.PlayForFree);
        staticText = "look at the top five cards of your library. Exile one of them face down " +
                "and put the rest on the bottom of your library in a random order. " +
                "You may cast the exiled card without paying its mana cost if it's an instant spell " +
                "with mana value 2 or less. If you don't, put that card into your hand";
    }

    private DiscoverTheImpossibleEffect(final DiscoverTheImpossibleEffect effect) {
        super(effect);
    }

    @Override
    public DiscoverTheImpossibleEffect copy() {
        return new DiscoverTheImpossibleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 5));
        if (cards.isEmpty()) {
            return false;
        }
        TargetCard target = new TargetCardInLibrary();
        player.choose(outcome, cards, target, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            player.putCardsOnBottomOfLibrary(card, game, source, false);
            return true;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        card.setFaceDown(true, game);
        cards.retainZone(Zone.LIBRARY, game);
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        return CardUtil.castSpellWithAttributesForFree(player, source, game, new CardsImpl(card), filter)
                || player.moveCards(card, Zone.HAND, source, game);
    }
}
