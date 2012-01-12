/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.abilities.effects.common;

import mage.Constants;
import mage.Constants.Outcome;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.SpellAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author LevelX
 */
public class LookLibraryControllerEffect extends OneShotEffect<LookLibraryControllerEffect> {

    protected DynamicValue numberOfCards;
    protected boolean mayShuffleAfter = false;
    protected boolean putOnTop = true; // if false on put back on bottom of library
            
    public LookLibraryControllerEffect() {
            this(1);
    }

    public LookLibraryControllerEffect(int numberOfCards) {
            this(numberOfCards, false, true);
    }

    public LookLibraryControllerEffect(DynamicValue numberOfCards) {
            this(numberOfCards, false, true);
    }

    public LookLibraryControllerEffect(int numberOfCards, boolean mayShuffleAfter) {
            this(numberOfCards, mayShuffleAfter, true);
    }
    
    public LookLibraryControllerEffect(int numberOfCards, boolean mayShuffleAfter, boolean putOnTop) {
            this(new StaticValue(numberOfCards), mayShuffleAfter, putOnTop);
    }

    public LookLibraryControllerEffect(DynamicValue numberOfCards, boolean mayShuffleAfter, boolean putOnTop) {
            super(Outcome.Benefit);
            this.numberOfCards = numberOfCards;
            this.mayShuffleAfter = mayShuffleAfter;
            this.putOnTop = putOnTop;

    }
    
    public LookLibraryControllerEffect(final LookLibraryControllerEffect effect) {
            super(effect);
            this.numberOfCards = effect.numberOfCards.clone(); 
            this.mayShuffleAfter = effect.mayShuffleAfter;
            this.putOnTop = effect.putOnTop;
    }

    @Override
    public LookLibraryControllerEffect copy() {
        return new LookLibraryControllerEffect(this);
        
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        String windowName ="Reveal"; 
        
        if (source instanceof SpellAbility) {
            Card sourceCard = game.getCard(source.getSourceId());
            if (sourceCard != null)
                windowName = sourceCard.getName(); 
        }
        else {
            Permanent sourcePermanent = game.getPermanent(source.getSourceId());
            if (sourcePermanent != null)
                windowName = sourcePermanent.getName(); 
        }
        
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        // take cards from library and look at them
        Cards cards = new CardsImpl(Zone.PICK);
        int count = Math.min(player.getLibrary().size(), this.numberOfCards.calculate(game, source));
        for (int i = 0; i < count; i++) {
            Card card = player.getLibrary().removeFromTop(game);
            if (card != null) {
                cards.add(card);
                this.cardLooked(card, game, source);
                game.setZone(card.getId(), Zone.PICK);
            }
        }
        player.lookAtCards(windowName, cards, game);

        this.actionWithSelectedCards(cards, game, source, windowName);
        
        this.putCardsBack(source, player, cards, game);
        
        this.mayShuffle(player, game);
        
        return true;
    }
    /**
     * 
     * @param card
     * @param game
     * @param source 
     */
    protected void cardLooked(Card card, Game game, Ability source) {
        return;
    }
    
    protected void actionWithSelectedCards(Cards cards, Game game, Ability source, String windowName) {
        return;
    }

    /**
     * Put the rest of the cards back to library
     * 
     * @param source
     * @param player
     * @param cards
     * @param game 
     */
    protected void putCardsBack(Ability source, Player player, Cards cards, Game game) {
        TargetCard target = new TargetCard(Zone.PICK, new FilterCard(this.getPutBackText()));
        target.setRequired(true);
        while (cards.size() > 1) {
            player.choose(Outcome.Neutral, cards, target, game);
            Card card = cards.get(target.getFirstTarget(), game);
            if (card != null) {
                cards.remove(card);
                card.moveToZone(Zone.LIBRARY, source.getId(), game, putOnTop);
            }
            target.clearChosen();
        }
        if (cards.size() == 1) {
            Card card = cards.get(cards.iterator().next(), game);
            card.moveToZone(Zone.LIBRARY, source.getId(), game, true);
        }
    }

    /**
     * Check to shuffle library if allowed
     * @param player
     * @param game 
     */
    protected void mayShuffle(Player player, Game game) {
        if (this.mayShuffleAfter) {
        	if (player.chooseUse(Constants.Outcome.Benefit, "Shuffle you library?", game)) {
			player.shuffleLibrary(game);
		}
        }
    }
    
    protected String getPutBackText() {
        StringBuilder sb = new StringBuilder("card to put ");
        if (putOnTop)
            sb.append("on your library (last chosen will be on top)");
        else
            sb.append("on bottom of your library (last chosen will be on bottom)");
        return sb.toString();
    }
    
    @Override
    public String getText(Mode mode) {
        return setText(mode, "");
    }
    

    public String setText(Mode mode, String middleText) {
        int numberLook = numberOfCards.calculate(null, null);
//        int numberPick = numberToPick.calculate(null, null) ;
        StringBuilder sb = new StringBuilder("Look at the top ");
        switch(numberLook) {
            case 0:
                sb.append(" X ");
                break;            
            case 1:
                sb.append("card ");
                break;
            case 2:
                sb.append("two");
                break;
            case 3:
                sb.append("three");
                break;
            case 4:
                sb.append("four");
                break;
            case 5:
                sb.append("five");
                break;
            default:
                sb.append(numberLook);
                break;
        }
        if (numberLook != 1)
                sb.append(" cards ");
        
        sb.append("of your Library");
        if (numberLook == 0)
            sb.append(", where {X} is the number of cards ").append(numberOfCards.getMessage());
        
        
        if (!middleText.isEmpty()) {
            sb.append(middleText);
        }
        else {
            if (numberLook > 1)
                sb.append(", then put them back in any order");
        }
        if (this.mayShuffleAfter)
            sb.append(". You may shuffle your library");
        
        return sb.toString();
    }
    
}

