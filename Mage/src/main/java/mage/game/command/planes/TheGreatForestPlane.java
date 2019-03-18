
package mage.game.command.planes;

import java.util.ArrayList;
import java.util.List;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MainPhaseStackEmptyCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.RollPlanarDieEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.command.Plane;
import mage.target.Target;
import mage.watchers.common.PlanarRollWatcher;

/**
 *
 * @author spjspj
 */
public class TheGreatForestPlane extends Plane {

    private static final String rule = "Each creature assigns combat damage equal to its toughness rather than its power";

    public TheGreatForestPlane() {
        this.setName("Plane - The Great Forest");
        this.setExpansionSetCodeForImage("PCA");

        // Each creature assigns combat damage equal to its toughness rather than its power
        Ability ability = new SimpleStaticAbility(Zone.COMMAND, new TheGreatForestCombatDamageRuleEffect());
        this.getAbilities().add(ability);

        // Active player can roll the planar die: Whenever you roll {CHAOS}, creatures you control get +0/+2 and gain trample until end of turn
        Effect chaosEffect = new BoostControlledEffect(0, 2, Duration.EndOfTurn);
        Target chaosTarget = null;
        Effect chaosEffect2 = new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.EndOfTurn);
        Target chaosTarget2 = null;

        List<Effect> chaosEffects = new ArrayList<Effect>();
        chaosEffects.add(chaosEffect);
        chaosEffects.add(chaosEffect2);
        List<Target> chaosTargets = new ArrayList<Target>();
        chaosTargets.add(chaosTarget);
        chaosTargets.add(chaosTarget2);

        ActivateIfConditionActivatedAbility chaosAbility = new ActivateIfConditionActivatedAbility(Zone.COMMAND, new RollPlanarDieEffect(chaosEffects, chaosTargets), new GenericManaCost(0), MainPhaseStackEmptyCondition.instance);
        chaosAbility.addWatcher(new PlanarRollWatcher());
        this.getAbilities().add(chaosAbility);
        chaosAbility.setMayActivate(TargetController.ANY);
        this.getAbilities().add(new SimpleStaticAbility(Zone.ALL, new PlanarDieRollCostIncreasingEffect(chaosAbility.getOriginalId())));
    }
}

class TheGreatForestCombatDamageRuleEffect extends ContinuousEffectImpl {

    public TheGreatForestCombatDamageRuleEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Each creature assigns combat damage equal to its toughness rather than its power";
    }

    public TheGreatForestCombatDamageRuleEffect(final TheGreatForestCombatDamageRuleEffect effect) {
        super(effect);
    }

    @Override
    public TheGreatForestCombatDamageRuleEffect copy() {
        return new TheGreatForestCombatDamageRuleEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Plane cPlane = game.getState().getCurrentPlane();        
        if (cPlane == null) {
            return false;
        }
        if (!cPlane.getName().equalsIgnoreCase("Plane - The Great Forest")) {
            return false;
        }

        // Change the rule
        game.getCombat().setUseToughnessForDamage(true);
        game.getCombat().addUseToughnessForDamageFilter(StaticFilters.FILTER_PERMANENT_CREATURES);
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.RulesEffects;
    }
}
