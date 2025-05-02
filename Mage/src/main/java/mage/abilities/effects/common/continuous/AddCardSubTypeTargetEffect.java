
package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author nantuko, Susucr
 */
public class AddCardSubTypeTargetEffect extends ContinuousEffectImpl {

    private final SubType addedSubType;

    public AddCardSubTypeTargetEffect(SubType addedSubType, Duration duration) {
        super(duration, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        this.addedSubType = addedSubType;
    }

    protected AddCardSubTypeTargetEffect(final AddCardSubTypeTargetEffect effect) {
        super(effect);
        this.addedSubType = effect.addedSubType;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = false;
        for (UUID targetId : getTargetPointer().getTargets(game, source)) {
            Permanent target = game.getPermanent(targetId);
            if (target != null) {
                target.addSubType(game, addedSubType);
                result = true;
            }
        }
        if (!result) {
            if (this.getDuration() == Duration.Custom) {
                this.discard();
            }
        }
        return result;
    }

    @Override
    public AddCardSubTypeTargetEffect copy() {
        return new AddCardSubTypeTargetEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return getTargetPointer().describeTargets(mode.getTargets(), "it") +
                (getTargetPointer().isPlural(mode.getTargets()) ? " become " : " becomes ") +
                CardUtil.addArticle(addedSubType.toString()) +
                " in addition to its other types" +
                (duration.toString().isEmpty() ? "" : ' ' + duration.toString());
    }
}
