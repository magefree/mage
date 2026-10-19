package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.util.RandomUtil;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author karapuzz14
 */
public class SeekCardEffect extends OneShotEffect {
    private final FilterCard filter;

    /**
     * @param filter for selecting a card
     */
    public SeekCardEffect(FilterCard filter) {
        super(Outcome.Benefit);
        this.filter = filter;
        this.staticText = "seek a " + filter.getMessage();
    }

    private SeekCardEffect(final SeekCardEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public SeekCardEffect copy() {
        return new SeekCardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());

        if (controller != null) {
            return controller.seekCard(filter, source, game);
        }

        return false;
    }

    /** Seeks without revealing the card or changing the order of the remaining library. */
    public static Card seekCard(Player player, FilterCard filter, Ability source, Game game) {
        List<Card> candidates = player.getLibrary().getCards(game).stream()
                .filter(card -> filter.match(card, player.getId(), source, game))
                .collect(Collectors.toList());
        Card card = RandomUtil.randomFromCollection(candidates);
        if (card == null) {
            return null;
        }
        game.informPlayers(player.getLogName() + " seeks a card from their library");
        player.moveCards(card, Zone.HAND, source, game);
        game.fireEvent(GameEvent.getEvent(GameEvent.EventType.SOUGHT, card.getId(), source, player.getId(), 1));
        return card;
    }

}
