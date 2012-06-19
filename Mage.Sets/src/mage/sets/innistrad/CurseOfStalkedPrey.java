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
package mage.sets.innistrad;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author BetaSteward
 */
public class CurseOfStalkedPrey extends CardImpl<CurseOfStalkedPrey> {

    public CurseOfStalkedPrey(UUID ownerId) {
        super(ownerId, 136, "Curse of Stalked Prey", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");
        this.expansionSetCode = "ISD";
        this.subtype.add("Aura");
        this.subtype.add("Curse");

        this.color.setRed(true);

        // Enchant player
        TargetPlayer target = new TargetPlayer();
        this.getSpellAbility().addTarget(target);
        this.getSpellAbility().addEffect(new AttachEffect(Constants.Outcome.AddAbility));
        Ability ability = new EnchantAbility(target.getTargetName());
        this.addAbility(ability);

        // Whenever a creature deals combat damage to enchanted player, put a +1/+1 counter on that creature.
        this.addAbility(new CurseOfStalkedPreyTriggeredAbility());

    }

    public CurseOfStalkedPrey(final CurseOfStalkedPrey card) {
        super(card);
    }

    @Override
    public CurseOfStalkedPrey copy() {
        return new CurseOfStalkedPrey(this);
    }
}

class CurseOfStalkedPreyTriggeredAbility extends TriggeredAbilityImpl<CurseOfStalkedPreyTriggeredAbility> {

    public CurseOfStalkedPreyTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
    }

    public CurseOfStalkedPreyTriggeredAbility(final CurseOfStalkedPreyTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CurseOfStalkedPreyTriggeredAbility copy() {
        return new CurseOfStalkedPreyTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == EventType.DAMAGED_PLAYER && ((DamagedPlayerEvent) event).isCombatDamage()) {
            Permanent enchantment = game.getPermanent(this.sourceId);
            if (enchantment != null && enchantment.getAttachedTo() != null) {
                Player player = game.getPlayer(enchantment.getAttachedTo());
                if (player != null && event.getTargetId().equals(player.getId())) {
                    getEffects().get(0).setTargetPointer(new FixedTarget(event.getSourceId()));
                       return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature deals combat damage to enchanted player, put a +1/+1 counter on that creature";
    }

}
