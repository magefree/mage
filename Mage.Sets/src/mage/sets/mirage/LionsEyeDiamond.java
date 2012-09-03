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
package mage.sets.mirage;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.TimingRule;
import mage.Constants.Zone;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.AddManaOfAnyColorEffect;
import mage.abilities.effects.common.BasicManaEffect;
import mage.abilities.mana.ManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.choices.ChoiceColor;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author North
 */
public class LionsEyeDiamond extends CardImpl<LionsEyeDiamond> {

    public LionsEyeDiamond(UUID ownerId) {
        super(ownerId, 272, "Lion's Eye Diamond", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{0}");
        this.expansionSetCode = "MIR";

        // Sacrifice Lion's Eye Diamond, Discard your hand: Add three mana of any one color to your mana pool. Activate this ability only any time you could cast an instant.
        this.addAbility(new LionsEyeDiamondAbility());
    }

    public LionsEyeDiamond(final LionsEyeDiamond card) {
        super(card);
    }

    @Override
    public LionsEyeDiamond copy() {
        return new LionsEyeDiamond(this);
    }
}


class LionsEyeDiamondAbility extends ManaAbility<LionsEyeDiamondAbility> {

    public LionsEyeDiamondAbility() {
        super(Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(3), new SacrificeSourceCost());
        this.addCost(new LionsEyeDiamondCost());
        this.addChoice(new ChoiceColor());
        this.timing = TimingRule.INSTANT;
    }

    public LionsEyeDiamondAbility(Zone zone, Mana mana, Cost cost) {
        super(zone, new BasicManaEffect(mana), cost);
        this.netMana = mana.copy();
    }

    public LionsEyeDiamondAbility(final LionsEyeDiamondAbility ability) {
        super(ability);
    }

    @Override
    public LionsEyeDiamondAbility copy() {
        return new LionsEyeDiamondAbility(this);
    }

    @Override
    public String getRule() {
        return super.getRule() + " Activate this ability only any time you could cast an instant.";
    }
}

class LionsEyeDiamondCost extends CostImpl<LionsEyeDiamondCost> {

    public LionsEyeDiamondCost() {
    }

    public LionsEyeDiamondCost(LionsEyeDiamondCost cost) {
        super(cost);
    }

    @Override
    public LionsEyeDiamondCost copy() {
        return new LionsEyeDiamondCost(this);
    }

    @Override
    public boolean canPay(UUID sourceId, UUID controllerId, Game game) {
        return true;
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        Player player = game.getPlayer(controllerId);
        if (player != null) {
            for (Card card : player.getHand().getCards(game)) {
                player.discard(card, ability, game);
            }
            paid = true;
        }
        return paid;
    }

    @Override
    public String getText() {
        return "Discard your hand";
    }
}
