package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;

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

}
