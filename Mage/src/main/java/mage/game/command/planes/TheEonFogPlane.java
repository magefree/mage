
package mage.game.command.planes;

import java.util.ArrayList;
import java.util.List;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MainPhaseStackEmptyCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.RollPlanarDieEffect;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.command.Plane;
import mage.game.events.GameEvent;
import mage.target.Target;
import mage.watchers.common.PlanarRollWatcher;

/**
 *
 * @author spjspj
 */
public class TheEonFogPlane extends Plane {

    public TheEonFogPlane() {
        this.setName("Plane - The Eon Fog");
        this.setExpansionSetCodeForImage("PCA");

        // All players miss their untap step
        Ability ability = new SimpleStaticAbility(Zone.COMMAND, new TheEonFogSkipUntapStepEffect(Duration.Custom, true));
        this.getAbilities().add(ability);

        // Active player can roll the planar die: Whenever you roll {CHAOS}, untap all permanents you control
        Effect chaosEffect = new UntapAllControllerEffect(new FilterControlledPermanent());
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

class TheEonFogSkipUntapStepEffect extends ContinuousRuleModifyingEffectImpl {

    boolean allPlayers = false;

    public TheEonFogSkipUntapStepEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral, false, false);
        this.allPlayers = false;
        staticText = "Players skip their untap steps";
    }

    public TheEonFogSkipUntapStepEffect(Duration d, boolean allPlayers) {
        super(d, Outcome.Neutral, false, false);
        this.allPlayers = allPlayers;
        staticText = "Players skip their untap steps";
    }

    public TheEonFogSkipUntapStepEffect(final TheEonFogSkipUntapStepEffect effect) {
        super(effect);
        this.allPlayers = effect.allPlayers;
    }

    @Override
    public TheEonFogSkipUntapStepEffect copy() {
        return new TheEonFogSkipUntapStepEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Plane cPlane = game.getState().getCurrentPlane();
        if (cPlane == null) {
            return false;
        }
        if (!cPlane.getName().equalsIgnoreCase("Plane - The Eon Fog")) {
            return false;
        }
        return event.getType() == GameEvent.EventType.UNTAP_STEP;
    }
}
