package mage.abilities.effects;

import mage.abilities.Ability;
import mage.game.Game;
import mage.target.Target;
import mage.target.targetadjustment.TargetAdjuster;
import mage.target.targetpointer.TargetPointer;

/**
 * @author notgreat
 */
public class OneShotNonTargetEffect extends OneShotEffect {
    OneShotEffect effect;
    Target notTarget;
    TargetAdjuster adjuster;

    public OneShotNonTargetEffect(OneShotEffect effect, Target notTarget) {
        this(effect, notTarget, null);
    }

    public OneShotNonTargetEffect(OneShotEffect effect, Target notTarget, TargetAdjuster adjuster) {
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
        boolean result = false;
        Target target = notTarget.copy();
        if (source.getTargetAdjuster() != null || !source.getTargets().isEmpty()){
            throw new IllegalStateException("source ability already has target but is using OneShotNonTargetEffect");
        }
        source.addTarget(target);
        if (adjuster != null) {
            adjuster.clearDefaultTargets();
            source.setTargetAdjuster(adjuster);
            source.adjustTargets(game);
            source.setTargetAdjuster(null);
        }

        if (source.getTargets().choose(outcome, source.getControllerId(), source.getId(), source, game)) {
            result = effect.apply(game, source);
        }
        source.getTargets().clear();
        return result;
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
    public OneShotEffect withTargetDescription(String target) {
        effect.withTargetDescription(target);
        return super.withTargetDescription(target);
    }

    @Override
    public void setValue(String key, Object value) {
        effect.setValue(key, value);
        super.setValue(key, value);
    }
}
