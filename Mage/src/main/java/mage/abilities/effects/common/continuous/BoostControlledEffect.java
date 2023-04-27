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
public class BoostControlledEffect extends ContinuousEffectImpl {

    private DynamicValue power;
    private DynamicValue toughness;
    protected FilterCreaturePermanent filter;
    protected boolean excludeSource;
    protected boolean lockedIn = false;

    public BoostControlledEffect(int power, int toughness, Duration duration) {
        this(power, toughness, duration, StaticFilters.FILTER_PERMANENT_CREATURES, false);
    }

    public BoostControlledEffect(DynamicValue power, DynamicValue toughness, Duration duration) {
        this(power, toughness, duration, StaticFilters.FILTER_PERMANENT_CREATURES, false);
    }

    public BoostControlledEffect(int power, int toughness, Duration duration, boolean excludeSource) {
        this(power, toughness, duration, StaticFilters.FILTER_PERMANENT_CREATURES, excludeSource);
    }

    public BoostControlledEffect(int power, int toughness, Duration duration, FilterCreaturePermanent filter) {
        this(StaticValue.get(power), StaticValue.get(toughness), duration, filter, false);
    }

    public BoostControlledEffect(int power, int toughness, Duration duration, FilterCreaturePermanent filter, boolean excludeSource) {
        this(StaticValue.get(power), StaticValue.get(toughness), duration, filter, excludeSource, true);
    }

    public BoostControlledEffect(DynamicValue power, DynamicValue toughness, Duration duration, FilterCreaturePermanent filter, boolean excludeSource) {
        this(power, toughness, duration, filter, excludeSource, false);
    }

    /**
     * @param power
     * @param toughness
     * @param duration
     * @param filter        AnotherPredicate is not working, you need to use the
     *                      excludeSource option
     * @param lockedIn      if true, power and toughness will be calculated only
     *                      once, when the ability resolves
     * @param excludeSource
     */
    public BoostControlledEffect(DynamicValue power, DynamicValue toughness, Duration duration, FilterCreaturePermanent filter, boolean excludeSource, boolean lockedIn) {
        super(duration, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        this.power = power;
        this.toughness = toughness;
        this.filter = (filter == null ? StaticFilters.FILTER_PERMANENT_CREATURES : filter);
        this.excludeSource = excludeSource;
        this.lockedIn = lockedIn;
        setText();
    }

    public BoostControlledEffect(final BoostControlledEffect effect) {
        super(effect);
        this.power = effect.power;
        this.toughness = effect.toughness;
        this.filter = effect.filter.copy();
        this.excludeSource = effect.excludeSource;
        this.lockedIn = effect.lockedIn;
    }

    @Override
    public BoostControlledEffect copy() {
        return new BoostControlledEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (this.affectedObjectsSet) {
            for (Permanent perm : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
                if (perm.isControlledBy(source.getControllerId())
                        && !(excludeSource && perm.getId().equals(source.getSourceId()))) {
                    affectedObjectList.add(new MageObjectReference(perm, game));
                }
            }
        }
        if (this.lockedIn) {
            power = StaticValue.get(power.calculate(game, source, this));
            toughness = StaticValue.get(toughness.calculate(game, source, this));
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (this.affectedObjectsSet) {
            for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext(); ) {
                Permanent permanent = it.next().getPermanent(game);
                if (permanent != null) {
                    permanent.addPower(power.calculate(game, source, this));
                    permanent.addToughness(toughness.calculate(game, source, this));
                } else {
                    it.remove(); // no longer on the battlefield, remove reference to object
                }
            }
        } else {
            for (Permanent perm : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
                if (perm.isControlledBy(source.getControllerId())
                        && (!(excludeSource && perm.getId().equals(source.getSourceId())))) {
                    perm.addPower(power.calculate(game, source, this));
                    perm.addToughness(toughness.calculate(game, source, this));
                }
            }
        }
        return true;
    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        String message = filter.getMessage().toLowerCase(Locale.ENGLISH);
        boolean each = message.startsWith("each");
        if (excludeSource && !each && !message.startsWith("all")) {
            sb.append("other ");
        }
        sb.append(filter.getMessage()).append(" you control ");
        sb.append(each ? "gets " : "get ");
        sb.append(CardUtil.getBoostText(power, toughness, duration));
        staticText = sb.toString();
    }

    public void setRule(String rule) {
        staticText = rule;
    }

    public void setLockedIn(boolean lockedIn) {
        this.lockedIn = lockedIn;
    }
}
