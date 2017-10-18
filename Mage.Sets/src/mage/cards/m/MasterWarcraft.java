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
package mage.cards.m;

import java.util.*;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.CastOnlyDuringPhaseStepSourceAbility;
import mage.abilities.condition.common.BeforeAttackersAreDeclaredCondition;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.combat.AttacksIfAbleTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author L_J
 */
public class MasterWarcraft extends CardImpl {

    public MasterWarcraft(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R/W}{R/W}");

        // Cast Master Warcraft only before attackers are declared.
        this.addAbility(new CastOnlyDuringPhaseStepSourceAbility(null, null, BeforeAttackersAreDeclaredCondition.instance));

        // You choose which creatures attack this turn.
        this.getSpellAbility().addEffect(new MasterWarcraftChooseAttackersEffect());

        // You choose which creatures block this turn and how those creatures block.
        this.getSpellAbility().addEffect(new MasterWarcraftChooseBlockersEffect());
    }

    public MasterWarcraft(final MasterWarcraft card) {
        super(card);
    }

    @Override
    public MasterWarcraft copy() {
        return new MasterWarcraft(this);
    }
}

class MasterWarcraftChooseAttackersEffect extends ContinuousRuleModifyingEffectImpl {

    public MasterWarcraftChooseAttackersEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit, false, false);
        staticText = "You choose which creatures attack this turn";
    }

    public MasterWarcraftChooseAttackersEffect(final MasterWarcraftChooseAttackersEffect effect) {
        super(effect);
    }

    @Override
    public MasterWarcraftChooseAttackersEffect copy() {
        return new MasterWarcraftChooseAttackersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARING_ATTACKERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player chooser = game.getPlayer(source.getControllerId());
        Player attackingPlayer = game.getPlayer(game.getCombat().getAttackingPlayerId());
        if (chooser != null && attackingPlayer != null && !attackingPlayer.getAvailableAttackers(game).isEmpty()) {
            for (Permanent permanent : game.getBattlefield().getActivePermanents(new FilterCreaturePermanent(), source.getControllerId(), source.getSourceId(), game)) {
                // Clears previous instances of "should attack" effects
                // ("shouldn't attack" effects don't need cleaning because MasterWarcraftMustAttackRequirementEffect overrides them)
                for (Map.Entry<RequirementEffect, Set<Ability>> entry : game.getContinuousEffects().getApplicableRequirementEffects(permanent, false, game).entrySet()) {
                    RequirementEffect effect = entry.getKey();
                    if (effect instanceof MasterWarcraftMustAttackRequirementEffect) {
                        effect.discard();
                    }
                }
            }
            new MasterWarcraftAttackEffect().apply(game, source); // Master Warcraft imposes its effect right before the attackers being declared...
        }
        return false; // ...and then resumes the attack declaration for the active player as normal
    }
}

class MasterWarcraftAttackEffect extends OneShotEffect {

    MasterWarcraftAttackEffect() {
        super(Outcome.Benefit);
    }

    MasterWarcraftAttackEffect(final MasterWarcraftAttackEffect effect) {
        super(effect);
    }

    @Override
    public MasterWarcraftAttackEffect copy() {
        return new MasterWarcraftAttackEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            // TODO: find a way to undo creature selection
            Target target = new TargetCreaturePermanent(0, Integer.MAX_VALUE, new FilterCreaturePermanent("creatures that will attack this combat (creatures not chosen won't attack this combat)"), true);
            if (target.choose(Outcome.Neutral, source.getControllerId(), source.getSourceId(), game)) {
                for (Permanent permanent : game.getBattlefield().getActivePermanents(new FilterCreaturePermanent(), source.getControllerId(), source.getSourceId(), game)) {
                    
                    // Choose creatures that will be attacking this combat
                    if (target.getTargets().contains(permanent.getId())) {
                        RequirementEffect effect = new MasterWarcraftMustAttackRequirementEffect();
                        effect.setText("");
                        effect.setTargetPointer(new FixedTarget(permanent.getId()));
                        game.addEffect(effect, source);
                        // TODO: find a better way to report attackers to game log
                        // game.informPlayers(controller.getLogName() + " has decided that " + permanent.getLogName() + " should attack this combat if able");
                        
                    // All other creatures can't attack
                    } else {
                        RestrictionEffect effect = new MasterWarcraftCantAttackRestrictionEffect();
                        effect.setText("");
                        effect.setTargetPointer(new FixedTarget(permanent.getId()));
                        game.addEffect(effect, source);
                    }
                    
                }
                return true;
            }
        }
        return false;
    }
}

class MasterWarcraftMustAttackRequirementEffect extends AttacksIfAbleTargetEffect {

    MasterWarcraftMustAttackRequirementEffect() {
        super(Duration.EndOfCombat);
    }

    MasterWarcraftMustAttackRequirementEffect(final MasterWarcraftMustAttackRequirementEffect effect) {
        super(effect);
    }

    @Override
    public MasterWarcraftMustAttackRequirementEffect copy() {
        return new MasterWarcraftMustAttackRequirementEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (discarded) {
            return false;
        }
        return this.getTargetPointer().getTargets(game, source).contains(permanent.getId());
    }
}

class MasterWarcraftCantAttackRestrictionEffect extends RestrictionEffect {

    MasterWarcraftCantAttackRestrictionEffect() {
        super(Duration.EndOfCombat);
    }

    MasterWarcraftCantAttackRestrictionEffect(final MasterWarcraftCantAttackRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public MasterWarcraftCantAttackRestrictionEffect copy() {
        return new MasterWarcraftCantAttackRestrictionEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return this.getTargetPointer().getFirst(game, source).equals(permanent.getId());
    }

    @Override
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game) {
        for (Map.Entry<RequirementEffect, Set<Ability>> entry : game.getContinuousEffects().getApplicableRequirementEffects(attacker, false, game).entrySet()) {
            RequirementEffect effect = entry.getKey();
            if (effect.mustAttack(game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return "Unless {this} must attack, {this} can't attack.";
    }
}

class MasterWarcraftChooseBlockersEffect extends ContinuousRuleModifyingEffectImpl { // TODO: reverse the resolution order in case of effect multiples

    public MasterWarcraftChooseBlockersEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit, false, false);
        staticText = "You choose which creatures block this turn and how those creatures block";
    }

    public MasterWarcraftChooseBlockersEffect(final MasterWarcraftChooseBlockersEffect effect) {
        super(effect);
    }

    @Override
    public MasterWarcraftChooseBlockersEffect copy() {
        return new MasterWarcraftChooseBlockersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARING_BLOCKERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player blockController = game.getPlayer(source.getControllerId());
        if (blockController != null) {
            game.getCombat().selectBlockers(blockController, game);
            return true;
        }
        return false;
    }
}
