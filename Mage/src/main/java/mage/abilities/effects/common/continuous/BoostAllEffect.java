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
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.Iterator;
import java.util.Locale;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class BoostAllEffect extends ContinuousEffectImpl {

    protected DynamicValue power;
    protected DynamicValue toughness;
    protected boolean excludeSource;
    protected FilterCreaturePermanent filter;

    public BoostAllEffect(int power, int toughness, Duration duration) {
        this(power, toughness, duration, false);
    }

    public BoostAllEffect(DynamicValue power, DynamicValue toughness, Duration duration) {
        this(power, toughness, duration, StaticFilters.FILTER_PERMANENT_ALL_CREATURES, false);
    }

    public BoostAllEffect(int power, int toughness, Duration duration, boolean excludeSource) {
        this(power, toughness, duration, StaticFilters.FILTER_PERMANENT_ALL_CREATURES, excludeSource);
    }

    public BoostAllEffect(int power, int toughness, Duration duration, FilterCreaturePermanent filter, boolean excludeSource) {
        this(StaticValue.get(power), StaticValue.get(toughness), duration, filter, excludeSource);
    }

    public BoostAllEffect(DynamicValue power, DynamicValue toughness, Duration duration, FilterCreaturePermanent filter, boolean excludeSource) {
        this(power, toughness, duration, filter, excludeSource, null);
    }

    public BoostAllEffect(DynamicValue power, DynamicValue toughness, Duration duration, FilterCreaturePermanent filter, boolean excludeSource, String rule) {
        super(duration, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, CardUtil.getBoostOutcome(power, toughness));
        this.power = power;
        this.toughness = toughness;
        this.filter = filter;
        this.excludeSource = excludeSource;

        if (rule == null || rule.isEmpty()) {
            setText();
        } else {
            this.staticText = rule;
        }
    }

    public BoostAllEffect(final BoostAllEffect effect) {
        super(effect);
        this.power = effect.power;
        this.toughness = effect.toughness;
        this.filter = effect.filter.copy();
        this.excludeSource = effect.excludeSource;
    }

    @Override
    public BoostAllEffect copy() {
        return new BoostAllEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        setRuntimeData(source, game);
        if (this.affectedObjectsSet) {
            for (Permanent perm : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
                if (!(excludeSource && perm.getId().equals(source.getSourceId())) && selectedByRuntimeData(perm, source, game)) {
                    affectedObjectList.add(new MageObjectReference(perm, game));
                }
            }
            power = StaticValue.get(power.calculate(game, source, this));
            toughness = StaticValue.get(toughness.calculate(game, source, this));
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (this.affectedObjectsSet) {
            for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext(); ) { // filter may not be used again, because object can have changed filter relevant attributes but still geets boost
                Permanent permanent = it.next().getPermanent(game);
                if (permanent != null) {
                    permanent.addPower(power.calculate(game, source, this));
                    permanent.addToughness(toughness.calculate(game, source, this));
                } else {
                    it.remove(); // no longer on the battlefield, remove reference to object
                }
            }
        } else {
            setRuntimeData(source, game);
            for (Permanent perm : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
                if (!(excludeSource && perm.getId().equals(source.getSourceId())) && selectedByRuntimeData(perm, source, game)) {
                    perm.addPower(power.calculate(game, source, this));
                    perm.addToughness(toughness.calculate(game, source, this));
                }
            }

        }
        return true;
    }

    /**
     * Overwrite this in effect that inherits from this
     *
     * @param source
     * @param game
     */
    protected void setRuntimeData(Ability source, Game game) {

    }

    /**
     * Overwrite this in effect that inherits from this
     *
     * @param permanent
     * @param source
     * @param game
     * @return
     */
    protected boolean selectedByRuntimeData(Permanent permanent, Ability source, Game game) {
        return true;
    }

    protected void setText() {
        StringBuilder sb = new StringBuilder();
        boolean each = filter.getMessage().toLowerCase(Locale.ENGLISH).startsWith("each");
        if (excludeSource && !each) {
            sb.append("other ");
        }
        sb.append(filter.getMessage());
        sb.append(each ? " gets " : " get ");
        sb.append(CardUtil.getBoostText(power, toughness, duration));
        staticText = sb.toString();
    }

}
