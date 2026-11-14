package mage.abilities.common;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.util.functions.MiracleCostModifierCreator;
import mage.watchers.common.MiracleGrantedWatcher;

public class MiracleGrantedAbility extends SimpleStaticAbility {

    private static final String staticRule = " <i>(You may cast that card for its miracle cost when you draw it if it's the first card you drew this turn.)</i>";
    private final String ruleText;

    public MiracleGrantedAbility(FilterCard filter, MiracleCostModifierCreator miracleCostModifierCreator, String costText) {
        super(new MiracleGrantedEffect());
        this.ruleText = filter.getMessage() + " in your hand have miracle. Its miracle cost is equal to " + costText + staticRule;
        this.addWatcher(new MiracleGrantedWatcher(this.zone, filter, miracleCostModifierCreator, costText));
    }

    private MiracleGrantedAbility(final MiracleGrantedAbility ability) {
        super(ability);
        this.ruleText = ability.ruleText;
    }

    @Override
    public MiracleGrantedAbility copy() {
        return new MiracleGrantedAbility(this);
    }

    @Override
    public String getRule() {
        return this.ruleText;
    }
}

class MiracleGrantedEffect extends ContinuousEffectImpl {

    public MiracleGrantedEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
    }

    private MiracleGrantedEffect(final MiracleGrantedEffect effect) {
        super(effect);
    }

    @Override
    public MiracleGrantedEffect copy() {
        return new MiracleGrantedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }
}
