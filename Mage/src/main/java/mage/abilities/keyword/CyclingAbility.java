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
package mage.abilities.keyword;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.Card;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CyclingAbility extends ActivatedAbilityImpl {

    private final Cost cost;
    private final String text;

    public CyclingAbility(Cost cost) {
        super(Zone.HAND, new DrawCardSourceControllerEffect(1), cost);
        this.addCost(new CyclingDiscardCost());
        this.cost = cost;
        this.text = "Cycling";
    }

    public CyclingAbility(Cost cost, FilterCard filter, String text) {
        super(Zone.HAND, new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true, true), cost);
        this.addCost(new CyclingDiscardCost());
        this.cost = cost;
        this.text = text;
    }

    public CyclingAbility(final CyclingAbility ability) {
        super(ability);
        this.cost = ability.cost;
        this.text = ability.text;
    }

    @Override
    public CyclingAbility copy() {
        return new CyclingAbility(this);
    }

    @Override
    public String getRule() {
        StringBuilder rule = new StringBuilder(this.text);
        if (cost instanceof ManaCost) {
            rule.append(' ');
        } else {
            rule.append("&mdash;");
        }
        rule.append(cost.getText()).append(" <i>(").append(super.getRule(true)).append(")</i>");
        return rule.toString();
    }

}

class CyclingDiscardCost extends CostImpl {

    public CyclingDiscardCost() {
    }

    public CyclingDiscardCost(CyclingDiscardCost cost) {
        super(cost);
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        return game.getPlayer(controllerId).getHand().contains(sourceId);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        Player player = game.getPlayer(controllerId);
        if (player != null) {
            Card card = player.getHand().get(sourceId, game);
            if (card != null) {
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.CYCLE_CARD, card.getId(), card.getId(), card.getOwnerId()));
                paid = player.discard(card, null, game);
                if (paid) {
                    game.fireEvent(GameEvent.getEvent(GameEvent.EventType.CYCLED_CARD, card.getId(), card.getId(), card.getOwnerId()));
                }
            }
        }
        return paid;
    }

    @Override
    public String getText() {
        return "Discard this card";
    }

    @Override
    public CyclingDiscardCost copy() {
        return new CyclingDiscardCost(this);
    }
}
