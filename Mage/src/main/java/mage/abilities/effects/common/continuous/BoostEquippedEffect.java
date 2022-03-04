
package mage.abilities.effects.common.continuous;

import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class BoostEquippedEffect extends ContinuousEffectImpl {

    private DynamicValue power;
    private DynamicValue toughness;
    private boolean fixedTarget = false;

    public BoostEquippedEffect(int power, int toughness) {
        this(power, toughness, Duration.WhileOnBattlefield);
    }

    public BoostEquippedEffect(int power, int toughness, Duration duration) {
        this(StaticValue.get(power), StaticValue.get(toughness), duration);
    }

    public BoostEquippedEffect(DynamicValue powerDynamicValue, DynamicValue toughnessDynamicValue) {
        this(powerDynamicValue, toughnessDynamicValue, Duration.WhileOnBattlefield);
    }

    public BoostEquippedEffect(DynamicValue powerDynamicValue, DynamicValue toughnessDynamicValue, Duration duration) {
        super(duration, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        this.power = powerDynamicValue;
        this.toughness = toughnessDynamicValue;
        if (duration == Duration.EndOfTurn) {
            fixedTarget = true;
        }
        this.staticText = "equipped creature gets " + CardUtil.getBoostText(power, toughness, duration);
    }

    public BoostEquippedEffect(final BoostEquippedEffect effect) {
        super(effect);
        this.power = effect.power.copy();
        this.toughness = effect.toughness.copy();
        this.fixedTarget = effect.fixedTarget;
    }

    @Override
    public BoostEquippedEffect copy() {
        return new BoostEquippedEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        if (fixedTarget) {
            Permanent equipment = game.getPermanent(source.getSourceId());
            if (equipment != null && equipment.getAttachedTo() != null) {
                this.setTargetPointer(new FixedTarget(equipment.getAttachedTo(), game));
            }
        }
        super.init(source, game); // inits the target pointer so call it after setting the targetPointer
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = null;
        if (fixedTarget) {
            creature = game.getPermanent(targetPointer.getFirst(game, source));
        } else {
            Permanent equipment = game.getPermanent(source.getSourceId());
            if (equipment != null && equipment.getAttachedTo() != null) {
                creature = game.getPermanent(equipment.getAttachedTo());
            }
        }

        if (creature != null) {
            creature.addPower(power.calculate(game, source, this));
            creature.addToughness(toughness.calculate(game, source, this));
        }

        return true;
    }
}
