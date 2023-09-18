package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 * @author TheElk801
 */
public class MillHalfLibraryTargetEffect extends OneShotEffect {

    private final boolean roundUp;

    public MillHalfLibraryTargetEffect(boolean roundUp) {
        super(Outcome.Benefit);
        this.roundUp = roundUp;
    }

    private MillHalfLibraryTargetEffect(final MillHalfLibraryTargetEffect effect) {
        super(effect);
        this.roundUp = effect.roundUp;
    }

    @Override
    public MillHalfLibraryTargetEffect copy() {
        return new MillHalfLibraryTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null) {
            return false;
        }
        int count = player.getLibrary().size();
        return player.millCards(count / 2 + (roundUp ? count % 2 : 0), source, game).size() > 0;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "target " + (mode.getTargets().isEmpty() ? "that player" : mode.getTargets().get(0).getTargetName()) +
                " mills half their library, rounded " + (roundUp ? "up" : "down");
    }
}
