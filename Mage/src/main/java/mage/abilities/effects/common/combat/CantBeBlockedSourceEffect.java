package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.EvasionEffect;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author North
 */
public class CantBeBlockedSourceEffect extends EvasionEffect {

    public CantBeBlockedSourceEffect() {
        this(Duration.WhileOnBattlefield);
    }

    public CantBeBlockedSourceEffect(Duration duration) {
        super(duration);
        this.staticCantBeBlockedMessage = "can't be blocked"
            + (this.duration == Duration.EndOfTurn ? " this turn" : "");
        staticText = "{this} " + this.staticCantBeBlockedMessage;
    }

    public CantBeBlockedSourceEffect(CantBeBlockedSourceEffect effect) {
        super(effect);
    }

    @Override
    public CantBeBlockedSourceEffect copy() {
        return new CantBeBlockedSourceEffect(this);
    }

    @Override
    public boolean cantBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return true;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(source.getSourceId());
    }
}
