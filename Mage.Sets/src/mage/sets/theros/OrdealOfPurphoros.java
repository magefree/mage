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
package mage.sets.theros;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreatureOrPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Plopman
 */
public class OrdealOfPurphoros extends CardImpl<OrdealOfPurphoros> {

    public OrdealOfPurphoros(UUID ownerId) {
        super(ownerId, 131, "Ordeal of Purphoros", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");
        this.expansionSetCode = "THS";
        this.subtype.add("Aura");

        this.color.setRed(true);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        // Whenever enchanted creature attacks, put a +1/+1 counter on it. Then if it has three or more +1/+1 counters on it, sacrifice Ordeal of Purphoros.
        this.addAbility(new AttacksEnchantedTriggeredAbility());
        // When you sacrifice Ordeal of Purphoros, it deals 3 damage to target creature or player.
        this.addAbility(new SacrificeSourceAbility());
        
    }

    public OrdealOfPurphoros(final OrdealOfPurphoros card) {
        super(card);
    }

    @Override
    public OrdealOfPurphoros copy() {
        return new OrdealOfPurphoros(this);
    }
}


class AttacksEnchantedTriggeredAbility extends TriggeredAbilityImpl<AttacksEnchantedTriggeredAbility> {

    public AttacksEnchantedTriggeredAbility() {
        super(Zone.BATTLEFIELD, new OrdealOfPurphorosEffect());
    }

    public AttacksEnchantedTriggeredAbility(final AttacksEnchantedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AttacksEnchantedTriggeredAbility copy() {
        return new AttacksEnchantedTriggeredAbility(this);
    }
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = game.getPermanent(this.getSourceId());
         if (event.getType() == GameEvent.EventType.ATTACKER_DECLARED) {
            if (enchantment != null && event.getSourceId().equals(enchantment.getAttachedTo())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever enchanted creature attacks, " + super.getRule();
    }
}


class OrdealOfPurphorosEffect extends OneShotEffect<OrdealOfPurphorosEffect> {

    private Counter counter;

    public OrdealOfPurphorosEffect() {
        super(Outcome.Benefit);
        counter = CounterType.P1P1.createInstance();
    }

 

    public OrdealOfPurphorosEffect(final OrdealOfPurphorosEffect effect) {
        super(effect);
        if (effect.counter != null) {
            this.counter = effect.counter.copy();
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if(enchantment != null){
            Permanent permanent = game.getPermanent(enchantment.getAttachedTo());
            if (permanent != null) {
                if (counter != null) {
                    Counter newCounter = counter.copy();
                    permanent.addCounters(newCounter, game);
                    
                    if(permanent.getCounters().get(counter.getName()).getCount() >= 3){
                        enchantment.sacrifice(source.getId(), game);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        
        return "put a +1/+1 counter on it. Then if it has three or more +1/+1 counters on it, sacrifice {this}";
    }

    @Override
    public OrdealOfPurphorosEffect copy() {
        return new OrdealOfPurphorosEffect(this);
    }


}


class SacrificeSourceAbility extends TriggeredAbilityImpl<SacrificeSourceAbility> {

    public SacrificeSourceAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(3), false);
        TargetCreatureOrPlayer target = new TargetCreatureOrPlayer();
        target.setRequired(true);
        this.addTarget(target);
    }

    public SacrificeSourceAbility(final SacrificeSourceAbility ability) {
        super(ability);
    }

    @Override
    public SacrificeSourceAbility copy() {
        return new SacrificeSourceAbility(this);
    }
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
         if (event.getType() == GameEvent.EventType.SACRIFICED_PERMANENT && event.getTargetId().equals(sourceId)) {
            return true; 
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When you sacrifice {this}, " + super.getRule();
    }
}