
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
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
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
        this(new StaticValue(power), new StaticValue(toughness), duration, filter, false);
    }

    public BoostControlledEffect(int power, int toughness, Duration duration, FilterCreaturePermanent filter, boolean excludeSource) {
        this(new StaticValue(power), new StaticValue(toughness), duration, filter, excludeSource, true);
    }

    public BoostControlledEffect(DynamicValue power, DynamicValue toughness, Duration duration, FilterCreaturePermanent filter, boolean excludeSource) {
        this(power, toughness, duration, filter, excludeSource, false);
    }

    /**
     * @param power
     * @param toughness
     * @param duration
     * @param filter AnotherPredicate is not working, you need to use the
     * excludeSource option
     * @param lockedIn if true, power and toughness will be calculated only
     * once, when the ability resolves
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
            for (Permanent perm : game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game)) {
                if (!(excludeSource && perm.getId().equals(source.getSourceId()))) {
                    affectedObjectList.add(new MageObjectReference(perm, game));
                }
            }
        }
        if (this.lockedIn) {
            power = new StaticValue(power.calculate(game, source, this));
            toughness = new StaticValue(toughness.calculate(game, source, this));
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (this.affectedObjectsSet) {
            for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext();) {
                Permanent permanent = it.next().getPermanent(game);
                if (permanent != null) {
                    permanent.addPower(power.calculate(game, source, this));
                    permanent.addToughness(toughness.calculate(game, source, this));
                } else {
                    it.remove(); // no longer on the battlefield, remove reference to object
                }
            }
        } else {
            for (Permanent perm : game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game)) {
                if (!(excludeSource && perm.getId().equals(source.getSourceId()))) {
                    perm.addPower(power.calculate(game, source, this));
                    perm.addToughness(toughness.calculate(game, source, this));
                }
            }
        }
        return true;
    }

    private void setText() {
        String message = null;
        StringBuilder sb = new StringBuilder();
        if (excludeSource) {
            sb.append("other ");
        }
        sb.append(filter.getMessage());
        sb.append(" you control get ");

        String p = power.toString();
        if (!p.startsWith("-")) {
            sb.append('+');
        }
        sb.append(p).append('/');
        String t = toughness.toString();
        if (!t.startsWith("-")) {
            if (p.startsWith("-")) {
                sb.append('-');
            } else {
                sb.append('+');
            }
        }
        sb.append(t);

        sb.append((duration == Duration.EndOfTurn ? " until end of turn" : ""));
        if (t.equals("X")) {
            message = toughness.getMessage();
        } else if (p.equals("X")) {
            message = power.getMessage();
        }
        if (message != null && !message.isEmpty()) {
            sb.append(", where X is ").append(message);
        }
        staticText = sb.toString();
    }

    public void setRule(String rule) {
        staticText = rule;
    }

    public void setLockedIn(boolean lockedIn) {
        this.lockedIn = lockedIn;
    }
}
