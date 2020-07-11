
package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author nantuko
 */
public class AddCardSubTypeTargetEffect extends ContinuousEffectImpl {

    private final SubType addedSubType;

    public AddCardSubTypeTargetEffect(SubType addedSubType, Duration duration) {
        super(duration, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        this.addedSubType = addedSubType;
    }

    public AddCardSubTypeTargetEffect(final AddCardSubTypeTargetEffect effect) {
        super(effect);
        this.addedSubType = effect.addedSubType;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(targetPointer.getFirst(game, source));
        if (target != null) {
            if (!target.hasSubtype(addedSubType, game)) {
                target.getSubtype(game).add(addedSubType);
            }
        } else {
            if (duration == Duration.Custom) {
                discard();
            }
        }
        return false;
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
        StringBuilder sb = new StringBuilder();
        if (!mode.getTargets().isEmpty()) {
            sb.append("Target ").append(mode.getTargets().get(0).getTargetName());
        } else {
            sb.append("It ");
        }
        if (addedSubType.toString().matches("(?i)^[AEIOUYaeiouy].*$")) {
            sb.append(" becomes an ");
        } else {
            sb.append(" becomes a ");
        }
        sb.append(addedSubType).append(" in addition to its other types ").append(duration.toString());
        return sb.toString();
    }
}
