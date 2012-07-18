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
package mage.sets.timespiral;

import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.CostImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author noxx
 */
public class SerraAvenger extends CardImpl<SerraAvenger> {

    private static final String rule = "You can't cast Serra Avenger during your first, second, or third turns of the game";

    public SerraAvenger(UUID ownerId) {
        super(ownerId, 40, "Serra Avenger", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{W}{W}");
        this.expansionSetCode = "TSP";
        this.subtype.add("Angel");

        this.color.setWhite(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // You can't cast Serra Avenger during your first, second, or third turns of the game.
        this.getSpellAbility().addCost(new SerraAvengerCost());
        this.addInfo("cost", rule);
    }

    public SerraAvenger(final SerraAvenger card) {
        super(card);
    }

    @Override
    public SerraAvenger copy() {
        return new SerraAvenger(this);
    }
}

class SerraAvengerCost extends CostImpl<SerraAvengerCost> {

    public SerraAvengerCost() {
        text = "You can't cast Serra Avenger during your first, second, or third turns of the game";
    }

    public SerraAvengerCost(final SerraAvengerCost cost) {
        super(cost);
    }

    @Override
    public SerraAvengerCost copy() {
        return new SerraAvengerCost(this);
    }

    @Override
    public boolean canPay(UUID sourceId, UUID controllerId, Game game) {
        if (game.getActivePlayerId().equals(controllerId)) {
            Player controller = game.getPlayer(controllerId);
            if (controller.getTurns() > 3) {
                return true;
            } else {
                game.informPlayer(controller, "You can't cass Serra Avenger (your turns passed: " + controller.getTurns() + ")");
                return false;
            }
        }

        // Always return true for not controller's turn:
        // 9/25/2006: You can cast Serra Avenger during another player's first, second, or third turns of the game
        //   if some other effect (such as Vedalken Orrery) enables that.
        return true;
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        this.paid = true;
        return paid;
    }
}