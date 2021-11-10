
package mage.abilities.effects.common.continuous;

import java.util.Iterator;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class BoostAllEffect extends ContinuousEffectImpl {

    protected DynamicValue power;
    protected DynamicValue toughness;
    protected boolean excludeSource;
    protected FilterCreaturePermanent filter;
    protected boolean lockedInPT;

    public BoostAllEffect(int power, int toughness, Duration duration) {
        this(power, toughness, duration, false);
    }

    public BoostAllEffect(DynamicValue power, DynamicValue toughness, Duration duration) {
        this(power, toughness, duration, new FilterCreaturePermanent("all creatures"), false);
    }

    public BoostAllEffect(int power, int toughness, Duration duration, boolean excludeSource) {
        this(power, toughness, duration, new FilterCreaturePermanent("all creatures"), excludeSource);
    }

    public BoostAllEffect(int power, int toughness, Duration duration, FilterCreaturePermanent filter, boolean excludeSource) {
        this(StaticValue.get(power), StaticValue.get(toughness), duration, filter, excludeSource);
    }

    public BoostAllEffect(DynamicValue power, DynamicValue toughness, Duration duration, FilterCreaturePermanent filter, boolean excludeSource) {
        this(power, toughness, duration, filter, excludeSource, null);
    }

    public BoostAllEffect(DynamicValue power, DynamicValue toughness, Duration duration, FilterCreaturePermanent filter, boolean excludeSource, String rule) {
        this(power, toughness, duration, filter, excludeSource, rule, false);
    }

    public BoostAllEffect(DynamicValue power, DynamicValue toughness, Duration duration, FilterCreaturePermanent filter, boolean excludeSource, String rule, boolean lockedInPT) {
        super(duration, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, isCanKill(toughness) ? Outcome.UnboostCreature : Outcome.BoostCreature);
        this.power = power;
        this.toughness = toughness;
        this.filter = filter;
        this.excludeSource = excludeSource;

        this.lockedInPT = lockedInPT;
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
        this.lockedInPT = effect.lockedInPT;
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
            for (Permanent perm : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
                if (!(excludeSource && perm.getId().equals(source.getSourceId())) && selectedByRuntimeData(perm, source, game)) {
                    affectedObjectList.add(new MageObjectReference(perm, game));
                }
            }
        }
        if (lockedInPT) {
            power = StaticValue.get(power.calculate(game, source, this));
            toughness = StaticValue.get(toughness.calculate(game, source, this));
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (this.affectedObjectsSet) {
            for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext();) { // filter may not be used again, because object can have changed filter relevant attributes but still geets boost
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
            for (Permanent perm : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
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
        if (excludeSource) {
            sb.append("other ");
        }
        sb.append(filter.getMessage()).append(" get ");
        sb.append(CardUtil.getBoostText(power, toughness, duration));
        staticText = sb.toString();
    }

}
