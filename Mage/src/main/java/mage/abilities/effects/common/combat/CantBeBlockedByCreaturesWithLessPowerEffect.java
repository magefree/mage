
package mage.abilities.effects.common.combat;

import mage.constants.Duration;
import mage.abilities.Ability;
import mage.abilities.effects.RestrictionEffect;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author North
 */
public class CantBeBlockedByCreaturesWithLessPowerEffect extends RestrictionEffect {

    public CantBeBlockedByCreaturesWithLessPowerEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "Creatures with power less than {this}'s power can't block it";
    }

    public CantBeBlockedByCreaturesWithLessPowerEffect(final CantBeBlockedByCreaturesWithLessPowerEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(source.getSourceId());
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game) {
        return (blocker.getPower().getValue() >= attacker.getPower().getValue());
    }

    @Override
    public CantBeBlockedByCreaturesWithLessPowerEffect copy() {
        return new CantBeBlockedByCreaturesWithLessPowerEffect(this);
    }
}
