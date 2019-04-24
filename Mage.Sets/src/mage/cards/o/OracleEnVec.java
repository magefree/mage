
package mage.cards.o;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.Mode;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TurnPhase;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.AttackedThisTurnWatcher;

/**
 *
 * @author emerald000
 */
public final class OracleEnVec extends CardImpl {

    public OracleEnVec(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Target opponent chooses any number of creatures he or she controls. During that player's next turn, the chosen creatures attack if able, and other creatures can't attack. At the beginning of that turn's end step, destroy each of the chosen creatures that didn't attack. Activate this ability only during your turn.
        Ability ability = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD, new OracleEnVecEffect(), new TapSourceCost(), MyTurnCondition.instance);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability, new AttackedThisTurnWatcher());
    }

    public OracleEnVec(final OracleEnVec card) {
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
        this.staticText = "Target opponent chooses any number of creatures he or she controls. During that player's next turn, the chosen creatures attack if able, and other creatures can't attack. At the beginning of that turn's end step, destroy each of the chosen creatures that didn't attack";
    }

    OracleEnVecEffect(final OracleEnVecEffect effect) {
        super(effect);
    }

    @Override
    public OracleEnVecEffect copy() {
        return new OracleEnVecEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (opponent != null) {
            Target target = new TargetControlledCreaturePermanent(0, Integer.MAX_VALUE, new FilterControlledCreaturePermanent(), true);
            if (target.choose(Outcome.Neutral, opponent.getId(), source.getSourceId(), game)) {
                for (Permanent permanent : game.getBattlefield().getActivePermanents(new FilterControlledCreaturePermanent(), opponent.getId(), source.getSourceId(), game)) {
                    if (target.getTargets().contains(permanent.getId())) {
                        RequirementEffect effect = new OracleEnVecMustAttackRequirementEffect();
                        effect.setTargetPointer(new FixedTarget(permanent.getId()));
                        game.addEffect(effect, source);
                    } else {
                        RestrictionEffect effect = new OracleEnVecCantAttackRestrictionEffect();
                        effect.setTargetPointer(new FixedTarget(permanent.getId()));
                        game.addEffect(effect, source);
                    }
                }
                game.addDelayedTriggeredAbility(new OracleEnVecDelayedTriggeredAbility(game.getTurnNum(), target.getTargets()), source);
                return true;
            }
        }
        return false;
    }
}

class OracleEnVecMustAttackRequirementEffect extends RequirementEffect {

    OracleEnVecMustAttackRequirementEffect() {
        super(Duration.Custom);
    }

    OracleEnVecMustAttackRequirementEffect(final OracleEnVecMustAttackRequirementEffect effect) {
        super(effect);
    }

    @Override
    public OracleEnVecMustAttackRequirementEffect copy() {
        return new OracleEnVecMustAttackRequirementEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return this.getTargetPointer().getFirst(game, source).equals(permanent.getId());
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
    public boolean isInactive(Ability source, Game game) {
        return startingTurn != game.getTurnNum()
                && (game.getPhase().getType() == TurnPhase.END
                && game.isActivePlayer(this.getTargetPointer().getFirst(game, source)));
    }

    @Override
    public String getText(Mode mode) {
        return "{this} attack if able.";
    }
}

class OracleEnVecCantAttackRestrictionEffect extends RestrictionEffect {

    OracleEnVecCantAttackRestrictionEffect() {
        super(Duration.Custom);
    }

    OracleEnVecCantAttackRestrictionEffect(final OracleEnVecCantAttackRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public OracleEnVecCantAttackRestrictionEffect copy() {
        return new OracleEnVecCantAttackRestrictionEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return this.getTargetPointer().getFirst(game, source).equals(permanent.getId());
    }

    @Override
    public boolean canAttack(Game game) {
        return false;
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        return startingTurn != game.getTurnNum()
                && (game.getPhase().getType() == TurnPhase.END
                && game.isActivePlayer(this.getTargetPointer().getFirst(game, source)));
    }

    @Override
    public String getText(Mode mode) {
        return "{this} can't attack.";
    }
}

class OracleEnVecDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private final int startingTurn;

    OracleEnVecDelayedTriggeredAbility(int startingTurn, List<UUID> chosenCreatures) {
        super(new OracleEnVecDestroyEffect(chosenCreatures), Duration.EndOfGame, true);
        this.startingTurn = startingTurn;
    }

    OracleEnVecDelayedTriggeredAbility(final OracleEnVecDelayedTriggeredAbility ability) {
        super(ability);
        this.startingTurn = ability.startingTurn;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.END_TURN_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return startingTurn != game.getTurnNum() && game.isActivePlayer(event.getPlayerId());
    }

    @Override
    public OracleEnVecDelayedTriggeredAbility copy() {
        return new OracleEnVecDelayedTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "At the beginning of that turn's end step, destroy each of the chosen creatures that didn't attack.";
    }
}

class OracleEnVecDestroyEffect extends OneShotEffect {

    private final List<UUID> chosenCreatures;

    OracleEnVecDestroyEffect(List<UUID> chosenCreatures) { // need to be changed to MageObjectReference
        super(Outcome.DestroyPermanent);
        this.chosenCreatures = chosenCreatures;
        this.staticText = "destroy each of the chosen creatures that didn't attack";
    }

    OracleEnVecDestroyEffect(final OracleEnVecDestroyEffect effect) {
        super(effect);
        this.chosenCreatures = effect.chosenCreatures;
    }

    @Override
    public OracleEnVecDestroyEffect copy() {
        return new OracleEnVecDestroyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        AttackedThisTurnWatcher watcher = (AttackedThisTurnWatcher) game.getState().getWatchers().get(AttackedThisTurnWatcher.class.getSimpleName());
        if (watcher != null) {
            for (UUID targetId : chosenCreatures) {
                Permanent permanent = game.getPermanent(targetId);
                if (permanent != null && !watcher.getAttackedThisTurnCreatures().contains(new MageObjectReference(permanent, game))) {
                    Effect effect = new DestroyTargetEffect();
                    effect.setTargetPointer(new FixedTarget(targetId));
                    effect.apply(game, source);
                }
            }
            return true;
        }
        return false;
    }
}
