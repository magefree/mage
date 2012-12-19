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
package mage.sets.urzaslegacy;

import java.util.Iterator;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;

/**
 *
 * @author Plopman
 */
public class MemoryJar extends CardImpl<MemoryJar> {



    public MemoryJar(UUID ownerId) {
        super(ownerId, 129, "Memory Jar", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{5}");
        this.expansionSetCode = "ULG";

        // {tap}, Sacrifice Memory Jar: Each player exiles all cards from his or her hand face down and draws seven cards.
        // At the beginning of the next end step, each player discards his or her hand and returns to his or her hand each
        //card he or she exiled this way.
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new MemoryJarEffect(), new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
        
    }

    public MemoryJar(final MemoryJar card) {
        super(card);
    }

    @Override
    public MemoryJar copy() {
        return new MemoryJar(this);
    }
}

class MemoryJarEffect extends OneShotEffect<MemoryJarEffect> {

    public MemoryJarEffect()
    {
        super(Outcome.DrawCard);
        staticText = "Each player exiles all cards from his or her hand face down and draws seven cards. At the beginning of the next end step, each player discards his or her hand and returns to his or her hand each card he or she exiled this way.";
    }
    public MemoryJarEffect(final MemoryJarEffect effect)
    {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) 
    {
        Cards cards = new CardsImpl();
        //Exil hand
        for (UUID playerId: game.getPlayer(source.getControllerId()).getInRange()) {
            Player player = game.getPlayer(playerId);
            if (player != null)
            {
                Cards hand = player.getHand();
                while (hand.size() > 0) {
                    Card card = hand.get(hand.iterator().next(), game);
                    if(card != null)
                    {
                        card.setFaceDown(true);
                        card.moveToExile(getId(), "Memory Jar", source.getSourceId(), game);
                        cards.add(card);
                    }
                }
            }
        }
        //Draw 7 cards
        for (UUID playerId: game.getPlayer(source.getControllerId()).getInRange()) {
            Player player = game.getPlayer(playerId);
            if (player != null)
            {
                player.drawCards(7, game);
            }
        }
        //Delayed ability
        Effect effect = new MemoryJarDelayedEffect();
        effect.setValue("MemoryJarCards", cards);
        DelayedTriggeredAbility delayedAbility = new MemoryJarDelayedTriggeredAbility(effect);
        
        delayedAbility.setSourceId(source.getSourceId());
        delayedAbility.setControllerId(source.getControllerId());
        game.addDelayedTriggeredAbility(delayedAbility);
        return true;
    }

    @Override
    public MemoryJarEffect copy() {
        return new MemoryJarEffect(this);
    }
}

class MemoryJarDelayedEffect extends OneShotEffect<MemoryJarDelayedEffect> {
        
    public MemoryJarDelayedEffect()
    {
        super(Outcome.DrawCard);
        staticText = "At the beginning of the next end step, each player discards his or her hand and returns to his or her hand each card he or she exiled this way.";
    }
    public MemoryJarDelayedEffect(final MemoryJarDelayedEffect effect)
    {
        super(effect);
    }
    @Override
    public MemoryJarDelayedEffect copy() {
        return new MemoryJarDelayedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Cards cards = (Cards)this.getValue("MemoryJarCards");
        
        if(cards != null)
        {
            //Discard
            for (UUID playerId: game.getPlayer(source.getControllerId()).getInRange()) {
                Player player = game.getPlayer(playerId);
                if (player != null)
                {
                    player.discard(player.getHand().size(), source, game);
                }
            }
            //Return to hand
            for (Iterator<Card> it = cards.getCards(game).iterator(); it.hasNext();) {
                Card card = it.next();
                card.moveToZone(Constants.Zone.HAND, source.getSourceId(), game, true);
            }
            return true;
        }
        return false;
    }
    
}

class MemoryJarDelayedTriggeredAbility extends DelayedTriggeredAbility<MemoryJarDelayedTriggeredAbility> {

    public MemoryJarDelayedTriggeredAbility(Effect effect) {
        super(effect);
    }

    public MemoryJarDelayedTriggeredAbility(final MemoryJarDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MemoryJarDelayedTriggeredAbility copy() {
        return new MemoryJarDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == EventType.END_TURN_STEP_PRE) {
            return true;
        }
        return false;
    }



}