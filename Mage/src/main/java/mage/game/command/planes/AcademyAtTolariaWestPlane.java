package mage.game.command.planes;

import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.MainPhaseStackEmptyCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.RollPlanarDieEffect;
import mage.abilities.effects.common.cost.PlanarDieRollCostIncreasingEffect;
import mage.abilities.effects.common.discard.DiscardHandControllerEffect;
import mage.constants.Outcome;
import mage.constants.Planes;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.command.Plane;
import mage.players.Player;
import mage.target.Target;
import mage.util.CardUtil;
import mage.watchers.common.PlanarRollWatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * @author spjspj
 */
public class AcademyAtTolariaWestPlane extends Plane {

    public AcademyAtTolariaWestPlane() {
        this.setPlaneType(Planes.PLANE_ACADEMY_AT_TOLARIA_WEST);

        // At the beginning of your end step, if you have 0 cards in hand, draw seven cards
        Ability ability = new BeginningOfEndStepTriggeredAbility(Zone.COMMAND, new DrawCardsActivePlayerEffect(7), TargetController.ANY, HellbentAPCondition.instance, false);
        this.getAbilities().add(ability);

        // Active player can roll the planar die: Whenever you roll {CHAOS}, discard your hand
        Effect chaosEffect = new DiscardHandControllerEffect();
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

    private AcademyAtTolariaWestPlane(final AcademyAtTolariaWestPlane plane) {
        super(plane);
    }

    @Override
    public AcademyAtTolariaWestPlane copy() {
        return new AcademyAtTolariaWestPlane(this);
    }
}

class DrawCardsActivePlayerEffect extends OneShotEffect {

    protected DynamicValue amount;

    public DrawCardsActivePlayerEffect(int amount) {
        this(StaticValue.get(amount));
    }

    public DrawCardsActivePlayerEffect(DynamicValue amount) {
        super(Outcome.DrawCard);
        this.amount = amount.copy();
        setText();
    }

    public DrawCardsActivePlayerEffect(final DrawCardsActivePlayerEffect effect) {
        super(effect);
        this.amount = effect.amount.copy();
    }

    @Override
    public DrawCardsActivePlayerEffect copy() {
        return new DrawCardsActivePlayerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Plane cPlane = game.getState().getCurrentPlane();
        if (cPlane == null) {
            return false;
        }
        if (!cPlane.getPlaneType().equals(Planes.PLANE_ACADEMY_AT_TOLARIA_WEST)) {
            return false;
        }
        Player player = game.getPlayer(game.getActivePlayerId());
        if (player != null) {
            player.drawCards(amount.calculate(game, source, this), source, game);
            return true;
        }
        return false;
    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        sb.append("draw ").append(CardUtil.numberToText(amount.toString())).append(" cards");
        staticText = sb.toString();
    }
}

enum HellbentAPCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getPlayer(game.getActivePlayerId()).getHand().isEmpty();
    }

    @Override
    public String toString() {
        return "if you have no cards in hand";
    }
}
