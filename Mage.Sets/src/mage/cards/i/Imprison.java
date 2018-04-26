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
package mage.cards.i;

import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AttacksOrBlocksEnchantedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.DestroySourceEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.RemoveFromCombatTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.stack.StackAbility;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.BlockedByOnlyOneCreatureThisCombatWatcher;

/**
 *
 * @author L_J
 */
public class Imprison extends CardImpl {

    public Imprison(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}");
        this.subtype.add(SubType.AURA);
        
        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Whenever a player activates an ability of enchanted creature with {T} in its activation cost that isn't a mana ability, you may pay {1}. If you do, counter that ability. If you don't, destroy Imprison.
        this.addAbility(new ImprisonTriggeredAbility());
        
        // Whenever enchanted creature attacks or blocks, you may pay {1}. If you do, tap the creature, remove it from combat, and creatures it was blocking that had become blocked by only that creature this combat become unblocked. If you don't, destroy Imprison.
        this.addAbility(new AttacksOrBlocksEnchantedTriggeredAbility(Zone.BATTLEFIELD, new DoIfCostPaid(new ImprisonUnblockEffect(), new DestroySourceEffect(), new ManaCostsImpl("1"))));
    }

    public Imprison(final Imprison card) {
        super(card);
    }

    @Override
    public Imprison copy() {
        return new Imprison(this);
    }
}

class ImprisonTriggeredAbility extends TriggeredAbilityImpl {

    ImprisonTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DoIfCostPaid(new CounterTargetEffect().setText("counter that ability"), new DestroySourceEffect(), new ManaCostsImpl("1")));
    }

    ImprisonTriggeredAbility(final ImprisonTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ImprisonTriggeredAbility copy() {
        return new ImprisonTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ACTIVATED_ABILITY;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = game.getPermanentOrLKIBattlefield(sourceId);
        if (enchantment != null && enchantment.getAttachedTo() != null) {
            Permanent enchantedPermanent = game.getPermanentOrLKIBattlefield(enchantment.getAttachedTo());
            if (event.getSourceId().equals(enchantedPermanent.getId())) {
                StackAbility stackAbility = (StackAbility) game.getStack().getStackObject(event.getSourceId());
                if (!(stackAbility.getStackAbility() instanceof ActivatedManaAbilityImpl)) {
                    String abilityText = stackAbility.getRule(true);
                    if (abilityText.contains("{T},") || abilityText.contains("{T}:") || abilityText.contains("{T} or")) {
                        getEffects().get(0).setTargetPointer(new FixedTarget(stackAbility.getId()));
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a player activates an ability of enchanted creature with {T} in its activation cost that isn't a mana ability, " + super.getRule();
    }
}

class ImprisonUnblockEffect extends OneShotEffect {

    public ImprisonUnblockEffect() {
        super(Outcome.Benefit);
        this.staticText = "tap the creature, remove it from combat, and creatures it was blocking that had become blocked by only that creature this combat become unblocked";
    }

    public ImprisonUnblockEffect(final ImprisonUnblockEffect effect) {
        super(effect);
    }

    @Override
    public ImprisonUnblockEffect copy() {
        return new ImprisonUnblockEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null && enchantment.getAttachedTo() != null) {
            Permanent permanent = game.getPermanent(enchantment.getAttachedTo());
            if (permanent != null) {
                if (permanent.isCreature()) {
                    
                    // Tap the creature
                    permanent.tap(game);
    
                    // Remove it from combat
                    Effect effect = new RemoveFromCombatTargetEffect();
                    effect.setTargetPointer(new FixedTarget(permanent.getId()));
                    effect.apply(game, source);
    
                    // Make blocked creatures unblocked
                    BlockedByOnlyOneCreatureThisCombatWatcher watcher = (BlockedByOnlyOneCreatureThisCombatWatcher) game.getState().getWatchers().get(BlockedByOnlyOneCreatureThisCombatWatcher.class.getSimpleName());
                    if (watcher != null) {
                        Set<CombatGroup> combatGroups = watcher.getBlockedOnlyByCreature(permanent.getId());
                        if (combatGroups != null) {
                            for (CombatGroup combatGroup : combatGroups) {
                                if (combatGroup != null) {
                                    combatGroup.setBlocked(false, game);
                                }
                            }
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
