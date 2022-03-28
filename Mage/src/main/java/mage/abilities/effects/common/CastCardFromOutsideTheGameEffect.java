package mage.abilities.effects.common;

import java.util.Set;
import mage.ApprovingObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 * @author magenoxx_at_gmail.com
 */
public class CastCardFromOutsideTheGameEffect extends OneShotEffect {

    private static final String choiceText = "Cast a card from outside the game?";

    private final FilterCard filterCard;

    public CastCardFromOutsideTheGameEffect(FilterCard filter, String ruleText) {
        super(Outcome.Benefit);
        this.staticText = ruleText;
        this.filterCard = filter;
    }

    public CastCardFromOutsideTheGameEffect(final CastCardFromOutsideTheGameEffect effect) {
        super(effect);
        filterCard = effect.filterCard;
    }

    @Override
    public CastCardFromOutsideTheGameEffect copy() {
        return new CastCardFromOutsideTheGameEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        while (player.chooseUse(Outcome.Benefit, choiceText, source, game)) {
            Cards cards = player.getSideboard();
            if (cards.isEmpty()) {
                if (!game.isSimulation()) {
                    game.informPlayer(player, "You have no cards outside the game.");
                }
                return false;
            }

            Set<Card> filtered = cards.getCards(filterCard, source.getControllerId(), source, game);
            if (filtered.isEmpty()) {
                if (!game.isSimulation()) {
                    game.informPlayer(player, "You have no " + filterCard.getMessage() + " outside the game.");
                }
                return false;
            }

            Cards filteredCards = new CardsImpl();
            for (Card card : filtered) {
                filteredCards.add(card.getId());
            }

            TargetCard target = new TargetCard(Zone.OUTSIDE, filterCard);
            if (player.choose(Outcome.Benefit, filteredCards, target, game)) {
                Card card = player.getSideboard().get(target.getFirstTarget(), game);
                if (card != null) {
                    game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
                    player.cast(player.chooseAbilityForCast(card, game, true), game, true, new ApprovingObject(source, game));
                    game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
                }
            }
        }

        return true;
    }

}
