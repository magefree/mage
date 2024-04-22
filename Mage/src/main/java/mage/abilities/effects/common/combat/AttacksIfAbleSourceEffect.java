
package mage.abilities.effects.common.combat;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.RequirementEffect;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.AttackedThisTurnWatcher;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class AttacksIfAbleSourceEffect extends RequirementEffect {

    boolean eachCombat;

    public AttacksIfAbleSourceEffect(Duration duration) {
        this(duration, true);
    }

    public AttacksIfAbleSourceEffect(Duration duration, boolean eachCombat) {
        super(duration);
        this.eachCombat = eachCombat;
        if (this.duration == Duration.EndOfTurn) {
            staticText = "{this} attacks " + (eachCombat ? "each combat" : "this combat") + " if able";
        } else {
            staticText = "{this} attacks each " + (eachCombat ? "combat" : "turn") + " if able";
        }
    }

    protected AttacksIfAbleSourceEffect(final AttacksIfAbleSourceEffect effect) {
        super(effect);
        this.eachCombat = effect.eachCombat;
    }

    @Override
    public AttacksIfAbleSourceEffect copy() {
        return new AttacksIfAbleSourceEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(source.getSourceId())) {
            if (eachCombat) {
                return true;
            }
            AttackedThisTurnWatcher watcher = game.getState().getWatcher(AttackedThisTurnWatcher.class);
            return watcher != null && !watcher.getAttackedThisTurnCreatures().contains(new MageObjectReference(permanent, game));
        }
        return false;
    }

    @Override
    public boolean mustAttack(Game game) {
        return true;
    }

    @Override
    public boolean mustBlock(Game game) {
        return false;
    }

}
