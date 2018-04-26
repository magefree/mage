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
package mage.cards.r;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByAllTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.permanent.PermanentInListPredicate;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 *
 * @author L_J
 */
public class RagingRiver extends CardImpl {

    public RagingRiver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}{R}");

        // Whenever one or more creatures you control attack, each defending player divides all creatures without flying he or she controls into a "left" pile and a "right" pile. Then, for each attacking creature you control, choose "left" or "right." That creature can't be blocked this combat except by creatures with flying and creatures in a pile with the chosen label.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(new RagingRiverEffect(), 1));
    }

    public RagingRiver(final RagingRiver card) {
        super(card);
    }

    @Override
    public RagingRiver copy() {
        return new RagingRiver(this);
    }
}

class RagingRiverEffect extends OneShotEffect {

    public RagingRiverEffect() {
        super(Outcome.Detriment);
        staticText = "each defending player divides all creatures without flying he or she controls into a \"left\" pile and a \"right\" pile. Then, for each attacking creature you control, choose \"left\" or \"right.\" That creature can't be blocked this combat except by creatures with flying and creatures in a pile with the chosen label";
    }

    public RagingRiverEffect(final RagingRiverEffect effect) {
        super(effect);
    }

    @Override
    public RagingRiverEffect copy() {
        return new RagingRiverEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            List<Permanent> left = new ArrayList<>();
            List<Permanent> right = new ArrayList<>();

            for (UUID defenderId : game.getCombat().getPlayerDefenders(game)) {
                Player defender = game.getPlayer(defenderId);
                if (defender != null) {
                    List<Permanent> leftLog = new ArrayList<>();
                    List<Permanent> rightLog = new ArrayList<>();
                    FilterControlledCreaturePermanent filterBlockers = new FilterControlledCreaturePermanent("creatures without flying you control to assign to the \"left\" pile (creatures not chosen will be assigned to the \"right\" pile)");
                    filterBlockers.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
                    Target target = new TargetControlledCreaturePermanent(0, Integer.MAX_VALUE, filterBlockers, true);
                    if (target.canChoose(source.getSourceId(), defenderId, game)) {
                        if (defender.chooseTarget(Outcome.Neutral, target, source, game)) {
                            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), defenderId, game)) {
                                if (target.getTargets().contains(permanent.getId())) {
                                    left.add(permanent);
                                    leftLog.add(permanent);
                                } 
                                else if (filterBlockers.match(permanent, source.getSourceId(), defenderId, game)) {
                                    right.add(permanent);
                                    rightLog.add(permanent);
                                }
                            }
                        }
                        
                        // it could be nice to invoke some graphic indicator of which creature is Left or Right in this spot
                        StringBuilder sb = new StringBuilder("Left pile of ").append(defender.getLogName()).append(": ");
                        sb.append(leftLog.stream().map(MageObject::getLogName).collect(Collectors.joining(", ")));

                        game.informPlayers(sb.toString());
                        
                        sb = new StringBuilder("Right pile of ").append(defender.getLogName()).append(": ");
                        sb.append(rightLog.stream().map(MageObject::getLogName).collect(Collectors.joining(", ")));

                        game.informPlayers(sb.toString());
                    }
                }
            }

            for (UUID attackers : game.getCombat().getAttackers()) {
                Permanent attacker = game.getPermanent(attackers);
                if (attacker != null && attacker.getControllerId() == controller.getId()) {
                    CombatGroup combatGroup = game.getCombat().findGroup(attacker.getId());
                    if (combatGroup != null) {
                        FilterCreaturePermanent filter = new FilterCreaturePermanent();
                        Player defender = game.getPlayer(combatGroup.getDefendingPlayerId());
                        if (defender != null) {
                            if (left.isEmpty() && right.isEmpty()) {
                                // shortcut in case of no labeled blockers available
                                filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
                            } else {
                                List<Permanent> leftLog = left.stream()
                                        .filter(permanent -> permanent.getControllerId() != null)
                                        .filter(permanent -> permanent.getControllerId().equals(defender.getId()))
                                        .collect(Collectors.toList());
                                List<Permanent> rightLog = right.stream()
                                        .filter(permanent -> permanent.getControllerId() != null)
                                        .filter(permanent -> permanent.getControllerId().equals(defender.getId()))
                                        .collect(Collectors.toList());

                                
                                if (controller.choosePile(outcome, attacker.getName() + ": attacking " + defender.getName(), leftLog, rightLog, game)) {
                                    filter.add(Predicates.not(Predicates.or(new AbilityPredicate(FlyingAbility.class), new PermanentInListPredicate(left))));
                                    game.informPlayers(attacker.getLogName() + ": attacks left (" + defender.getLogName() + ")");
                                } else {
                                    filter.add(Predicates.not(Predicates.or(new AbilityPredicate(FlyingAbility.class), new PermanentInListPredicate(right))));
                                    game.informPlayers(attacker.getLogName() + ": attacks right (" + defender.getLogName() + ")");
                                }
                            }
                            RestrictionEffect effect = new CantBeBlockedByAllTargetEffect(filter, Duration.EndOfCombat);
                            effect.setTargetPointer(new FixedTarget(attacker.getId()));
                            game.addEffect(effect, source);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
