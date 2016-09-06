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
package mage.sets.tempest;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

/**
 *    10/4/2004 : If there is only one card in your graveyard, it is exiled by the first part of the effect and you do not get to put any cards into your hand since the second part fails.
 *    10/15/2006: It does not target the cards, but it targets the opponent. If you can't target an opponent, you can't activate this ability.
 * 
 * @author Plopman
 */
public class PhyrexianGrimoire extends CardImpl {

    public PhyrexianGrimoire(UUID ownerId) {
        super(ownerId, 291, "Phyrexian Grimoire", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "TMP";

        // {4}, {tap}: Target opponent chooses one of the top two cards of your graveyard. Exile that card and put the other one into your hand.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PhyrexianGrimoireEffect(), new ManaCostsImpl("{4}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    public PhyrexianGrimoire(final PhyrexianGrimoire card) {
        super(card);
    }

    @Override
    public PhyrexianGrimoire copy() {
        return new PhyrexianGrimoire(this);
    }
}

class PhyrexianGrimoireEffect extends OneShotEffect {

    public PhyrexianGrimoireEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Target opponent chooses one of the top two cards of your graveyard. Exile that card and put the other one into your hand";
    }

    public PhyrexianGrimoireEffect(final PhyrexianGrimoireEffect effect) {
        super(effect);
    }

    @Override
    public PhyrexianGrimoireEffect copy() {
        return new PhyrexianGrimoireEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (controller != null && opponent != null) {
            if(controller.getGraveyard().size() > 0)
            {
                Cards cards = new CardsImpl();
                
                UUID card1 = null;
                UUID card2 = null;
                for (UUID cardId : controller.getGraveyard()) {
                    card2 = card1;
                    card1 = cardId;
                }
                if(card1 != null){
                    cards.add(card1);
                }
                if(card2 != null){
                    cards.add(card2);
                }
                
                TargetCard target = new TargetCard(Zone.GRAVEYARD, new FilterCard());
                target.setRequired(true);
                if(opponent.choose(Outcome.Exile, cards, target, game))
                {
                    Card card = game.getCard(target.getFirstTarget()); 
                    cards.remove(target.getFirstTarget());
                    if (card != null) {
                        controller.moveCards(card, Zone.EXILED, source, game);
                    }
                    
                    if(!cards.isEmpty())
                    {
                        card = game.getCard(cards.iterator().next()); 
                        if (card != null) {
                            controller.moveCards(card, Zone.HAND, source, game);
                        }
                    }
                    
                }
            }
            return true;
        }
        return false;
    }
}
