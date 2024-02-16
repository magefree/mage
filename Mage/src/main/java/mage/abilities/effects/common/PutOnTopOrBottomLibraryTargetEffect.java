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

    private final boolean textOwnerOf;

    public PutOnTopOrBottomLibraryTargetEffect(boolean textOwnerOf) {
        super(Outcome.ReturnToHand);
        this.textOwnerOf = textOwnerOf;
    }

    private PutOnTopOrBottomLibraryTargetEffect(final PutOnTopOrBottomLibraryTargetEffect effect) {
        super(effect);
        this.textOwnerOf = effect.textOwnerOf;
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
        String targetText = getTargetPointer().describeTargets(mode.getTargets(), "that permanent");
        return (textOwnerOf ? "the owner of " + targetText : targetText + "'s owner") +
                " puts it on the top or bottom of their library";
    }
}
