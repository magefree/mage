package mage.cards.o;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.Mode;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfPlayersNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;
import mage.target.targetpointer.FixedTargets;
import mage.watchers.common.AttackedThisTurnWatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author emerald000
 */
public final class OracleEnVec extends CardImpl {

    public OracleEnVec(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {T}: Target opponent chooses any number of creatures they control. During that player’s next turn, the chosen
        // creatures attack if able, and other creatures can’t attack. At the beginning of that turn’s end step,
        // destroy each of the chosen creatures that didn’t attack this turn. Activate this ability only during your turn.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new OracleEnVecEffect(), new TapSourceCost(), MyTurnCondition.instance
        );
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private OracleEnVec(final OracleEnVec card) {
        super(card);
    }

    @Override
    public OracleEnVec copy() {
        return new OracleEnVec(this);
    }
}

class OracleEnVecEffect extends OneShotEffect {

    OracleEnVecEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target opponent chooses any number of creatures they control. During that player's next turn, "
                + "the chosen creatures attack if able, and other creatures can't attack. At the beginning of that turn's end step, "
                + "destroy each of the chosen creatures that didn't attack this turn";
    }

    private OracleEnVecEffect(final OracleEnVecEffect effect) {
        super(effect);
    }

    @Override
    public OracleEnVecEffect copy() {
        return new OracleEnVecEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (opponent == null) {
            return false;
        }
        Target target = new TargetControlledCreaturePermanent(0, Integer.MAX_VALUE);
        target.withNotTarget(true);
        if (!target.choose(Outcome.Neutral, opponent.getId(), source.getSourceId(), source, game)) {
            return false;
        }
        List<Permanent> chosenPermanents = new ArrayList<>();
        for (Permanent permanent : game.getBattlefield().getActivePermanents(new FilterControlledCreaturePermanent(), opponent.getId(), source, game)) {
            if (target.getTargets().contains(permanent.getId())) {
                chosenPermanents.add(permanent);
                RequirementEffect effect = new OracleEnVecMustAttackRequirementEffect();
                effect.setTargetPointer(new FixedTarget(permanent, game));
                game.addEffect(effect, source);
            } else {
                RestrictionEffect effect = new OracleEnVecCantAttackRestrictionEffect();
                effect.setTargetPointer(new FixedTarget(permanent, game));
                game.addEffect(effect, source);
            }
        }
        Effect destroyEffect = new OracleEnVecDestroyEffect();
        destroyEffect.setTargetPointer(new FixedTargets(chosenPermanents, game));
        DelayedTriggeredAbility ability = new AtTheBeginOfPlayersNextEndStepDelayedTriggeredAbility(
                destroyEffect, opponent.getId()
        ).setTriggerPhrase("At the beginning of that turn's end step, ");
        game.addDelayedTriggeredAbility(ability, source);
        return true;
    }
}

class OracleEnVecMustAttackRequirementEffect extends RequirementEffect {

    OracleEnVecMustAttackRequirementEffect() {
        super(Duration.UntilEndOfYourNextTurn);
    }

    private OracleEnVecMustAttackRequirementEffect(final OracleEnVecMustAttackRequirementEffect effect) {
        super(effect);
    }

    @Override
    public OracleEnVecMustAttackRequirementEffect copy() {
        return new OracleEnVecMustAttackRequirementEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return this.getTargetPointer().getFirst(game, source) != null
                && this.getTargetPointer().getFirst(game, source).equals(permanent.getId())
                && this.isYourNextTurn(game);
    }

    @Override
    public boolean mustAttack(Game game) {
        return true;
    }

    @Override
    public boolean mustBlock(Game game) {
        return false;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        Permanent perm = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (perm != null) {
            setStartingControllerAndTurnNum(game, perm.getControllerId(), game.getActivePlayerId()); // setup startingController to calc isYourTurn calls
        } else {
            discard();
        }
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        return game.getTurnPhaseType() == TurnPhase.END && this.isYourNextTurn(game);
    }

    @Override
    public String getText(Mode mode) {
        return "{this} attack if able.";
    }
}

class OracleEnVecCantAttackRestrictionEffect extends RestrictionEffect {

    OracleEnVecCantAttackRestrictionEffect() {
        super(Duration.Custom);
        this.staticText = "{this} can't attack.";
    }

    private OracleEnVecCantAttackRestrictionEffect(final OracleEnVecCantAttackRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public OracleEnVecCantAttackRestrictionEffect copy() {
        return new OracleEnVecCantAttackRestrictionEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return this.getTargetPointer().getFirst(game, source) != null
                && this.getTargetPointer().getFirst(game, source).equals(permanent.getId());
    }

    @Override
    public boolean canAttack(Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        Permanent perm = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (perm != null) {
            setStartingControllerAndTurnNum(game, perm.getControllerId(), game.getActivePlayerId()); // setup startingController to calc isYourTurn calls
        } else {
            discard();
        }
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        return game.getTurnPhaseType() == TurnPhase.END && this.isYourNextTurn(game);
    }
}

class OracleEnVecDestroyEffect extends OneShotEffect {

    OracleEnVecDestroyEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "destroy each of the chosen creatures that didn't attack";
    }

    private OracleEnVecDestroyEffect(final OracleEnVecDestroyEffect effect) {
        super(effect);
    }

    @Override
    public OracleEnVecDestroyEffect copy() {
        return new OracleEnVecDestroyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        AttackedThisTurnWatcher watcher = game.getState().getWatcher(AttackedThisTurnWatcher.class);
        if (watcher == null) {
            return false;
        }
        for (UUID targetId : getTargetPointer().getTargets(game, source)) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null
                    && permanent.isPhasedIn()
                    && !watcher.getAttackedThisTurnCreatures().contains(new MageObjectReference(permanent, game))) {
                permanent.destroy(source, game, false);
            }
        }
        return true;
    }
}
