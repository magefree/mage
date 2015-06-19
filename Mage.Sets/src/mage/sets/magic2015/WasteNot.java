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
package mage.sets.magic2015;

import java.util.UUID;
import mage.Mana;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.BasicManaEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.token.ZombieToken;

/**
 *
 * @author emerald000
 */
public class WasteNot extends CardImpl {

    public WasteNot(UUID ownerId) {
        super(ownerId, 122, "Waste Not", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");
        this.expansionSetCode = "M15";


        // Whenever an opponent discards a creature card, put a 2/2 black Zombie creature token onto the battlefield.
        this.addAbility(new WasteNotCreatureTriggeredAbility());
        
        // Whenever an opponent discards a land card, add {B}{B} to your mana pool.
        this.addAbility(new WasteNotLandTriggeredAbility());
        
        // Whenever an opponent discards a noncreature, nonland card, draw a card.
        this.addAbility(new WasteNotOtherTriggeredAbility());
    }

    public WasteNot(final WasteNot card) {
        super(card);
    }

    @Override
    public WasteNot copy() {
        return new WasteNot(this);
    }
}

class WasteNotCreatureTriggeredAbility extends TriggeredAbilityImpl {
    
    WasteNotCreatureTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new ZombieToken("M15")), false);
    }
    
    WasteNotCreatureTriggeredAbility(final WasteNotCreatureTriggeredAbility ability) {
        super(ability);
    }
    
    @Override
    public WasteNotCreatureTriggeredAbility copy() {
        return new WasteNotCreatureTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DISCARDED_CARD;
    }
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(this.getControllerId()).contains(event.getPlayerId())) {
            Card discarded = game.getCard(event.getTargetId());
            if (discarded != null && discarded.getCardType().contains(CardType.CREATURE)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String getRule() {
        return "Whenever an opponent discards a creature card, put a 2/2 black Zombie creature token onto the battlefield.";
    }
}

class WasteNotLandTriggeredAbility extends TriggeredAbilityImpl {
    
    WasteNotLandTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BasicManaEffect(new Mana(0, 0, 0, 0, 2, 0, 0)), false);
    }
    
    WasteNotLandTriggeredAbility(final WasteNotLandTriggeredAbility ability) {
        super(ability);
    }
    
    @Override
    public WasteNotLandTriggeredAbility copy() {
        return new WasteNotLandTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DISCARDED_CARD;
    }
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(this.getControllerId()).contains(event.getPlayerId())) {
            Card discarded = game.getCard(event.getTargetId());
            if (discarded != null && discarded.getCardType().contains(CardType.LAND)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String getRule() {
        return "Whenever an opponent discards a land card, add {B}{B} to your mana pool.";
    }
}

class WasteNotOtherTriggeredAbility extends TriggeredAbilityImpl {
    
    WasteNotOtherTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), false);
    }
    
    WasteNotOtherTriggeredAbility(final WasteNotOtherTriggeredAbility ability) {
        super(ability);
    }
    
    @Override
    public WasteNotOtherTriggeredAbility copy() {
        return new WasteNotOtherTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DISCARDED_CARD;
    }
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(this.getControllerId()).contains(event.getPlayerId())) {
            Card discarded = game.getCard(event.getTargetId());
            if (discarded != null && !discarded.getCardType().contains(CardType.LAND) && !discarded.getCardType().contains(CardType.CREATURE)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String getRule() {
        return "Whenever an opponent discards a noncreature, nonland card, draw a card.";
    }
}