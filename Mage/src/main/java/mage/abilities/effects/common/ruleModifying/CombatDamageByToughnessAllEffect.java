package mage.abilities.effects.common.ruleModifying;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.List;

/**
 * @author TheElk801, xanderhall
 */
public class CombatDamageByToughnessAllEffect extends ContinuousEffectImpl {

    private final FilterCreaturePermanent filter;

    public CombatDamageByToughnessAllEffect() {
        this(StaticFilters.FILTER_PERMANENT_CREATURE);
    }

    /**
     * "Each [] assigns combat damage equal to its toughness rather than its power"
     * @param filter Warning: ObjectSourcePlayer predicates will be ignored
     */
    public CombatDamageByToughnessAllEffect(FilterCreaturePermanent filter) {
        this(filter, Duration.WhileOnBattlefield);
    }
     
    public CombatDamageByToughnessAllEffect(FilterCreaturePermanent filter, Duration duration) {
        super(duration, Layer.RulesEffects, SubLayer.NA, Outcome.Neutral);
        this.filter = filter;
        this.staticText = "each " + filter.getMessage() + " assigns combat damage equal to its toughness rather than its power";
    }

    private CombatDamageByToughnessAllEffect(final CombatDamageByToughnessAllEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public CombatDamageByToughnessAllEffect copy() {
        return new CombatDamageByToughnessAllEffect(this);
    }

    @Override
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
        game.getCombat().setUseToughnessForDamage(true);
        game.getCombat().addUseToughnessForDamageFilter(filter);
    }

    @Override
    public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            return false;
        }
        affectedObjects.add(permanent);
        return true;
    }
}
