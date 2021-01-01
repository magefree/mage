package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author North
 */
public class CantBeBlockedSourceEffect extends RestrictionEffect {

    public CantBeBlockedSourceEffect() {
        this(Duration.WhileOnBattlefield);
    }

    public CantBeBlockedSourceEffect(Duration duration) {
        super(duration);
        this.staticText = "{this} can't be blocked";
        if (this.duration == Duration.EndOfTurn) {
            this.staticText += " this turn";
        }
    }

    public CantBeBlockedSourceEffect(CantBeBlockedSourceEffect effect) {
        super(effect);
    }

    @Override
    public CantBeBlockedSourceEffect copy() {
        return new CantBeBlockedSourceEffect(this);
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(source.getSourceId());
    }
}
