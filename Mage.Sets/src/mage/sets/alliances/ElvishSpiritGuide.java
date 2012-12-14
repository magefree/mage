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
package mage.sets.alliances;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.common.BasicManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Plopman
 */
public class ElvishSpiritGuide extends CardImpl<ElvishSpiritGuide> {

    public ElvishSpiritGuide(UUID ownerId) {
        super(ownerId, 69, "Elvish Spirit Guide", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.expansionSetCode = "ALL";
        this.subtype.add("Elf");
        this.subtype.add("Spirit");

        this.color.setGreen(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Exile Elvish Spirit Guide from your hand: Add {G} to your mana pool.
        this.addAbility(new SimpleManaAbility(Constants.Zone.HAND, new BasicManaEffect(Mana.GreenMana), new ExileSourceFromHandCost()));
    }

    public ElvishSpiritGuide(final ElvishSpiritGuide card) {
        super(card);
    }

    @Override
    public ElvishSpiritGuide copy() {
        return new ElvishSpiritGuide(this);
    }
}


class ExileSourceFromHandCost extends CostImpl<ExileSourceFromHandCost> {

    public ExileSourceFromHandCost() {
        this.text = "Exile {this} from your hand";
    }

    public ExileSourceFromHandCost(ExileSourceFromHandCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        Card card = game.getCard(sourceId);
        Player player = game.getPlayer(controllerId);
        if (player != null && player.getHand().contains(sourceId) && card != null) {
            paid = card.moveToExile(ability.getSourceId(), "from Hand", ability.getSourceId(), game);
        }
        return paid;
    }

    @Override
    public boolean canPay(UUID sourceId, UUID controllerId, Game game) {
        Player player = game.getPlayer(controllerId);
        if (player != null && player.getHand().contains(sourceId)) {
            return true;
        }
        return false;
    }

    @Override
    public ExileSourceFromHandCost copy() {
        return new ExileSourceFromHandCost(this);
    }

}
