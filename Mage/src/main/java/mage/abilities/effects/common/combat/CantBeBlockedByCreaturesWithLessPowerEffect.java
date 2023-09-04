package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.EvasionEffect;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author North
 */
public class CantBeBlockedByCreaturesWithLessPowerEffect extends EvasionEffect {

    public CantBeBlockedByCreaturesWithLessPowerEffect() {
        super(Duration.WhileOnBattlefield);
        this.staticCantBeBlockedMessage = "can't be blocked by creatures with lesser power";
        staticText = "Creatures with power less than {this}'s power can't block it";
    }

    protected CantBeBlockedByCreaturesWithLessPowerEffect(final CantBeBlockedByCreaturesWithLessPowerEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(source.getSourceId());
    }

    @Override
    public boolean cantBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return (blocker.getPower().getValue() < attacker.getPower().getValue());
    }

    @Override
    public CantBeBlockedByCreaturesWithLessPowerEffect copy() {
        return new CantBeBlockedByCreaturesWithLessPowerEffect(this);
    }
}
