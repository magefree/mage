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
package mage.sets.timespiral;

import java.util.UUID;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.common.PutIntoGraveFromBattlefieldSourceTriggeredAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
/**
 *
 * @author fireshoes
 */
public class SpiritLoop extends CardImpl {

    public SpiritLoop(UUID ownerId) {
        super(ownerId, 42, "Spirit Loop", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");
        this.expansionSetCode = "TSP";
        this.subtype.add("Aura");


        // Enchant creature you control
        TargetPermanent auraTarget = new TargetControlledCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        
        // Whenever enchanted creature deals damage, you gain that much life.
        this.addAbility(new SpiritLoopTriggeredAbility());
        
        // When Spirit Loop is put into a graveyard from the battlefield, return Spirit Loop to its owner's hand.
        this.addAbility(new PutIntoGraveFromBattlefieldSourceTriggeredAbility(new ReturnToHandSourceEffect()));
        
    }

    public SpiritLoop(final SpiritLoop card) {
        super(card);
    }

    @Override
    public SpiritLoop copy() {
        return new SpiritLoop(this);
    }
}

class SpiritLoopTriggeredAbility extends TriggeredAbilityImpl {
        
    public SpiritLoopTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SpiritLoopEffect(), false);
    }
    
    public SpiritLoopTriggeredAbility(final SpiritLoopTriggeredAbility ability) {
        super(ability);
    }
    
    @Override
    public SpiritLoopTriggeredAbility copy() {
        return new SpiritLoopTriggeredAbility(this);
    }
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType().equals(GameEvent.EventType.DAMAGED_CREATURE)
                || event.getType().equals(GameEvent.EventType.DAMAGED_PLAYER)
                || event.getType().equals(GameEvent.EventType.DAMAGED_PLANESWALKER)) {
            Permanent enchantment = game.getPermanent(this.getSourceId());
            if (enchantment == null || enchantment.getAttachedTo() == null) {
                return false;
            }
            Permanent enchanted = game.getPermanent(enchantment.getAttachedTo());
            if (enchanted != null && event.getSourceId().equals(enchanted.getId())) {
                for (Effect effect : this.getEffects()) {
                    effect.setValue("damage", event.getAmount());
                }
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String getRule() {
        return "Whenever enchanted creature deals damage, " + super.getRule();
    }
}

class SpiritLoopEffect extends OneShotEffect {
    
    public SpiritLoopEffect() {
        super(Outcome.GainLife);
        this.staticText = "you gain that much life";
    }
    
    public SpiritLoopEffect(final SpiritLoopEffect effect) {
        super(effect);
    }
    
    @Override
    public SpiritLoopEffect copy() {
        return new SpiritLoopEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        int amount = (Integer) getValue("damage");
        if (amount > 0) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                controller.gainLife(amount, game);
                return true;
            }
        }
        return false;     
    }
}