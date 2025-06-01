package mage.abilities.effects.common.continuous;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.*;
import java.util.stream.Collectors;

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
    public boolean applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageObject> objects) {
        if (objects.isEmpty() && this.duration == Duration.Custom) {
            discard();
            return false;
        }
        for (MageObject object : objects) {
            if (!(object instanceof Permanent)) {
                continue;
            }
            Permanent permanent = (Permanent) object;
            if (loseOther) {
                permanent.removeAllCreatureTypes(game);
            }
            permanent.addSubType(game, subtypes);
        }
        return true;
    }

    @Override
    public List<MageObject> queryAffectedObjects(Layer layer, Ability source, Game game) {
        return getTargetPointer().getTargets(game, source)
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public BecomesCreatureTypeTargetEffect copy() {
        return new BecomesCreatureTypeTargetEffect(this);
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
