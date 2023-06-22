package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author TheElk801
 */
public class PutIntoLibraryNFromTopTargetEffect extends OneShotEffect {

    private final int position;

    public PutIntoLibraryNFromTopTargetEffect(int position) {
        super(Outcome.Benefit);
        this.position = position;
    }

    private PutIntoLibraryNFromTopTargetEffect(final PutIntoLibraryNFromTopTargetEffect effect) {
        super(effect);
        this.position = effect.position;
    }

    @Override
    public PutIntoLibraryNFromTopTargetEffect copy() {
        return new PutIntoLibraryNFromTopTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        return player != null && permanent != null
                && player.putCardOnTopXOfLibrary(permanent, game, source, position, true);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "put " + getTargetPointer().describeTargets(mode.getTargets(), "") +
                " into its owner's library " + CardUtil.numberToOrdinalText(position) + " from the top";
    }
}
