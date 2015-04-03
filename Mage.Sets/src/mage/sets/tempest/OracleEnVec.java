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
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
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
package mage.sets.tempest;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
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
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
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
public class OracleEnVec extends CardImpl {

    public OracleEnVec(UUID ownerId) {
        super(ownerId, 243, "Oracle en-Vec", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.expansionSetCode = "TMP";
        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Target opponent chooses any number of creatures he or she controls. During that player's next turn, the chosen creatures attack if able, and other creatures can't attack. At the beginning of that turn's end step, destroy each of the chosen creatures that didn't attack. Activate this ability only during your turn.
        Ability ability = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD, new OracleEnVecEffect(), new TapSourceCost(), MyTurnCondition.getInstance());
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
                    }
                    else {
                        RestrictionEffect effect = new OracleEnVecCantAttackRestrictionEffect();
                        effect.setTargetPointer(new FixedTarget(permanent.getId()));
                        game.addEffect(effect, source);
                    }
                }
                DelayedTriggeredAbility delayedAbility = new OracleEnVecDelayedTriggeredAbility(game.getTurnNum(), target.getTargets());
                delayedAbility.setSourceId(source.getSourceId());
                delayedAbility.setControllerId(source.getControllerId());
                delayedAbility.setSourceObject(source.getSourceObject(game), game);
                game.addDelayedTriggeredAbility(delayedAbility);
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
                && game.getActivePlayerId().equals(this.getTargetPointer().getFirst(game, source)));
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
                && game.getActivePlayerId().equals(this.getTargetPointer().getFirst(game, source)));
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
        return startingTurn != game.getTurnNum() && game.getActivePlayerId().equals(event.getPlayerId());
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
    
    OracleEnVecDestroyEffect(List<UUID> chosenCreatures) {
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
        AttackedThisTurnWatcher watcher = (AttackedThisTurnWatcher) game.getState().getWatchers().get("AttackedThisTurn");
        if (watcher != null) {
            for (UUID targetId : chosenCreatures) {
                if (!watcher.getAttackedThisTurnCreatures().contains(targetId)) {
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
