package mage.abilities.effects.common.continuous;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class BoostSourceEffect extends ContinuousEffectImpl {
    private DynamicValue power;
    private DynamicValue toughness;
    private final String sourceDescription;

    public BoostSourceEffect(int power, int toughness, Duration duration) {
        this(power, toughness, duration, "{this}");
    }

    public BoostSourceEffect(int power, int toughness, Duration duration, String sourceDescription) {
        this(StaticValue.get(power), StaticValue.get(toughness), duration, sourceDescription);
    }

    public BoostSourceEffect(DynamicValue power, DynamicValue toughness, Duration duration) {
        this(power, toughness, duration, "{this}");
    }

    public BoostSourceEffect(DynamicValue power, DynamicValue toughness, Duration duration, String sourceDescription) {
        super(duration, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        this.power = power;
        this.toughness = toughness;
        this.sourceDescription = sourceDescription;
        this.staticText = sourceDescription + " gets " + CardUtil.getBoostText(power, toughness, duration, ValuePhrasing.LEGACY);
    }

    protected BoostSourceEffect(final BoostSourceEffect effect) {
        super(effect);
        this.power = effect.power.copy();
        this.toughness = effect.toughness.copy();
        this.sourceDescription = effect.sourceDescription;
    }

    @Override
    public BoostSourceEffect copy() {
        return new BoostSourceEffect(this);
    }

    public BoostSourceEffect usingPhrasing(ValuePhrasing textPhrasing){
        this.staticText = sourceDescription + " gets " + CardUtil.getBoostText(power, toughness, duration, textPhrasing);
        return this;
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
    public boolean apply(Game game, Ability source) {
        Permanent target;
        if (getAffectedObjectsSet()) {
            target = affectedObjectList.get(0).getPermanent(game);
        } else {
            target = game.getPermanent(source.getSourceId());
        }
        if (target != null) {
            target.addPower(power.calculate(game, source, this));
            target.addToughness(toughness.calculate(game, source, this));
            return true;
        }
        return false;
    }
}
