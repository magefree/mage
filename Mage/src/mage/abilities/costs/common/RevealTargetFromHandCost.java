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

// author jeff


package mage.abilities.costs.common;
import java.util.UUID;
import mage.Constants;
import mage.abilities.Ability;
import mage.abilities.costs.CostImpl;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

public class RevealTargetFromHandCost extends CostImpl<RevealTargetFromHandCost> {
   
    public int convertedManaCosts = 0;
   
    public RevealTargetFromHandCost(TargetCardInHand target) {
        this.addTarget(target);
        this.text = "reveal " + target.getTargetName();
    }
   
    public RevealTargetFromHandCost(RevealTargetFromHandCost cost) {
        super(cost);
    }
   
    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        if (targets.choose(Constants.Outcome.Benefit, controllerId, sourceId, game)) {
            Player player = game.getPlayer(controllerId);
            Cards cards = new CardsImpl();
            for (UUID targetId: targets.get(0).getTargets()) {
                Card card = player.getHand().get(targetId, game);
                if (card == null)
                    return false;
                convertedManaCosts = card.getManaCost().convertedManaCost();
                cards.add(card);
                player.revealCards("Revealed card", cards, game); // this is not a boolean
                paid = true;
                return paid;
            }
        }
        paid = false;
        return paid;
    }
   
    public int getConvertedCosts() {
            return convertedManaCosts;
        }

    @Override
    public boolean canPay(UUID sourceId, UUID controllerId, Game game) {
        return targets.canChoose(controllerId, game);
    }

    @Override
    public RevealTargetFromHandCost copy() {
        return new RevealTargetFromHandCost(this);
    }
}