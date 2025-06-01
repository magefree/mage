package mage.abilities.effects.common.continuous;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author LevelX2
 */
public class BecomesSubtypeAllEffect extends ContinuousEffectImpl {

    private final List<SubType> subtypes = new ArrayList<>();
    private final boolean loseOther; // loses other subtypes
    private final FilterCreaturePermanent filter;

    public BecomesSubtypeAllEffect(Duration duration, SubType subtype) {
        this(duration, Arrays.asList(subtype));
    }

    public BecomesSubtypeAllEffect(Duration duration, List<SubType> subtypes) {
        this(duration, subtypes, StaticFilters.FILTER_PERMANENT_CREATURE, true);
    }

    public BecomesSubtypeAllEffect(Duration duration, List<SubType> subtypes, FilterCreaturePermanent filter, boolean loseOther) {
        super(duration, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Detriment);
        this.subtypes.addAll(subtypes);
        this.staticText = setText();
        this.loseOther = loseOther;
        this.filter = filter;
    }

    protected BecomesSubtypeAllEffect(final BecomesSubtypeAllEffect effect) {
        super(effect);
        this.subtypes.addAll(effect.subtypes);
        this.loseOther = effect.loseOther;
        this.filter = effect.filter;
    }

    @Override
    public boolean applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> objects) {
        if (objects.isEmpty() && duration == Duration.Custom) {
            this.discard();
            return false;
        }
        for (MageItem object : objects) {
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
    public List<MageItem> queryAffectedObjects(Layer layer, Ability source, Game game) {
        return new ArrayList<>(game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game));
    }

    @Override
    public BecomesSubtypeAllEffect copy() {
        return new BecomesSubtypeAllEffect(this);
    }

    private String setText() {
        StringBuilder sb = new StringBuilder();
        sb.append("Target creature becomes that type");
        if (!duration.toString().isEmpty()
                && duration != Duration.EndOfGame) {
            sb.append(' ').append(duration.toString());
        }
        return sb.toString();
    }
}
