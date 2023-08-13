
package mage.cards.m;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author LevelX2
 */
public final class ManifoldInsights extends CardImpl {

    public ManifoldInsights(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // Reveal the top ten cards of your library. Starting with the next opponent in turn order, each opponent chooses a different nonland card from among them. Put the chosen cards into your hand and the rest on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(new ManifoldInsightsEffect());
    }

    private ManifoldInsights(final ManifoldInsights card) {
        super(card);
    }

    @Override
    public ManifoldInsights copy() {
        return new ManifoldInsights(this);
    }
}

class ManifoldInsightsEffect extends OneShotEffect {

    public ManifoldInsightsEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Reveal the top ten cards of your library. Starting with the next opponent in turn order, each opponent chooses a different nonland card from among them. Put the chosen cards into your hand and the rest on the bottom of your library in a random order";
    }

    public ManifoldInsightsEffect(final ManifoldInsightsEffect effect) {
        super(effect);
    }

    @Override
    public ManifoldInsightsEffect copy() {
        return new ManifoldInsightsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            Cards topLib = new CardsImpl();
            topLib.addAllCards(controller.getLibrary().getTopCards(game, 10));
            controller.revealCards(sourceObject.getIdName(), topLib, game);
            Cards chosenCards = new CardsImpl();
            if (game.getOpponents(controller.getId()).size() >= topLib.getCards(StaticFilters.FILTER_CARD_NON_LAND, game).size()) {
                chosenCards.addAllCards(topLib.getCards(StaticFilters.FILTER_CARD_NON_LAND, game));
                topLib.removeAll(chosenCards);
            } else if (!topLib.getCards(StaticFilters.FILTER_CARD_NON_LAND, game).isEmpty()) {
                for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                    if (controller.hasOpponent(playerId, game)) {
                        Player opponent = game.getPlayer(playerId);
                        if (opponent != null && !topLib.getCards(StaticFilters.FILTER_CARD_NON_LAND, game).isEmpty()) {
                            TargetCard target = new TargetCard(Zone.LIBRARY, StaticFilters.FILTER_CARD_NON_LAND);
                            if (opponent.choose(Outcome.Detriment, topLib, target, source, game)) {
                                Card card = game.getCard(target.getFirstTarget());
                                if (card != null) {
                                    topLib.remove(card);
                                    chosenCards.add(card);
                                }
                            }
                        }
                    }
                }
            }
            controller.moveCards(chosenCards, Zone.HAND, source, game);
            controller.putCardsOnBottomOfLibrary(topLib, game, source, false);
            return true;
        }
        return false;
    }
}
