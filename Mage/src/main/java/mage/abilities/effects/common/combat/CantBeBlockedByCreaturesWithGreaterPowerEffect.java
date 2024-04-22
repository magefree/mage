package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author LevelX2
 */
public class CantBeBlockedByCreaturesWithGreaterPowerEffect extends RestrictionEffect {

    public CantBeBlockedByCreaturesWithGreaterPowerEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "Creatures with power greater than {this}'s power can't block it";
    }

    protected CantBeBlockedByCreaturesWithGreaterPowerEffect(final CantBeBlockedByCreaturesWithGreaterPowerEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(source.getSourceId());
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return blocker.getPower().getValue() <= attacker.getPower().getValue();
    }

    @Override
    public CantBeBlockedByCreaturesWithGreaterPowerEffect copy() {
        return new CantBeBlockedByCreaturesWithGreaterPowerEffect(this);
    }
}
