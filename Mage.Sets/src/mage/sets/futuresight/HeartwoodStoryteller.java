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
package mage.sets.futuresight;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author emerald000
 */
public class HeartwoodStoryteller extends CardImpl {

    public HeartwoodStoryteller(UUID ownerId) {
        super(ownerId, 127, "Heartwood Storyteller", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");
        this.expansionSetCode = "FUT";
        this.subtype.add("Treefolk");
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever a player casts a noncreature spell, each of that player's opponents may draw a card.
        this.addAbility(new HeartwoodStorytellerTriggeredAbility());
    }

    public HeartwoodStoryteller(final HeartwoodStoryteller card) {
        super(card);
    }

    @Override
    public HeartwoodStoryteller copy() {
        return new HeartwoodStoryteller(this);
    }
}

class HeartwoodStorytellerTriggeredAbility extends TriggeredAbilityImpl {
    
    HeartwoodStorytellerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new HeartwoodStorytellerEffect(), false);
    }
    
    HeartwoodStorytellerTriggeredAbility(final HeartwoodStorytellerTriggeredAbility ability) {
        super(ability);
    }
    
    @Override
    public HeartwoodStorytellerTriggeredAbility copy() {
        return new HeartwoodStorytellerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.SPELL_CAST;
    }
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell != null && !spell.getCardType().contains(CardType.CREATURE)) {
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
            }
            return true;
        }
        return false;
    }
    
    @Override
    public String getRule() {
        return "Whenever a player casts a noncreature spell, each of that player's opponents may draw a card.";
    }
}

class HeartwoodStorytellerEffect extends OneShotEffect {
    
    HeartwoodStorytellerEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Each of that player's opponents may draw a card";
    }
    
    HeartwoodStorytellerEffect(final HeartwoodStorytellerEffect effect) {
        super(effect);
    }
    
    @Override
    public HeartwoodStorytellerEffect copy() {
        return new HeartwoodStorytellerEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getOpponents(this.getTargetPointer().getFirst(game, source))) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.drawCards(1, game);
            }
        }
        return true;
    }
}
