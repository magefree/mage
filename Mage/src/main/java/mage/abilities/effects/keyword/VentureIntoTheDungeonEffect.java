package mage.abilities.effects.keyword;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 * @author TheElk801
 */
public class VentureIntoTheDungeonEffect extends OneShotEffect {

    public VentureIntoTheDungeonEffect() {
        super(Outcome.Benefit);
        staticText = "venture into the dungeon. <i>(Enter the first room or advance to the next room.)</i>";
    }

    private VentureIntoTheDungeonEffect(final VentureIntoTheDungeonEffect effect) {
        super(effect);
    }

    @Override
    public VentureIntoTheDungeonEffect copy() {
        return new VentureIntoTheDungeonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.ventureIntoDungeon(source, game);
        return true;
    }
}
