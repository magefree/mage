package mage.abilities.effects.common.continuous;

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

import java.util.Iterator;
import java.util.Locale;

/**
 * @author LevelX2
 */
public class SetBasePowerToughnessAllEffect extends ContinuousEffectImpl {

    private final FilterPermanent filter;
    private DynamicValue power;
    private DynamicValue toughness;
    private final boolean lockedInPT;

    public SetBasePowerToughnessAllEffect(int power, int toughness, Duration duration) {
        this(StaticValue.get(power), StaticValue.get(toughness), duration, new FilterCreaturePermanent("Creatures"), true);
    }

    public SetBasePowerToughnessAllEffect(int power, int toughness, Duration duration, FilterPermanent filter, boolean lockedInPT) {
        this(StaticValue.get(power), StaticValue.get(toughness), duration, filter, lockedInPT);
    }

    public SetBasePowerToughnessAllEffect(DynamicValue power, DynamicValue toughness, Duration duration, FilterPermanent filter, boolean lockedInPT) {
        super(duration, Layer.PTChangingEffects_7, SubLayer.SetPT_7b, Outcome.BoostCreature);
        this.power = power;
        this.toughness = toughness;
        this.filter = filter;
        this.lockedInPT = lockedInPT;
    }

    public SetBasePowerToughnessAllEffect(final SetBasePowerToughnessAllEffect effect) {
        super(effect);
        this.power = effect.power;
        this.toughness = effect.toughness;
        this.filter = effect.filter;
        this.lockedInPT = effect.lockedInPT;
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
        }
        if (lockedInPT) {
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

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(filter.getMessage());
        if (filter.getMessage().toLowerCase(Locale.ENGLISH).startsWith("each ")) {
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
