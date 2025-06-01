
package mage.abilities.effects.common.continuous;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    public boolean applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> objects) {
        if (objects.isEmpty()) {
            this.discard();
            return false;
        }
        for (MageItem object : objects) {
            if (!(object instanceof Permanent)) {
                continue;
            }
            ((Permanent) object).addSubType(game, addedSubType);
        }
        return true;
    }

    @Override
    public List<MageItem> queryAffectedObjects(Layer layer, Ability source, Game game) {
        return getTargetPointer().getTargets(game, source)
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
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
