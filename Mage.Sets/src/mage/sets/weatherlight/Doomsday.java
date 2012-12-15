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
package mage.sets.weatherlight;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author Plopman
 */
public class Doomsday extends CardImpl<Doomsday> {

    public Doomsday(UUID ownerId) {
        super(ownerId, 8, "Doomsday", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{B}{B}{B}");
        this.expansionSetCode = "WTH";

        this.color.setBlack(true);

        // Search your library and graveyard for five cards and exile the rest. Put the chosen cards on top of your library in any order. You lose half your life, rounded up.
        this.getSpellAbility().addEffect(new DoomsdayEffect());
    }

    public Doomsday(final Doomsday card) {
        super(card);
    }

    @Override
    public Doomsday copy() {
        return new Doomsday(this);
    }
}


class DoomsdayEffect extends OneShotEffect<DoomsdayEffect> {

    public DoomsdayEffect() {
        super(Constants.Outcome.LoseLife);
        staticText = "Search your library and graveyard for five cards and exile the rest. Put the chosen cards on top of your library in any order. You lose half your life, rounded up";
    }

    public DoomsdayEffect(final DoomsdayEffect effect) {
        super(effect);
    }

    @Override
    public DoomsdayEffect copy() {
        return new DoomsdayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        
        if (player != null) {
            //Search your library and graveyard for five cards
            Cards allCards = new CardsImpl(Constants.Zone.PICK);
            Cards cards = new CardsImpl(Constants.Zone.PICK);
            allCards.addAll(player.getLibrary().getCardList());
            allCards.addAll(player.getGraveyard());
            int number = Math.min(5, allCards.size());
            TargetCard target = new TargetCard(number, number, Constants.Zone.PICK, new FilterCard());
            
            if(player.choose(Outcome.Benefit, allCards, target, game)){
                // exile the rest
                for(UUID uuid : allCards){
                    if(!target.getTargets().contains(uuid)){
                        Card card = game.getCard(uuid);
                        if(card != null){
                            card.moveToExile(null, "Doomsday", source.getId(), game);
                        }      
                    }
                    else{
                        cards.add(uuid);
                    }
                    
                }
                //Put the chosen cards on top of your library in any order
                target = new TargetCard(Constants.Zone.PICK, new FilterCard("Card to put on top"));
                target.setRequired(true);
                while (cards.size() > 1) {
                    player.choose(Outcome.Neutral, cards, target, game);
                    Card card = cards.get(target.getFirstTarget(), game);
                    if (card != null) {
                        cards.remove(card);
                        card.moveToZone(Constants.Zone.LIBRARY, source.getId(), game, true);
                    }
                    target.clearChosen();
                }
                if (cards.size() == 1) {
                    Card card = cards.get(cards.iterator().next(), game);
                    card.moveToZone(Constants.Zone.LIBRARY, source.getId(), game, true);
                }
            }
            
            //You lose half your life, rounded up
            int amount = (player.getLife() + 1) / 2;
            if (amount > 0) {
                player.loseLife(amount, game);
                return true;
            }
        }
        return false;
    }
}