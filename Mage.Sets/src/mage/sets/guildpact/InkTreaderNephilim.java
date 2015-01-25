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
package mage.sets.guildpact;

import java.util.Collection;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.UUID;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import java.util.Map;
import java.util.List;
import mage.target.Target;
import mage.filter.predicate.mageobject.FromSetPredicate;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.TargetAddress;

/**
 * @author duncant
 */
public class InkTreaderNephilim extends CardImpl {

    public InkTreaderNephilim(UUID ownerId) {
        super(ownerId, 117, "Ink-Treader Nephilim", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{R}{G}{W}{U}");
        this.expansionSetCode = "GPT";
        this.subtype.add("Nephilim");

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever a player casts an instant or sorcery spell, if that spell targets only Ink-Treader Nephilim, copy the spell for each other creature that spell could target. Each copy targets a different one of those creatures.
        this.addAbility(new InkTreaderNephilimTriggeredAbility());
    }

    public InkTreaderNephilim(final InkTreaderNephilim card) {
        super(card);
    }

    @Override
    public InkTreaderNephilim copy() {
        return new InkTreaderNephilim(this);
    }
}

class InkTreaderNephilimTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterSpell filter = new FilterSpell();

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)));
    }

    InkTreaderNephilimTriggeredAbility() {
        super(Zone.BATTLEFIELD, new InkTreaderNephilimEffect(), false);
    }

    InkTreaderNephilimTriggeredAbility(final InkTreaderNephilimTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public InkTreaderNephilimTriggeredAbility copy() {
        return new InkTreaderNephilimTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null &&
                (spell.getCardType().contains(CardType.INSTANT) || spell.getCardType().contains(CardType.SORCERY))){
                for (Effect effect : getEffects()) {
                    effect.setValue("TriggeringSpell", spell);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        Spell spell = (Spell) getEffects().get(0).getValue("TriggeringSpell");
        if (spell != null) {
            boolean allTargetsInkTreaderNephilim = true;
            boolean atLeastOneTargetsInkTreaderNephilim = false;
            for (TargetAddress addr : TargetAddress.walk(spell)) {
                Target targetInstance = addr.getTarget(spell);
                for (UUID target : targetInstance.getTargets()) {
                    allTargetsInkTreaderNephilim &= target.equals(sourceId);
                    atLeastOneTargetsInkTreaderNephilim |= target.equals(sourceId);
                }
            }
            if (allTargetsInkTreaderNephilim && atLeastOneTargetsInkTreaderNephilim) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a player casts an instant or sorcery spell, if that spell targets only {this}, copy the spell for each other creature that spell could target. Each copy targets a different one of those creatures.";
    }
}

class InkTreaderNephilimEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
    }

    public InkTreaderNephilimEffect() {
        super(Outcome.Copy);
        staticText = "copy the spell for each other creature that spell could target. Each copy targets a different one of those creatures";
    }

    public InkTreaderNephilimEffect(final InkTreaderNephilimEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Spell spell = (Spell) getValue("TriggeringSpell");
        if (spell != null) {
            Map<UUID, Spell> targetable = new HashMap<>();
            // gather all creatures that can be targeted from the spell to copy 
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, controller.getId(), source.getSourceId(), game)) {
                Spell copy = spell.copySpell();                
                copy.setControllerId(controller.getId());
                copy.setCopiedSpell(true);
                if (permanent.getId().equals(source.getSourceId())) {
                    continue; // copy only for other creatures
                }
                boolean legal = true;
                for (TargetAddress addr : TargetAddress.walk(copy)) {
                    Target targetInstance = addr.getTarget(copy);
                    legal &= targetInstance.canTarget(permanent.getId(), addr.getSpellAbility(copy), game);
                }
                if (legal) {
                    for (TargetAddress addr : TargetAddress.walk(copy)) {
                        Target targetInstance = addr.getTarget(copy);
                        int numTargets = targetInstance.getNumberOfTargets();
                        targetInstance.clearChosen();
                        while (numTargets > 0) {
                            targetInstance.add(permanent.getId(), game);
                            numTargets--;
                        }
                    }
                    targetable.put(permanent.getId(), copy);
                }
            }
            // controller 
            while (targetable.size() > 0) {
                FilterPermanent filterCreatures = new FilterPermanent("creature that spell could target ("+ targetable.size() + " remaining)");
                filterCreatures.add(new FromSetPredicate(targetable.keySet()));
                TargetPermanent target = new TargetPermanent(0, 1, filterCreatures, true);
                if (target.possibleTargets(controller.getId(), game).size() > 1
                    && target.canChoose(source.getSourceId(), controller.getId(), game)) {
                    controller.choose(Outcome.Neutral, target, source.getId(), game);
                }
                Collection<UUID> choosenIds = target.getTargets();
                if (choosenIds.isEmpty()) {
                    choosenIds = targetable.keySet();
                }
                List<UUID> toDelete = new ArrayList<>();
                for (UUID chosenId : choosenIds) {
                    Spell chosenCopy = targetable.get(chosenId);
                    if (chosenCopy != null) {
                        game.getStack().push(chosenCopy);
                        toDelete.add(chosenId);
                    }
                }
                for (UUID idToDelte : toDelete) {
                    targetable.remove(idToDelte);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public InkTreaderNephilimEffect copy() {
        return new InkTreaderNephilimEffect(this);
    }

}
