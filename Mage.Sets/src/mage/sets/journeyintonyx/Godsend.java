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
package mage.sets.journeyintonyx;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continious.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class Godsend extends CardImpl<Godsend> {

    public Godsend(UUID ownerId) {
        super(ownerId, 12, "Godsend", Rarity.MYTHIC, new CardType[]{CardType.ARTIFACT}, "{1}{W}{W}");
        this.expansionSetCode = "JOU";
        this.supertype.add("Legendary");
        this.subtype.add("Equipment");

        this.color.setWhite(true);

        // Equipped creature gets +3/+3.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(3,3,Duration.WhileOnBattlefield)));
        // Whenever equipped creature blocks or becomes blocked by one or more creatures, you may exile one of those creatures.
        this.addAbility(new GodsendTriggeredAbility());
        // Opponents can't cast cards with the same name as cards exiled with Godsend.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GodsendReplacementEffect()));
        // Equip {3}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(3)));
    }

    public Godsend(final Godsend card) {
        super(card);
    }

    @Override
    public Godsend copy() {
        return new Godsend(this);
    }
}

class GodsendTriggeredAbility extends TriggeredAbilityImpl<GodsendTriggeredAbility> {

    private Set<UUID> possibleTargets = new HashSet<>();
    
    GodsendTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GodsendExileEffect(), true);
    }

    GodsendTriggeredAbility(final GodsendTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GodsendTriggeredAbility copy() {
        return new GodsendTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType().equals(GameEvent.EventType.DECLARED_BLOCKERS)) {
            Permanent sourcePermanent = game.getPermanentOrLKIBattlefield((this.getSourceId()));
            if (sourcePermanent != null) {
                possibleTargets.clear();
                if (sourcePermanent.isAttacking()) {
                    for (CombatGroup group: game.getCombat().getBlockingGroups()) {
                        if (group.getAttackers().contains(this.getSourceId())) {
                            possibleTargets.addAll(group.getBlockers());
                        }
                    }
                    
                }
                if (sourcePermanent.getBlocking() > 0) {
                    for (CombatGroup group: game.getCombat().getBlockingGroups()) {
                        if (group.getBlockers().contains(this.getSourceId())) {
                            possibleTargets.addAll(group.getAttackers());
                        }
                    }
                    
                }
                if (possibleTargets.size() > 0) {
                    if (possibleTargets.size() == 1) {
                        this.getTargets().clear();
                        this.getEffects().get(0).setTargetPointer(new FixedTarget(possibleTargets.iterator().next()));
                    } else {
                        FilterCreaturePermanent filter = new FilterCreaturePermanent("one blocking or blocked creature");                        
                        List<PermanentIdPredicate> uuidPredicates = new ArrayList<>();
                        for (UUID creatureId : possibleTargets) {
                            uuidPredicates.add(new PermanentIdPredicate(creatureId));
                        }
                        filter.add(Predicates.or(uuidPredicates));
                        this.getTargets().add(new TargetCreaturePermanent(filter, true));
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever equipped creature blocks or becomes blocked by one or more creatures, you may exile one of those creatures.";
    }
}

class GodsendExileEffect extends OneShotEffect<GodsendExileEffect> {
    
    public GodsendExileEffect() {
        super(Outcome.Exile);
        this.staticText = "you may exile one of those creatures";
    }
    
    public GodsendExileEffect(final GodsendExileEffect effect) {
        super(effect);
    }
    
    @Override
    public GodsendExileEffect copy() {
        return new GodsendExileEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (creature != null && controller != null && sourcePermanent != null) {
            UUID exileId = CardUtil.getCardExileZoneId(game, source);
            controller.moveCardToExileWithInfo(creature, exileId, 
                    new StringBuilder(sourcePermanent.getName()).append(" (").append(sourcePermanent.getZoneChangeCounter()).append(")").toString()
                    , source.getSourceId(), game, Zone.BATTLEFIELD);
            
        }
        return false;
    }
}

class GodsendReplacementEffect extends ReplacementEffectImpl<GodsendReplacementEffect> {

    public GodsendReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Opponents can't cast cards with the same name as cards exiled with {this}";
    }

    public GodsendReplacementEffect(final GodsendReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public GodsendReplacementEffect copy() {
        return new GodsendReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == EventType.CAST_SPELL && game.getOpponents(source.getControllerId()).contains(event.getPlayerId())) {
            List<String> nameOfExiledCards = new ArrayList<>();
            // game.getExile()
//            MageObject object = game.getObject(event.getSourceId());
//            if (object != null && object.getName().equals(game.getState().getValue(source.getSourceId().toString()))) {
//                return true;
//            }
        }
        return false;
    }
}
