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
public class PutOnTopOrBottomLibraryTargetEffect extends OneShotEffect {

    public PutOnTopOrBottomLibraryTargetEffect() {
        this("");
    }

    public PutOnTopOrBottomLibraryTargetEffect(String text) {
        super(Outcome.Benefit);
        staticText = text;
    }

    private PutOnTopOrBottomLibraryTargetEffect(final PutOnTopOrBottomLibraryTargetEffect effect) {
        super(effect);
    }

    @Override
    public PutOnTopOrBottomLibraryTargetEffect copy() {
        return new PutOnTopOrBottomLibraryTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getOwnerId(source.getFirstTarget()));
        if (player == null) {
            return false;
        }
        boolean onTop = player.chooseUse(
                Outcome.Detriment, "Put the targeted object on the top or bottom of your library?",
                null, "Top", "Bottom", source, game
        );
        return new PutOnLibraryTargetEffect(onTop).apply(game, source);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "the owner of target " + mode.getTargets().get(0).getTargetName() +
                " puts it on the top or bottom of their library";
    }
}
