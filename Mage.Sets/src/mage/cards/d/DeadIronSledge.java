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
package mage.cards.d;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BlocksAttachedTriggeredAbility;
import mage.abilities.common.BlocksOrBecomesBlockedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.DestroySourceEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.BlockedByIdPredicate;
import mage.filter.predicate.permanent.BlockingAttackerIdPredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FirstTargetPointer;
import mage.target.targetpointer.FixedTarget;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

/**
 *
 * @author jerekwilson
 */
public class DeadIronSledge extends CardImpl {

    public DeadIronSledge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");
        
        this.subtype.add(SubType.EQUIPMENT);

        // Whenever equipped creature blocks or becomes blocked by a creature, destroy both creatures.
        this.addAbility(new DeadIronSledgeTriggeredAbility());
       
        
        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2)));
    }

    public DeadIronSledge(final DeadIronSledge card) {
        super(card);
    }

    @Override
    public DeadIronSledge copy() {
        return new DeadIronSledge(this);
    }
     
}
class DeadIronSledgeTriggeredAbility extends TriggeredAbilityImpl {

    private Set<UUID> possibleTargets = new HashSet<>();
    
    DeadIronSledgeTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DeadIronSledgeDestroyEffect(), false);
    }

    DeadIronSledgeTriggeredAbility(final DeadIronSledgeTriggeredAbility ability) {
        super(ability);
    }
    
    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_BLOCKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        List<Permanent> targetPermanents = new ArrayList<>();
        Permanent equipment = game.getPermanentOrLKIBattlefield((this.getSourceId()));
        if (equipment != null && equipment.getAttachedTo() != null) {
            Permanent equippedPermanent = game.getPermanentOrLKIBattlefield((equipment.getAttachedTo()));
            if (equippedPermanent != null) {
                possibleTargets.clear();
                if(equippedPermanent.isBlocked(game)){
                    possibleTargets.add(equippedPermanent.getId()); //add equipped creature to target list
                }
                String targetName = "";
                if (equippedPermanent.isAttacking()) {
                    for (CombatGroup group : game.getCombat().getGroups()) {
                        if (group.getAttackers().contains(equippedPermanent.getId())) {
                            possibleTargets.addAll(group.getBlockers());
                        }
                    }
                    targetName = "a creature blocking attacker ";
                } else if (equippedPermanent.getBlocking() > 0) {
                    for (CombatGroup group : game.getCombat().getGroups()) {
                        if (group.getBlockers().contains(equippedPermanent.getId())) {
                            possibleTargets.addAll(group.getAttackers());
                        }
                    }
                    targetName = "a creature blocked by creature ";
                }
                if (!possibleTargets.isEmpty()) {
                    this.getTargets().clear();
                    
                    for (UUID creatureId : possibleTargets) {
                        Permanent target = game.getPermanentOrLKIBattlefield(creatureId);
                        targetPermanents.add(target);
                    }
                    
                    this.getEffects().get(0).setTargetPointer(new FixedTargets(targetPermanents,game));
                    
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public TriggeredAbility copy() {
        return new DeadIronSledgeTriggeredAbility(this);
    }
    
    @Override
    public String getRule() {
        return "Whenever equipped creature blocks or becomes blocked by a creature, destroy both creatures.";
    }
}

class DeadIronSledgeDestroyEffect extends OneShotEffect {

    public DeadIronSledgeDestroyEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "destroy both creatures";
    }

    public DeadIronSledgeDestroyEffect(final DeadIronSledgeDestroyEffect effect) {
        super(effect);
    }

    @Override
    public DeadIronSledgeDestroyEffect copy() {
        return new DeadIronSledgeDestroyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        
        List<UUID> targets = this.getTargetPointer().getTargets(game, source);
        for(UUID target: targets){
            Permanent permanent = game.getPermanentOrLKIBattlefield(target);
            permanent.destroy(target, game, false);
        }
        return true;
    }
}
