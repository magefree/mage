package mage.abilities.effects.common.combat;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.RequirementEffect;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.AttackedThisTurnWatcher;

/**
 *
 * @author LevelX2
 */
public class AttacksIfAbleAllEffect extends RequirementEffect {

    private final FilterPermanent filter;
    private boolean eachCombat;

    public AttacksIfAbleAllEffect(FilterPermanent filter) {
        this(filter, Duration.WhileOnBattlefield);
    }

    public AttacksIfAbleAllEffect(FilterPermanent filter, Duration duration) {
        super(duration);
        this.filter = filter;
        if (this.duration == Duration.EndOfTurn) {
            eachCombat = false;
            staticText = filter.getMessage() + " attack this turn if able";
        } else {
            eachCombat = true;
            String durationString = this.duration.toString();
            if (durationString.isEmpty()) {
                staticText = filter.getMessage() + " attack each combat if able";
            } else {
                staticText = durationString + ", " + filter.getMessage() + " attack each combat if able";
            }
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
