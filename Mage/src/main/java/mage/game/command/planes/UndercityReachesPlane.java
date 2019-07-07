
package mage.game.command.planes;

import java.util.ArrayList;
import java.util.List;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MainPhaseStackEmptyCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.RollPlanarDieEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect.HandSizeModification;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.command.Plane;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.PlanarRollWatcher;

/**
 *
 * @author spjspj
 */
public class UndercityReachesPlane extends Plane {

    private static final FilterCard filter = new FilterCard("creature spells");

    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
    }

    public UndercityReachesPlane() {
        this.setName("Plane - Undercity Reaches");
        this.setExpansionSetCodeForImage("PCA");

        // Whenever a creature deals combat damage to a player, its controller may a draw a card
        Ability ability = new UndercityReachesTriggeredAbility();

        this.getAbilities().add(ability);

        // Active player can roll the planar die: Whenever you roll {CHAOS}, reveal the top three cards of your library. Put all creature cards revealed this way into your hand and the rest into your graveyard.
        Effect chaosEffect = new MaximumHandSizeControllerEffect(Integer.MAX_VALUE, Duration.EndOfGame, HandSizeModification.SET);
        Target chaosTarget = null;

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

class UndercityReachesTriggeredAbility extends TriggeredAbilityImpl {

    public UndercityReachesTriggeredAbility() {
        super(Zone.COMMAND, null, true);
    }

    public UndercityReachesTriggeredAbility(final UndercityReachesTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public UndercityReachesTriggeredAbility copy() {
        return new UndercityReachesTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Plane cPlane = game.getState().getCurrentPlane();
        if (cPlane == null) {
            return false;
        }
        if (!cPlane.getName().equalsIgnoreCase("Plane - Undercity Reaches")) {
            return false;
        }

        if (((DamagedPlayerEvent) event).isCombatDamage()) {
            Permanent creature = game.getPermanent(event.getSourceId());
            if (creature != null) {
                Effect effect = new DrawCardTargetEffect(new StaticValue(1), false, true);
                effect.setTargetPointer(new FixedTarget(creature.getControllerId()));
                effect.apply(game, null);
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature deals combat damage to a player, its controller may a draw a card";
    }
}
