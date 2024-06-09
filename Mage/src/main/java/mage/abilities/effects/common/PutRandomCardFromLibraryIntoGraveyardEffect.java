package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.util.RandomUtil;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author karapuzz14
 */
public class PutRandomCardFromLibraryIntoGraveyardEffect extends OneShotEffect {
    private FilterCard filter;

    /**
     * @param filter for selecting a card
     */
    public PutRandomCardFromLibraryIntoGraveyardEffect(FilterCard filter) {
        super(Outcome.Discard);
        this.filter = filter;
        this.staticText = "put a random " + filter.getMessage() + " from your library into your graveyard";
    }

    private PutRandomCardFromLibraryIntoGraveyardEffect(final PutRandomCardFromLibraryIntoGraveyardEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public PutRandomCardFromLibraryIntoGraveyardEffect copy() {
        return new PutRandomCardFromLibraryIntoGraveyardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());

        if (controller == null) {
            return false;
        }

        Set<Card> cards = controller.getLibrary()
                .getCards(game)
                .stream()
                .filter(card -> filter.match(card, getId(), source, game))
                .collect(Collectors.toSet());

        Card card = RandomUtil.randomFromCollection(cards);
        if (card == null) {
            return false;
        }
        game.informPlayers(controller.getLogName() + " puts a random " + filter.getMessage() + " from their library into their graveyard.");
        controller.moveCards(card, Zone.GRAVEYARD, source, game);

        return true;
    }

}
