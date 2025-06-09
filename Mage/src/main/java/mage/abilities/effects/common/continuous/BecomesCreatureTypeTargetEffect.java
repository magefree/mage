package mage.abilities.effects.common.continuous;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.*;

/**
 * @author LevelX2
 */
public class BecomesCreatureTypeTargetEffect extends ContinuousEffectImpl {

    private final List<SubType> subtypes = new ArrayList<>();
    private final boolean loseOther;  // loses other creature types

    public BecomesCreatureTypeTargetEffect(Duration duration, SubType subtype) {
        this(duration, Arrays.asList(subtype));
    }

    public BecomesCreatureTypeTargetEffect(Duration duration, SubType subtype, boolean loseOther) {
        this(duration, Arrays.asList(subtype), loseOther);
    }

    public BecomesCreatureTypeTargetEffect(Duration duration, List<SubType> subtypes) {
        this(duration, subtypes, true);
    }

    public BecomesCreatureTypeTargetEffect(Duration duration, List<SubType> subtypes, boolean loseOther) {
        super(duration, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Detriment);
        this.subtypes.addAll(subtypes);
        this.staticText = setText();
        this.loseOther = loseOther;
    }

    protected BecomesCreatureTypeTargetEffect(final BecomesCreatureTypeTargetEffect effect) {
        super(effect);
        this.subtypes.addAll(effect.subtypes);
        this.loseOther = effect.loseOther;
    }

    @Override
    public BecomesCreatureTypeTargetEffect copy() {
        return new BecomesCreatureTypeTargetEffect(this);
    }

    @Override
    public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
        for (UUID targetId : getTargetPointer().getTargets(game, source)) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null) {
                affectedObjects.add(permanent);
            }
        }
        if (affectedObjects.isEmpty() && duration == Duration.Custom) {
            this.discard();
            return false;
        }
        return !affectedObjects.isEmpty();
    }

    @Override
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
        for (MageItem object : affectedObjects) {
            Permanent permanent = (Permanent) object;
            if (loseOther) {
                permanent.removeAllCreatureTypes(game);
            }
            permanent.addSubType(game, subtypes);
        }
    }

    private String setText() {
        StringBuilder sb = new StringBuilder();
        sb.append("Target creature becomes that type");
        if (!duration.toString().isEmpty() && duration != Duration.EndOfGame) {
            sb.append(' ').append(duration.toString());
        }
        return sb.toString();
    }
}
