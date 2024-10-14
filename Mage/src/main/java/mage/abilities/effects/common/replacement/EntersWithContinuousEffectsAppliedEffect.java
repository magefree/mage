package mage.abilities.effects.common.replacement;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.List;

/**
 * @author xenohedron
 */
public class EntersWithContinuousEffectsAppliedEffect extends ReplacementEffectImpl {

    private final List<ContinuousEffect> effects;

    /**
     * Use prior to an effect that puts a card onto the battlefield e.g. with additional color/subtype
     * Text must be set manually (since printed out of order); intended for internal usage
     */
    public EntersWithContinuousEffectsAppliedEffect(List<ContinuousEffect> effects) {
        super(Duration.EndOfStep, Outcome.Neutral);
        if (effects.isEmpty()) {
            throw new IllegalArgumentException("Wrong code usage: empty effects list in EntersWithContinuousEffectsApplied");
        }
        this.effects = effects;
    }

    private EntersWithContinuousEffectsAppliedEffect(final EntersWithContinuousEffectsAppliedEffect effect) {
        super(effect);
        this.effects = CardUtil.deepCopyObject(effect.effects);
    }

    @Override
    public EntersWithContinuousEffectsAppliedEffect copy() {
        return new EntersWithContinuousEffectsAppliedEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
        return permanent != null && getTargetPointer().getTargets(game, source).contains(permanent.getId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
        if (permanent != null) {
            FixedTarget fixedTarget = new FixedTarget(permanent.getId(), permanent.getZoneChangeCounter(game) + 1);
            for (ContinuousEffect effect : effects) {
                game.addEffect(effect.copy().setTargetPointer(fixedTarget.copy()), source);
            }
        }
        return false;
    }
}
