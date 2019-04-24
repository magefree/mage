

package mage.abilities.effects.common.combat;

import mage.constants.Duration;
import mage.abilities.Ability;
import mage.abilities.effects.RestrictionEffect;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CantBlockSourceEffect extends RestrictionEffect {

    public CantBlockSourceEffect(Duration duration) {
        super(duration);
        this.staticText = "{this} can't block";
        if (duration == Duration.EndOfTurn) {
            this.staticText += " this turn";
        }
    }

    public CantBlockSourceEffect(final CantBlockSourceEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(source.getSourceId());
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game) {
        return false;
    }

    @Override
    public CantBlockSourceEffect copy() {
        return new CantBlockSourceEffect(this);
    }

}
