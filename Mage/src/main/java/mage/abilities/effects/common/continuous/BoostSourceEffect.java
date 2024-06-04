package mage.abilities.effects.common.continuous;

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

/**
 * @author BetaSteward_at_googlemail.com
 */
public class BoostSourceEffect extends ContinuousEffectImpl {

    private final DynamicValue power;
    private final DynamicValue toughness;

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
        this.power = power.copy();
        this.toughness = toughness.copy();
        this.staticText = description + " gets " + CardUtil.getBoostText(power, toughness, duration);
    }

    protected BoostSourceEffect(final BoostSourceEffect effect) {
        super(effect);
        this.power = effect.power;
        this.toughness = effect.toughness;
    }

    @Override
    public BoostSourceEffect copy() {
        return new BoostSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent != null) {
            permanent.addPower(power.calculate(game, source, this));
            permanent.addToughness(toughness.calculate(game, source, this));
            return true;
        }
        return false;
    }
}
