/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ..AS IS.. AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.game.command.planes;

import java.util.ArrayList;
import java.util.List;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.IsStillOnPlaneCondition;
import mage.abilities.condition.common.MainPhaseStackEmptyCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.RollPlanarDieEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.command.Plane;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.PlanarRollWatcher;

/**
 *
 * @author spjspj
 */
public class TheZephyrMazePlane extends Plane {

    private static final FilterCreaturePermanent filter1 = new FilterCreaturePermanent("creatures with flying");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("creatures without flying");

    static {
        filter1.add(new AbilityPredicate(FlyingAbility.class));
        filter2.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    private static final String flyingRule = "Creatures with flying get +2/+0";
    private static final String walkingRule = "Creatures without flying get -2/-0";

    public TheZephyrMazePlane() {
        this.setName("Plane - The Zephyr Maze");
        this.setExpansionSetCodeForImage("PCA");

        // Creatures with flying get +2/+0
        // Creatures without flying get -2/+0
        Ability ability = new SimpleStaticAbility(Zone.COMMAND, new ConditionalContinuousEffect(
                new BoostAllEffect(2, 0, Duration.WhileOnBattlefield, filter1, false),
                new IsStillOnPlaneCondition(this.getName()),
                flyingRule));
        this.getAbilities().add(ability);
        Ability ability2 = new SimpleStaticAbility(Zone.COMMAND, new ConditionalContinuousEffect(
                new BoostAllEffect(-2, 0, Duration.WhileOnBattlefield, filter2, false),
                new IsStillOnPlaneCondition(this.getName()),
                walkingRule));
        this.getAbilities().add(ability2);

        // Active player can roll the planar die: Whenever you roll {CHAOS}, target creature gains flying until end of turn
        Effect chaosEffect = new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn);        
        Target chaosTarget = new TargetCreaturePermanent(0, 1);

        List<Effect> chaosEffects = new ArrayList<Effect>();
        chaosEffects.add(chaosEffect);
        List<Target> chaosTargets = new ArrayList<Target>();
        chaosTargets.add(chaosTarget);

        ActivateIfConditionActivatedAbility chaosAbility = new ActivateIfConditionActivatedAbility(Zone.COMMAND, new RollPlanarDieEffect(chaosEffects, chaosTargets), new GenericManaCost(0), MainPhaseStackEmptyCondition.instance);
        chaosAbility.addWatcher(new PlanarRollWatcher());
        this.getAbilities().add(chaosAbility);
        chaosAbility.setMayActivate(TargetController.ANY);
        this.getAbilities().add(new SimpleStaticAbility(Zone.ALL, new PlanarDieRollCostIncreasingEffect(chaosAbility.getOriginalId())));
    }
}
