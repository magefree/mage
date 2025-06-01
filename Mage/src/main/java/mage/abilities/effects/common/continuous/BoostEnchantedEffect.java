package mage.abilities.effects.common.continuous;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.Collections;
import java.util.List;

/**
 * @author BetaSteward_at_googlemail.com, North
 */
public class BoostEnchantedEffect extends ContinuousEffectImpl {

    private DynamicValue power;
    private DynamicValue toughness;

    public BoostEnchantedEffect(int power, int toughness) {
        this(power, toughness, Duration.WhileOnBattlefield);
    }

    public BoostEnchantedEffect(int power, int toughness, Duration duration) {
        this(StaticValue.get(power), StaticValue.get(toughness), duration);
    }

    public BoostEnchantedEffect(DynamicValue power, DynamicValue toughness) {
        this(power, toughness, Duration.WhileOnBattlefield);
    }

    public BoostEnchantedEffect(DynamicValue power, DynamicValue toughness, Duration duration) {
        super(duration, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, CardUtil.getBoostOutcome(power, toughness));
        this.power = power;
        this.toughness = toughness;
        this.staticText = "enchanted creature gets " + CardUtil.getBoostText(power, toughness, duration);
    }

    protected BoostEnchantedEffect(final BoostEnchantedEffect effect) {
        super(effect);
        this.power = effect.power.copy();
        this.toughness = effect.toughness.copy();
    }

    @Override
    public BoostEnchantedEffect copy() {
        return new BoostEnchantedEffect(this);
    }

    @Override
    public boolean applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageObject> objects) {
        if (getAffectedObjectsSet() && objects.isEmpty()) {
            this.discard();
            return false;
        }
        for (MageObject object : objects) {
            if (!(object instanceof Permanent)) {
                continue;
            }
            Permanent permanent = (Permanent) object;
            permanent.addPower(power.calculate(game, source, this));
            permanent.addToughness(toughness.calculate(game, source, this));
        }
        return true;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (getAffectedObjectsSet()) {
            // Added boosts of activated or triggered abilities exist independent from the source they are created by
            // so a continuous effect for the permanent itself with the attachment is created
            Permanent equipment = game.getPermanentOrLKIBattlefield(source.getSourceId());
            if (equipment != null && equipment.getAttachedTo() != null) {
                this.setTargetPointer(new FixedTarget(equipment.getAttachedTo(), game.getState().getZoneChangeCounter(equipment.getAttachedTo())));
            }
            power = StaticValue.get(power.calculate(game, source, this));
            toughness = StaticValue.get(toughness.calculate(game, source, this));
        }
    }

    @Override
    public List<MageObject> queryAffectedObjects(Layer layer, Ability source, Game game) {
        if (getAffectedObjectsSet()) {
            Permanent equipment = game.getPermanent(getTargetPointer().getFirst(game, source));
            return equipment != null ? Collections.singletonList(equipment) : Collections.emptyList();
        } else {
            Permanent equipment = game.getPermanent(source.getSourceId());
            if (equipment == null || equipment.getAttachedTo() == null) {
                return Collections.emptyList();
            }
            Permanent attachedTo = game.getPermanent(equipment.getAttachedTo());
            return attachedTo != null ? Collections.singletonList(attachedTo) : Collections.emptyList();
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = null;
        if (getAffectedObjectsSet()) {
            permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (permanent == null) {
                discard();
                return true;
            }
        } else {
            Permanent equipment = game.getPermanent(source.getSourceId());
            if (equipment != null && equipment.getAttachedTo() != null) {
                permanent = game.getPermanent(equipment.getAttachedTo());
            }
        }
        if (permanent != null) {
            permanent.addPower(power.calculate(game, source, this));
            permanent.addToughness(toughness.calculate(game, source, this));
        }
        return true;
    }

}
