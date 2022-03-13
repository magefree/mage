package mage.abilities.effects.common.combat;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.RequirementEffect;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.AttackedThisTurnWatcher;

/**
 *
 * @author LevelX2
 */
public class AttacksIfAbleAllEffect extends RequirementEffect {

    private final FilterCreaturePermanent filter;

    public AttacksIfAbleAllEffect(FilterCreaturePermanent filter) {
        this(filter, Duration.WhileOnBattlefield);
    }

    boolean eachCombat;

    public AttacksIfAbleAllEffect(FilterCreaturePermanent filter, Duration duration) {
        this(filter, duration, false);
    }

    public AttacksIfAbleAllEffect(FilterCreaturePermanent filter, Duration duration, boolean eachCombat) {
        super(duration);
        this.filter = filter;
        this.eachCombat = eachCombat;
        if (this.duration == Duration.EndOfTurn) {
            staticText = filter.getMessage() + " attack " + (eachCombat ? "each combat" : "this turn") + " if able";
        } else {
            staticText = filter.getMessage() + " attack each " + (eachCombat ? "combat" : "turn") + " if able";
        }
    }

    public AttacksIfAbleAllEffect(final AttacksIfAbleAllEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.eachCombat = effect.eachCombat;
    }

    @Override
    public AttacksIfAbleAllEffect copy() {
        return new AttacksIfAbleAllEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (filter.match(permanent, source.getControllerId(), source, game)) {
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
