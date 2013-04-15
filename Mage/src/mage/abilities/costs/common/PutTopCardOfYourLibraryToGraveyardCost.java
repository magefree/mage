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

package mage.abilities.costs.common;

import java.util.UUID;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.costs.CostImpl;
import mage.cards.Card;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */


public class PutTopCardOfYourLibraryToGraveyardCost extends CostImpl<PutTopCardOfYourLibraryToGraveyardCost> {

    int numberOfCards;

    public PutTopCardOfYourLibraryToGraveyardCost() {
        this(1);
    }

    public PutTopCardOfYourLibraryToGraveyardCost(int numberOfCards) {
        this.numberOfCards = numberOfCards;
        this.text = setText();
    }

    public PutTopCardOfYourLibraryToGraveyardCost(PutTopCardOfYourLibraryToGraveyardCost cost) {
        super(cost);
        this.numberOfCards = cost.numberOfCards;
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        Player player = game.getPlayer(controllerId);
        if (player != null && player.getLibrary().size() >= numberOfCards) {
            int i = 0;
            paid = true;
            while (i < numberOfCards) {
                Card card = player.getLibrary().removeFromTop(game);
                if (card != null) {
                    // all cards must reach the graveyard to pay the costs
                    paid &= card.moveToZone(Zone.GRAVEYARD, sourceId, game, true);
                }
                ++i;
            }
        }
        return paid;
    }

    @Override
    public boolean canPay(UUID sourceId, UUID controllerId, Game game) {
        Player player = game.getPlayer(controllerId);
        if (player != null && player.getLibrary().size() >= numberOfCards) {
            return true;
        }
        return false;
    }

    @Override
    public PutTopCardOfYourLibraryToGraveyardCost copy() {
        return new PutTopCardOfYourLibraryToGraveyardCost(this);
    }

    private String setText() {
        StringBuilder sb = new StringBuilder("Put the top ");
        if (numberOfCards == 1) {
            sb.append("card");
        } else {
            sb.append(CardUtil.numberToText(numberOfCards)).append(" cards");
        }
        sb.append(" of your library into your graveyard");
        return sb.toString();
    }
}
