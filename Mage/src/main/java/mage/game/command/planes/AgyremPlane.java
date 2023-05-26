package mage.game.command.planes;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.common.MainPhaseStackEmptyCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.abilities.effects.common.RollPlanarDieEffect;
import mage.abilities.effects.common.cost.PlanarDieRollCostIncreasingEffect;
import mage.cards.Card;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.command.Plane;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.PlanarRollWatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author spjspj
 */
public class AgyremPlane extends Plane {

    private static final FilterCreaturePermanent filterWhite = new FilterCreaturePermanent("a white creature");
    private static final FilterCreaturePermanent filterNonWhite = new FilterCreaturePermanent("a nonwhite creature");

    static {
        filterWhite.add(new ColorPredicate(ObjectColor.WHITE));
        filterNonWhite.add(Predicates.not(new ColorPredicate(ObjectColor.WHITE)));
    }

    public AgyremPlane() {
        this.setPlaneType(Planes.PLANE_AGYREM);

        // Whenever a white creature dies, return it to the battlefield under its owner's control at the beginning of the next end step
        DiesCreatureTriggeredAbility ability = new DiesCreatureTriggeredAbility(Zone.COMMAND, new AgyremEffect(), false, filterWhite, true);
        this.getAbilities().add(ability);
        // Whenever a nonwhite creature dies, return it to its owner's hand at the beginning of the next end step.
        DiesCreatureTriggeredAbility ability2 = new DiesCreatureTriggeredAbility(Zone.COMMAND, new AgyremEffect2(), false, filterNonWhite, true);
        this.getAbilities().add(ability2);

        // Active player can roll the planar die: Whenever you roll {CHAOS}, creatures can't attack you until a player planeswalks
        Effect chaosEffect = new AgyremRestrictionEffect();
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

    private AgyremPlane(final AgyremPlane plane) {
        super(plane);
    }

    @Override
    public AgyremPlane copy() {
        return new AgyremPlane(this);
    }
}

class AgyremEffect extends OneShotEffect {

    public AgyremEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "return that card to the battlefield under its owner's control at the beginning of the next end step";
    }

    public AgyremEffect(final AgyremEffect effect) {
        super(effect);
    }

    @Override
    public AgyremEffect copy() {
        return new AgyremEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (card != null) {
            Effect effect = new ReturnToBattlefieldUnderOwnerControlTargetEffect(false, false);
            effect.setTargetPointer(new FixedTarget(card, game));
            effect.setText("return that card to the battlefield under its owner's control at the beginning of the next end step");
            game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect, TargetController.ANY), source);
            return true;
        }
        return false;
    }
}

class AgyremEffect2 extends OneShotEffect {

    public AgyremEffect2() {
        super(Outcome.PutCardInPlay);
        this.staticText = "return it to its owner's hand at the beginning of the next end step";
    }

    public AgyremEffect2(final AgyremEffect2 effect) {
        super(effect);
    }

    @Override
    public AgyremEffect2 copy() {
        return new AgyremEffect2(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (card != null) {
            Effect effect = new ReturnFromGraveyardToHandTargetEffect();
            effect.setTargetPointer(new FixedTarget(card, game));
            effect.setText("return it to its owner's hand at the beginning of the next end step");
            game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect, TargetController.ANY), source);
            return true;
        }
        return false;
    }
}

class AgyremRestrictionEffect extends RestrictionEffect {

    AgyremRestrictionEffect() {
        super(Duration.Custom, Outcome.Benefit);
        staticText = "Creatures can't attack you";
    }

    AgyremRestrictionEffect(final AgyremRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.isCreature(game);
    }

    @Override
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game, boolean canUseChooseDialogs) {
        if (defenderId == null) {
            return true;
        }

        Plane cPlane = game.getState().getCurrentPlane();
        if (cPlane != null && cPlane.getPlaneType().equals(Planes.PLANE_AGYREM)) {
            return !defenderId.equals(source.getControllerId());
        }
        return true;
    }

    @Override
    public AgyremRestrictionEffect copy() {
        return new AgyremRestrictionEffect(this);
    }
}