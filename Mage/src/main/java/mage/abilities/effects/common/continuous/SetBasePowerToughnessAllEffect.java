package mage.abilities.effects.common.continuous;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Iterator;
import java.util.Locale;

/**
 * @author LevelX2
 */
public class SetBasePowerToughnessAllEffect extends ContinuousEffectImpl {

    private final FilterPermanent filter;
    private DynamicValue power;
    private DynamicValue toughness;

    public SetBasePowerToughnessAllEffect(int power, int toughness, Duration duration) {
        this(power, toughness, duration, StaticFilters.FILTER_PERMANENT_CREATURES);
    }

    public SetBasePowerToughnessAllEffect(int power, int toughness, Duration duration, FilterPermanent filter) {
        this(StaticValue.get(power), StaticValue.get(toughness), duration, filter);
    }

    public SetBasePowerToughnessAllEffect(DynamicValue power, DynamicValue toughness, Duration duration, FilterPermanent filter) {
        super(duration, Layer.PTChangingEffects_7, SubLayer.SetPT_7b, Outcome.BoostCreature);
        this.power = power;
        this.toughness = toughness;
        this.filter = filter;
        this.staticText = filter.getMessage()
                + (filter.getMessage().toLowerCase(Locale.ENGLISH).startsWith("each ") ? " has " : " have ")
                + "base power and toughness " + power + '/' + toughness
                + (duration.toString().isEmpty() ? "" : ' ' + duration.toString());
    }

    protected SetBasePowerToughnessAllEffect(final SetBasePowerToughnessAllEffect effect) {
        super(effect);
        this.power = effect.power;
        this.toughness = effect.toughness;
        this.filter = effect.filter;
    }

    @Override
    public SetBasePowerToughnessAllEffect copy() {
        return new SetBasePowerToughnessAllEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (affectedObjectsSet) {
            for (Permanent perm : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
                affectedObjectList.add(new MageObjectReference(perm, game));
            }
            power = StaticValue.get(power.calculate(game, source, this));
            toughness = StaticValue.get(toughness.calculate(game, source, this));
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int newPower = power.calculate(game, source, this);
        int newToughness = toughness.calculate(game, source, this);
        if (affectedObjectsSet) {
            for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext(); ) {
                Permanent permanent = it.next().getPermanent(game);
                if (permanent != null) {
                    permanent.getPower().setModifiedBaseValue(newPower);
                    permanent.getToughness().setModifiedBaseValue(newToughness);
                } else {
                    it.remove(); // no longer on the battlefield, remove reference to object
                }
            }
        } else {
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
                permanent.getPower().setModifiedBaseValue(newPower);
                permanent.getToughness().setModifiedBaseValue(newToughness);
            }
        }
        return true;
    }

}
