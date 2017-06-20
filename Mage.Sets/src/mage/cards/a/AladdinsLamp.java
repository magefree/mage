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
package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetCard;

import java.util.UUID;

/**
 *
 * @author MarcoMarin
 */
public class AladdinsLamp extends CardImpl {

    public AladdinsLamp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{10}");

        // {X}, {tap}: The next time you would draw a card this turn, instead look at the top X cards of your library, put all but one of them on the bottom of your library in a random order, then draw a card. X can't be 0.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AladdinsLampEffect(), new ManaCostsImpl("{X}"));        
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
        
    }
    

    public AladdinsLamp(final AladdinsLamp card) {
        super(card);
    }

    @Override
    public AladdinsLamp copy() {
        return new AladdinsLamp(this);
    }
}

class AladdinsLampEffect extends ReplacementEffectImpl {

    public AladdinsLampEffect() {
        super(Duration.EndOfTurn, Outcome.DrawCard);
        staticText = "The next time you would draw a card this turn, instead look at the top X cards of your library, put all but one of them on the bottom of your library in a random order, then draw a card. X can't be 0.";
    }

    public AladdinsLampEffect(final AladdinsLampEffect effect) {
        super(effect);
    }

    @Override
    public AladdinsLampEffect copy() {
        return new AladdinsLampEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {        
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        Cards cards = new CardsImpl();
        int count = source.getManaCostsToPay().getX();
        count = Math.min(player.getLibrary().size(), count);
        for (int i = 0; i < count; i++) {
            Card card = player.getLibrary().removeFromTop(game);// I'd like to use .getTopCards(game, count), but it returns a set which I dunno how to work with and would break the copied code adding to effort/time;
            if (card != null) {
                cards.add(card);                
            }
        }
        player.lookAtCards("Aladdin's Lamp", cards, game);

        TargetCard target = new TargetCard(Zone.LIBRARY, new FilterCard());

        if (player.choose(outcome, cards, target, game)) {
            Card card = cards.get(target.getFirstTarget(), game);
            if (card != null) {
                cards.remove(card);
                card.moveToZone(Zone.HAND, source.getSourceId(), game, false);
            }
        }
    
        
        // Put the rest on the bottom of your library in a random order
        while (!cards.isEmpty()) {
            Card card = cards.getRandom(game);
            if (card != null) {
                cards.remove(card);
                card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, false);
            }
        }
        //player.drawCards(1, game); // Technically I should move it to the top and then draw but, I cant confirm that flag above refers to bottom of library
        return true;
    }
    
    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }   
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.getControllerId().equals(event.getPlayerId());
    }
}