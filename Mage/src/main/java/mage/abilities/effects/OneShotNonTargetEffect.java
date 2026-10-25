package mage.abilities.effects;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.game.Game;
import mage.target.Target;
import mage.target.Targets;
import mage.target.targetadjustment.TargetAdjuster;
import mage.target.targetpointer.FixedTargets;
import mage.target.targetpointer.TargetPointer;

import java.util.List;
import java.util.stream.Collectors;

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
        Targets targets = new Targets(notTarget.copy());
        if (adjuster != null) {
            // adjusters write into an ability's target list, so give them a throwaway one
            Ability scratch = source.copy();
            scratch.getTargets().clear();
            scratch.addTarget(notTarget.copy());
            adjuster.clearDefaultTargets();
            scratch.setTargetAdjuster(adjuster);
            scratch.adjustTargets(game);
            targets = scratch.getTargets();
        }
        // the result is ignored: a for-each adjuster can leave some players with nothing to choose
        targets.choose(outcome, source.getControllerId(), source.getSourceId(), source, game);
        // zcc 0 accepts any zcc. A spell's or stack ability's zcc is not stored under its own id,
        // so MageObjectReference(id, game) would mark chosen stack objects as gone.
        // Any zcc is safe because the effect applies right after the choice.
        List<MageObjectReference> chosen = targets.stream()
                .flatMap(target -> target.getTargets().stream())
                .map(id -> new MageObjectReference(id, 0, game))
                .collect(Collectors.toList());
        // some effects fall back to other objects when their pointer is empty (ExileTargetForSourceEffect)
        if (chosen.isEmpty()) {
            return false;
        }
        OneShotEffect chosenEffect = effect.copy();
        chosenEffect.setTargetPointer(new FixedTargets(chosen));
        return chosenEffect.apply(game, source);
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
