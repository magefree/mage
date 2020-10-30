package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AdventureAwaits extends CardImpl {

    public AdventureAwaits(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Look at the top five cards of your library. You may reveal a creature card from among them and put it into your hand. Put the rest on the bottom of your library in a random order. If you don't put a card into your hand this way, draw a card.
        this.getSpellAbility().addEffect(new AdventureAwaitsEffect());
    }

    private AdventureAwaits(final AdventureAwaits card) {
        super(card);
    }

    @Override
    public AdventureAwaits copy() {
        return new AdventureAwaits(this);
    }
}

class AdventureAwaitsEffect extends OneShotEffect {

    AdventureAwaitsEffect() {
        super(Outcome.Benefit);
        staticText = "Look at the top five cards of your library. " +
                "You may reveal a creature card from among them and put it into your hand. " +
                "Put the rest on the bottom of your library in a random order. " +
                "If you didn't put a card into your hand this way, draw a card.";
    }

    private AdventureAwaitsEffect(final AdventureAwaitsEffect effect) {
        super(effect);
    }

    @Override
    public AdventureAwaitsEffect copy() {
        return new AdventureAwaitsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 5));
        TargetCard target = new TargetCardInLibrary(0, 1, StaticFilters.FILTER_CARD_CREATURE);
        player.choose(outcome, cards, target, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card != null && player.chooseUse(outcome, "Put " + card.getName() + " into your hand?",
                "Otherwise draw a card", "Put into hand", "Draw a card", source, game
        )) {
            player.moveCards(card, Zone.HAND, source, game);
            cards.remove(card);
            player.putCardsOnBottomOfLibrary(cards, game, source, false);
        } else {
            player.putCardsOnBottomOfLibrary(cards, game, source, false);
            player.drawCards(1, source.getSourceId(), game);
        }
        return true;
    }
}
