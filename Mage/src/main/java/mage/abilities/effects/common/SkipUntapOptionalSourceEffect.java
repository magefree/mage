
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class SkipUntapOptionalSourceEffect extends RestrictionEffect {

    public SkipUntapOptionalSourceEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "You may choose not to untap {this} during your untap step";
    }

    public SkipUntapOptionalSourceEffect(final SkipUntapOptionalSourceEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(source.getSourceId())
                && permanent.isControlledBy(game.getActivePlayerId()) && // your untap step
                permanent.isTapped();
    }

    @Override
    public boolean canBeUntapped(Permanent permanent, Ability source, Game game) {
        Player player = game.getPlayer(permanent.getControllerId());
        return player != null && player.chooseUse(Outcome.Benefit, "Untap " + permanent.getLogName() + '?', source, game);
    }

    @Override
    public SkipUntapOptionalSourceEffect copy() {
        return new SkipUntapOptionalSourceEffect(this);
    }
}
