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
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.List;

/**
 * @author TheElk801, xenohedron
 */
public class CombatDamageByToughnessControlledEffect extends ContinuousEffectImpl {

    private final FilterCreaturePermanent filter;

    public CombatDamageByToughnessControlledEffect() {
        this(StaticFilters.FILTER_PERMANENT_CREATURE);
    }

    /**
     * "Each [] you control assigns combat damage equal to its toughness rather than its power"
     * @param filter Warning: ObjectSourcePlayer predicates will be ignored
     */
    public CombatDamageByToughnessControlledEffect(FilterCreaturePermanent filter) {
        this(filter, Duration.WhileOnBattlefield);
    }

    public CombatDamageByToughnessControlledEffect(FilterCreaturePermanent filter, Duration duration) {
        super(duration, Layer.RulesEffects, SubLayer.NA, Outcome.Neutral);
        this.filter = filter;
        this.staticText = "each " + filter.getMessage()
                + (filter.getMessage().contains("you control") ? "" : " you control")
                + " assigns combat damage equal to its toughness rather than its power";
    }

    private CombatDamageByToughnessControlledEffect(final CombatDamageByToughnessControlledEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public CombatDamageByToughnessControlledEffect copy() {
        return new CombatDamageByToughnessControlledEffect(this);
    }

    @Override
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
        FilterCreaturePermanent filterPermanent = filter.copy();
        filterPermanent.add(new ControllerIdPredicate(source.getControllerId()));
        game.getCombat().setUseToughnessForDamage(true);
        game.getCombat().addUseToughnessForDamageFilter(filterPermanent);
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
