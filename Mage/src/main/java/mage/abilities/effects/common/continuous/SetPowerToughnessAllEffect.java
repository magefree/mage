

package mage.abilities.effects.common.continuous;

import java.util.Iterator;
import java.util.Locale;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class SetPowerToughnessAllEffect extends ContinuousEffectImpl {

    private FilterPermanent filter;
    private DynamicValue power;
    private DynamicValue toughness;
    private boolean lockedInPT;

    public SetPowerToughnessAllEffect(int power, int toughness, Duration duration) {
        this(new StaticValue(power), new StaticValue(toughness), duration, new FilterCreaturePermanent("Creatures"), true);
    }

    public SetPowerToughnessAllEffect(int power, int toughness, Duration duration, FilterPermanent filter, boolean lockedInPT) {
        this(new StaticValue(power), new StaticValue(toughness), duration, filter, lockedInPT);
    }

    public SetPowerToughnessAllEffect(DynamicValue power, DynamicValue toughness, Duration duration, FilterPermanent filter, boolean lockedInPT) {
        super(duration, Layer.PTChangingEffects_7, SubLayer.SetPT_7b, Outcome.BoostCreature);
        this.power = power;
        this.toughness = toughness;
        this.filter = filter;
        this.lockedInPT = lockedInPT;
    }

    public SetPowerToughnessAllEffect(final SetPowerToughnessAllEffect effect) {
        super(effect);
        this.power = effect.power;
        this.toughness = effect.toughness;
        this.filter = effect.filter;
        this.lockedInPT = effect.lockedInPT;
    }

    @Override
    public SetPowerToughnessAllEffect copy() {
        return new SetPowerToughnessAllEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (affectedObjectsSet) {
            for (Permanent perm: game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
                affectedObjectList.add(new MageObjectReference(perm, game));
            }
        }
        if (lockedInPT) {
            power = new StaticValue(power.calculate(game, source, this));
            toughness = new StaticValue(toughness.calculate(game, source, this));
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int newPower = power.calculate(game, source, this);
        int newToughness = toughness.calculate(game, source, this);
        if (affectedObjectsSet) {
            for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext();) {
                Permanent permanent = it.next().getPermanent(game);
                if (permanent != null) {
                    permanent.getPower().setValue(newPower);
                    permanent.getToughness().setValue(newToughness);
                } else {
                    it.remove(); // no longer on the battlefield, remove reference to object
                }
            }
        } else {
            for (Permanent permanent: game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
                permanent.getPower().setValue(newPower);
                permanent.getToughness().setValue(newToughness);
            }
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        StringBuilder sb = new StringBuilder();
        sb.append(filter.getMessage());
        if (filter.getMessage().toLowerCase(Locale.ENGLISH).startsWith("Each ")) {
            sb.append(" has base power and toughness ");
        } else {
            sb.append(" have base power and toughness ");
        }
        sb.append(power).append('/').append(toughness);
        if (!duration.toString().isEmpty()) {
            sb.append(' ').append(duration.toString());
        }
        return sb.toString();
    }
}
