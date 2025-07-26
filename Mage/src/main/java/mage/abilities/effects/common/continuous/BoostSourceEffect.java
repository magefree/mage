package mage.abilities.effects.common.continuous;

import mage.MageItem;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.List;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class BoostSourceEffect extends ContinuousEffectImpl {
    private DynamicValue power;
    private DynamicValue toughness;

    public BoostSourceEffect(int power, int toughness, Duration duration) {
        this(power, toughness, duration, "{this}");
    }

    public BoostSourceEffect(int power, int toughness, Duration duration, String description) {
        this(StaticValue.get(power), StaticValue.get(toughness), duration, description);
    }

    public BoostSourceEffect(DynamicValue power, DynamicValue toughness, Duration duration) {
        this(power, toughness, duration, "{this}");
    }

    public BoostSourceEffect(DynamicValue power, DynamicValue toughness, Duration duration, String description) {
        super(duration, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        this.power = power;
        this.toughness = toughness;
        this.staticText = description + " gets " + CardUtil.getBoostText(power, toughness, duration);
    }

    protected BoostSourceEffect(final BoostSourceEffect effect) {
        super(effect);
        this.power = effect.power.copy();
        this.toughness = effect.toughness.copy();
    }

    @Override
    public BoostSourceEffect copy() {
        return new BoostSourceEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (getAffectedObjectsSet()) {
            affectedObjectList.add(new MageObjectReference(source.getSourceId(), game));
            // Boost must be locked in (if it's a dynamic value) for non-static ability
            power = StaticValue.get(power.calculate(game, source, this));
            toughness = StaticValue.get(toughness.calculate(game, source, this));
        }
    }

    @Override
    public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
        if (getAffectedObjectsSet()) {
            for (MageObjectReference mor : affectedObjectList) {
                Permanent permanent = mor.getPermanent(game);
                if (permanent != null) {
                    affectedObjects.add(permanent);
                }
            }
        } else {
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent != null) {
                affectedObjects.add(permanent);
            }
        }
        if (affectedObjects.isEmpty()) {
            if (getAffectedObjectsSet()) {
                this.discard();
            }
            return false;
        }
        return true;
    }

    @Override
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
        for (MageItem object : affectedObjects) {
            Permanent permanent = (Permanent) object;
            permanent.addPower(power.calculate(game, source, this));
            permanent.addToughness(toughness.calculate(game, source, this));
        }
    }
}
