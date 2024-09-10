package mage.game.command.planes;

import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MainPhaseStackEmptyCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.RollPlanarDieEffect;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashAllEffect;
import mage.abilities.effects.common.cost.PlanarDieRollCostIncreasingEffect;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.EnchantedPredicate;
import mage.game.command.Plane;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.PlanarRollWatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * @author spjspj
 */
public class AkoumPlane extends Plane {

    private static final FilterCard filterCard = new FilterCard("enchantment spells");
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature that isn't enchanted");

    static {
        filter.add(Predicates.not(EnchantedPredicate.instance));
        filterCard.add(CardType.ENCHANTMENT.getPredicate());
    }

    public AkoumPlane() {
        this.setPlaneType(Planes.PLANE_AKOUM);

        // Players may cast enchantment spells as if they had flash
        SimpleStaticAbility ability = new SimpleStaticAbility(Zone.COMMAND, new CastAsThoughItHadFlashAllEffect(Duration.Custom, filterCard, true));
        this.getAbilities().add(ability);

        // Active player can roll the planar die: Whenever you roll {CHAOS}, destroy target creature that isn't enchanted
        Effect chaosEffect = new DestroyTargetEffect("destroy target creature that isn't enchanted");
        Target chaosTarget = new TargetCreaturePermanent(filter);

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

    private AkoumPlane(final AkoumPlane plane) {
        super(plane);
    }

    @Override
    public AkoumPlane copy() {
        return new AkoumPlane(this);
    }
}
