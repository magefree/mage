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
 
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.mana.VariableManaCost;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author LevelX2
 */
public class ExileFromHandCost extends CostImpl {
 
    List<Card> cards = new ArrayList<>();
    private boolean setXFromCMC;
 
    public ExileFromHandCost(TargetCardInHand target) {
        this(target, false);
    }
    /**
     * 
     * @param target
     * @param setXFromCMC the spells X value on the stack is set to the converted mana costs of the exiled card
     */
    public ExileFromHandCost(TargetCardInHand target, boolean setXFromCMC) {
        this.addTarget(target);
        this.text = "exile " + target.getTargetName();
        this.setXFromCMC = setXFromCMC;
    }
 
    public ExileFromHandCost(final ExileFromHandCost cost) {
        super(cost);
        for (Card card: cost.cards) {
            this.cards.add(card.copy());
        }
        this.setXFromCMC = cost.setXFromCMC;
    }
 
    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        if (targets.choose(Outcome.Exile, controllerId, sourceId, game)) {
            Player player = game.getPlayer(controllerId);
            int cmc = 0;
            for (UUID targetId: targets.get(0).getTargets()) {
                Card card = player.getHand().get(targetId, game);
                if (card == null) {
                    return false;
                }
                cmc += card.getManaCost().convertedManaCost();
                this.cards.add(card);
                paid |= player.moveCardToExileWithInfo(card, null, null, ability.getSourceId(), game, Zone.HAND, true);
            }
            if (paid && setXFromCMC) {
                VariableManaCost vmc = new VariableManaCost();
                vmc.setAmount(cmc);
                vmc.setPaid();
                ability.getManaCostsToPay().add(vmc);
            }
        }
        return paid;
    }
 
    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        return targets.canChoose(sourceId, controllerId, game);
    }
 
    @Override
    public ExileFromHandCost copy() {
        return new ExileFromHandCost(this);
    }
 
     public List<Card> getCards() {
        return cards;
    }
}
