package mage.abilities.effects;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.game.Game;
import mage.target.Target;
import mage.target.targetadjustment.TargetAdjuster;
import mage.target.targetpointer.TargetPointer;

public class OneShotNonTargetEffect extends OneShotEffect {
    OneShotEffect effect;
    Target notTarget;
    TargetAdjuster adjuster = null;

    public OneShotNonTargetEffect(OneShotEffect effect, Target notTarget) {
        super(effect.outcome);
        this.effect = effect.copy();
        this.notTarget = notTarget.copy();
    }

    public OneShotNonTargetEffect(OneShotEffect effect, Target notTarget, TargetAdjuster adjuster) {
        super(effect.outcome);
        this.effect = effect;
        this.notTarget = notTarget;
        this.notTarget.withNotTarget(true);
        this.adjuster = adjuster;
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
            adjuster.adjustTargets(modifiedSource, game);
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

    @Override
    public String getText(Mode mode) {
        return effect.getText(mode);
    }
}
