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
public class SeekCardEffect extends OneShotEffect {
    private final FilterCard filter;
    private final Zone inZone;
    private final boolean tapped;

    public SeekCardEffect(FilterCard filter) {
        this(filter, Zone.HAND, false);
    }

    public SeekCardEffect(FilterCard filter, Zone inZone) {
        this(filter, inZone, false);
    }
    /**
     * @param filter for selecting a card
     */
    public SeekCardEffect(FilterCard filter, Zone inZone, boolean tapped) {
        super(Outcome.Benefit);
        this.filter = filter;
        this.inZone = inZone;
        this.tapped = tapped;
        this.staticText = "seek a " + filter.getMessage();
    }

    private SeekCardEffect(final SeekCardEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.inZone = effect.inZone;
        this.tapped = effect.tapped;
    }

    @Override
    public SeekCardEffect copy() {
        return new SeekCardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Set<Card> cards = controller.getLibrary()
                    .getCards(game)
                    .stream()
                    .filter(card -> filter.match(card, getId(), source, game))
                    .collect(Collectors.toSet());
            Card card = RandomUtil.randomFromCollection(cards);
            if (card == null) {
                return false;
            }
            game.informPlayers(controller.getLogName() + " seeks a card from their library");
            if (inZone == Zone.BATTLEFIELD) {
                controller.moveCards(card, inZone, source, game, tapped, false, false, null);
            } else {
                controller.moveCards(card, inZone, source, game);
            }
            return true;
        }
        return false;
    }

}
