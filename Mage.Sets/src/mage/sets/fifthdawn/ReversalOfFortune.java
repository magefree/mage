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
package mage.sets.fifthdawn;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Plopman
 */
public class ReversalOfFortune extends CardImpl {

    public ReversalOfFortune(UUID ownerId) {
        super(ownerId, 77, "Reversal of Fortune", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{4}{R}{R}");
        this.expansionSetCode = "5DN";


        // Target opponent reveals his or her hand. You may copy an instant or sorcery card in it. If you do, you may cast the copy without paying its mana cost.
        this.getSpellAbility().addEffect(new ReversalOfFortuneEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    public ReversalOfFortune(final ReversalOfFortune card) {
        super(card);
    }

    @Override
    public ReversalOfFortune copy() {
        return new ReversalOfFortune(this);
    }
}


class ReversalOfFortuneEffect extends OneShotEffect {

    public ReversalOfFortuneEffect() {
        super(Outcome.Copy);
        this.staticText = "Target opponent reveals his or her hand. You may copy an instant or sorcery card in it. If you do, you may cast the copy without paying its mana cost";
    }

    public ReversalOfFortuneEffect(final ReversalOfFortuneEffect effect) {
        super(effect);
    }

    @Override
    public ReversalOfFortuneEffect copy() {
        return new ReversalOfFortuneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (controller != null && opponent != null) {
            // Target opponent reveals his or her hand
            Cards revealedCards = new CardsImpl(Zone.HAND); 
            revealedCards.addAll(opponent.getHand()); 
            opponent.revealCards("Reveal", revealedCards, game);
            
            //You may copy an instant or sorcery card in it
            TargetCard target = new TargetCard(1, Zone.HAND, new FilterInstantOrSorceryCard());  
            target.setRequired(false);
            if (controller.choose(outcome, revealedCards, target, game)) {
                Card card = revealedCards.get((UUID) target.getFirstTarget(), game);
                //If you do, you may cast the copy without paying its mana cost
                if(card != null){
                    Card copiedCard = game.copyCard(card, source, source.getControllerId());
                    if (controller.chooseUse(outcome, "Cast the copied card without paying mana cost?", game)) {
                        controller.cast(copiedCard.getSpellAbility(), game, true);
                    }
                }
                else{
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}