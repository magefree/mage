package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author LevelX2
 */
public class BoostAllOfChosenSubtypeEffect extends BoostAllEffect {

    private SubType subtype = null;

    public BoostAllOfChosenSubtypeEffect(int power, int toughness, Duration duration, boolean excludeSource) {
        super(power, toughness, duration, new FilterCreaturePermanent("creatures of the chosen type"), excludeSource);
    }

    public BoostAllOfChosenSubtypeEffect(int power, int toughness, Duration duration, FilterCreaturePermanent filter, boolean excludeSource) {
        super(power, toughness, duration, filter, excludeSource);
    }

    public BoostAllOfChosenSubtypeEffect(final BoostAllOfChosenSubtypeEffect effect) {
        super(effect);
        this.subtype = effect.subtype;
    }

    @Override
    public BoostAllOfChosenSubtypeEffect copy() {
        return new BoostAllOfChosenSubtypeEffect(this);
    }

    @Override
    protected boolean selectedByRuntimeData(Permanent permanent, Ability source, Game game) {
        if (subtype != null) {
            return permanent.hasSubtype(subtype, game);
        }
        return false;
    }

    @Override
    protected void setRuntimeData(Ability source, Game game) {
        SubType subType = ChooseCreatureTypeEffect.getChosenCreatureType(source.getSourceId(), game);
        if (subType != null) {
            subtype = subType;
        } else {
            discard();
        }
    }
}
