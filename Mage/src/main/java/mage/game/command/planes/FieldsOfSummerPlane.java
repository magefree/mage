
package mage.game.command.planes;

import java.util.ArrayList;
import java.util.List;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.condition.common.MainPhaseStackEmptyCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.abilities.effects.common.RollPlanarDieEffect;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.command.Plane;
import mage.players.Player;
import mage.target.Target;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.PlanarRollWatcher;

/**
 *
 * @author spjspj
 */
public class FieldsOfSummerPlane extends Plane {

    private final static FilterSpell filter = new FilterSpell("a spell");

    public FieldsOfSummerPlane() {
        this.setName("Plane - Fields of Summer");
        this.setExpansionSetCodeForImage("PCA");

        // Whenever a player casts a spell, that player may gain 2 life
        SpellCastAllTriggeredAbility ability = new SpellCastAllTriggeredAbility(Zone.COMMAND, new FieldsOfSummerEffect(), filter, false, SetTargetPointer.PLAYER);
        this.getAbilities().add(ability);

        // Active player can roll the planar die: Whenever you roll {CHAOS}, you may gain 10 life
        Effect chaosEffect = new GainLifeEffect(10);
        Target chaosTarget = null;

        List<Effect> chaosEffects = new ArrayList<>();
        chaosEffects.add(chaosEffect);
        List<Target> chaosTargets = new ArrayList<>();
        chaosTargets.add(chaosTarget);

        ActivateIfConditionActivatedAbility chaosAbility = new ActivateIfConditionActivatedAbility(Zone.COMMAND, new RollPlanarDieEffect(chaosEffects, chaosTargets), new GenericManaCost(0), MainPhaseStackEmptyCondition.instance);
        chaosAbility.addWatcher(new PlanarRollWatcher());
        this.getAbilities().add(chaosAbility);
        chaosAbility.setMayActivate(TargetController.ANY);
        this.getAbilities().add(new SimpleStaticAbility(Zone.ALL, new PlanarDieRollCostIncreasingEffect(chaosAbility.getOriginalId())));
    }
}

class FieldsOfSummerEffect extends OneShotEffect {

    public FieldsOfSummerEffect() {
        super(Outcome.GainLife);
        this.staticText = "that player may gain 2 life";
    }

    public FieldsOfSummerEffect(final FieldsOfSummerEffect effect) {
        super(effect);
    }

    @Override
    public FieldsOfSummerEffect copy() {
        return new FieldsOfSummerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Plane cPlane = game.getState().getCurrentPlane();
        if (cPlane == null || !cPlane.getName().equalsIgnoreCase("Plane - Fields of Summer")) {
            return false;
        }
        Player owner = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (owner != null && owner.canRespond() && owner.chooseUse(Outcome.Benefit, "Gain 2 life?", source, game)) {
            Effect effect = new GainLifeTargetEffect(2);
            effect.setTargetPointer(new FixedTarget(owner.getId())).apply(game, source);
            return true;
        }
        return false;
    }
}
