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
package mage.sets.commander2013;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class CurseOfChaos extends CardImpl<CurseOfChaos> {

    public CurseOfChaos(UUID ownerId) {
        super(ownerId, 105, "Curse of Chaos", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");
        this.expansionSetCode = "C13";
        this.subtype.add("Aura");
        this.subtype.add("Curse");

        this.color.setRed(true);

        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.DrawCard));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // Whenever a player attacks enchanted player with one or more creatures, that attacking player may discard a card. If the player does, he or she draws a card.
        this.addAbility(new CurseOfChaosTriggeredAbility());
    }

    public CurseOfChaos(final CurseOfChaos card) {
        super(card);
    }

    @Override
    public CurseOfChaos copy() {
        return new CurseOfChaos(this);
    }
}

class CurseOfChaosTriggeredAbility extends TriggeredAbilityImpl<CurseOfChaosTriggeredAbility> {

    public CurseOfChaosTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CurseOfChaosEffect(), false); // false because handled in effect
    }

    public CurseOfChaosTriggeredAbility(final CurseOfChaosTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType().equals(EventType.DECLARED_ATTACKERS)) {
            Permanent enchantment = game.getPermanent(this.getSourceId());
            if (enchantment != null
                    && enchantment.getAttachedTo() != null
                    && game.getCombat().getPlayerDefenders(game).contains(enchantment.getAttachedTo())) {
                for (Effect effect: this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(game.getCombat().getAttackerId()));                    
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return new StringBuilder("Whenever a player attacks enchanted player with one or more creatures, ").append(super.getRule()).toString();
    }

    @Override
    public CurseOfChaosTriggeredAbility copy() {
        return new CurseOfChaosTriggeredAbility(this);
    }

}

class CurseOfChaosEffect extends OneShotEffect<CurseOfChaosEffect> {

    public CurseOfChaosEffect() {
        super(Outcome.Benefit);
        this.staticText = "that attacking player may discard a card. If the player does, he or she draws a card";
    }

    public CurseOfChaosEffect(final CurseOfChaosEffect effect) {
        super(effect);
    }

    @Override
    public CurseOfChaosEffect copy() {
        return new CurseOfChaosEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player attacker = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (attacker != null) {
            if (attacker.getHand().size() > 0 && attacker.chooseUse(outcome, "Discard a card and draw a card?", game)){
                attacker.discard(1, source, game);
                attacker.drawCards(1, game);
            }
            return true;
        }
        return false;
    }
}
