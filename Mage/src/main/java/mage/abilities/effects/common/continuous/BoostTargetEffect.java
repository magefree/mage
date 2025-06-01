package mage.abilities.effects.common.continuous;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class BoostTargetEffect extends ContinuousEffectImpl {

    private DynamicValue power;
    private DynamicValue toughness;

    public BoostTargetEffect(int power, int toughness) {
        this(power, toughness, Duration.EndOfTurn);
    }

    public BoostTargetEffect(int power, int toughness, Duration duration) {
        this(StaticValue.get(power), StaticValue.get(toughness), duration);
    }

    public BoostTargetEffect(DynamicValue power, DynamicValue toughness) {
        this(power, toughness, Duration.EndOfTurn);
    }

    public BoostTargetEffect(DynamicValue power, DynamicValue toughness, Duration duration) {
        super(duration, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, CardUtil.getBoostOutcome(power, toughness));
        this.power = power;
        this.toughness = toughness;
    }

    protected BoostTargetEffect(final BoostTargetEffect effect) {
        super(effect);
        this.power = effect.power.copy();
        this.toughness = effect.toughness.copy();
    }

    @Override
    public BoostTargetEffect copy() {
        return new BoostTargetEffect(this);
    }

    @Override
    public boolean applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> objects) {
        for (MageItem object : objects) {
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
            // Boost must be locked in (if it's a dynamic value) for non-static ability
            power = StaticValue.get(power.calculate(game, source, this));
            toughness = StaticValue.get(toughness.calculate(game, source, this));
        }
    }

    @Override
    public List<MageItem> queryAffectedObjects(Layer layer, Ability source, Game game) {
        return getTargetPointer().getTargets(game,source)
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .filter(permanent -> permanent.isCreature(game))
                .collect(Collectors.toList());
    }
    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return getTargetPointer().describeTargets(mode.getTargets(), "it") +
                (getTargetPointer().isPlural(mode.getTargets()) ? " each get " : " gets ") +
                CardUtil.getBoostText(power, toughness, duration);
    }
}
