package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.WhileConditionContinuousEffect;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class BoostSourceWhileControlsEffect extends WhileConditionContinuousEffect {

    private final int power;
    private final int toughness;
    private final String filterDescription;

    public BoostSourceWhileControlsEffect(FilterPermanent filter, int power, int toughness) {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, new PermanentsOnTheBattlefieldCondition(filter), Outcome.BoostCreature);
        this.power = power;
        this.toughness = toughness;
        this.filterDescription = filter.getMessage();
        staticText = "{this} gets "
                + CardUtil.getBoostCountAsStr(power, toughness)
                + " as long as you control "
                + (filterDescription.startsWith("an ") ? "" : "a ")
                + filterDescription;
    }

    protected BoostSourceWhileControlsEffect(final BoostSourceWhileControlsEffect effect) {
        super(effect);
        this.power = effect.power;
        this.toughness = effect.toughness;
        this.filterDescription = effect.filterDescription;
    }

    @Override
    public BoostSourceWhileControlsEffect copy() {
        return new BoostSourceWhileControlsEffect(this);
    }

    @Override
    public boolean applyEffect(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            permanent.addPower(power);
            permanent.addToughness(toughness);
        }
        return true;
    }

}
