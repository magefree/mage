package mage.abilities.effects;

import mage.abilities.Ability;
import mage.game.Game;
import mage.target.Target;
import mage.target.Targets;
import mage.target.targetadjustment.GenericTargetAdjuster;
import mage.target.targetpointer.TargetPointer;

/**
 * @author notgreat
 */
public class OneShotNonTargetEffect extends OneShotEffect {
    OneShotEffect effect;
    Target notTarget;
    GenericTargetAdjuster adjuster;

    public OneShotNonTargetEffect(OneShotEffect effect, Target notTarget) {
        this(effect, notTarget, null);
    }

    public OneShotNonTargetEffect(OneShotEffect effect, Target notTarget, GenericTargetAdjuster adjuster) {
        super(effect.outcome);
        this.effect = effect;
        this.notTarget = notTarget;
        this.notTarget.withNotTarget(true);
        this.adjuster = adjuster;
        if (effect.staticText == null || effect.staticText.equals("")){
            throw new IllegalArgumentException("Effect must use static text");
        }
        this.setText(effect.staticText);
    }

    private OneShotNonTargetEffect(OneShotNonTargetEffect eff) {
        super(eff);
        this.effect = eff.effect.copy();
        this.notTarget = eff.notTarget.copy();
        this.adjuster = eff.adjuster;
    }

    public OneShotNonTargetEffect copy() {
        return new OneShotNonTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Target target = notTarget.copy();
        Ability modifiedSource = source.copy();
        modifiedSource.addTarget(target);
        if (adjuster != null) {
            if (adjuster.blueprintTarget == null) {
                adjuster.addDefaultTargets(modifiedSource);
            }
            adjuster.adjustTargets(modifiedSource, game);
            Targets adjustedTargets = modifiedSource.getTargets();
            if (adjustedTargets.isEmpty()) { //if adjusted to have no targets, apply anyway
                return effect.apply(game, modifiedSource);
            }
            target = modifiedSource.getTargets().get(0);
        }

        if (target.canChoose(source.getControllerId(), modifiedSource, game)) {
            target.choose(this.outcome, source.getControllerId(), modifiedSource, game);
            return effect.apply(game, modifiedSource);
        }
        return false;
    }

    @Override
    public OneShotEffect setTargetPointer(TargetPointer targetPointer) {
        if (targetPointer == null) {
            return null;
        }
        effect.setTargetPointer(targetPointer);
        return super.setTargetPointer(targetPointer);
    }

    @Override
    public void setValue(String key, Object value) {
        effect.setValue(key, value);
        super.setValue(key, value);
    }
}
