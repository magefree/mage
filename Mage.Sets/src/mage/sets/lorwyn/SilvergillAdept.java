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
package mage.sets.lorwyn;

import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardsImpl;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 *
 * @author North
 */
public class SilvergillAdept extends CardImpl<SilvergillAdept> {

    public SilvergillAdept(UUID ownerId) {
        super(ownerId, 86, "Silvergill Adept", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.expansionSetCode = "LRW";
        this.subtype.add("Merfolk");
        this.subtype.add("Wizard");

        this.color.setBlue(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // As an additional cost to cast Silvergill Adept, reveal a Merfolk card from your hand or pay {3}.
        this.getSpellAbility().addCost(new SilvergillAdeptCost());
        // When Silvergill Adept enters the battlefield, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardControllerEffect(1)));
    }

    public SilvergillAdept(final SilvergillAdept card) {
        super(card);
    }

    @Override
    public SilvergillAdept copy() {
        return new SilvergillAdept(this);
    }
}

class SilvergillAdeptCost extends CostImpl<SilvergillAdeptCost> {

    private static final FilterCard filter = new FilterCard("Merfolk card");
    private GenericManaCost mana = new GenericManaCost(3);

    static {
        filter.add(new SubtypePredicate("Merfolk"));
    }

    public SilvergillAdeptCost() {
        this.text = "reveal a Merfolk card from your hand or pay {3}";
    }

    public SilvergillAdeptCost(SilvergillAdeptCost cost) {
        super(cost);
        this.mana = cost.mana;
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {

        Player player = game.getPlayer(controllerId);
        if (player == null) {
            return false;
        }

        paid = false;
        if (player.getHand().count(filter, game) > 0
                && player.chooseUse(Outcome.Benefit, "Reveal a Merfolk card? Otherwise pay {3}.", game)) {
            TargetCardInHand target = new TargetCardInHand(filter);
            if (player.choose(Outcome.Benefit, target, sourceId, game)) {
                Card card = player.getHand().get(target.getFirstTarget(), game);
                if (card != null) {
                    paid = true;
                    player.revealCards("Revealed card", new CardsImpl(card), game);
                }
            }
        } else {
            mana.clearPaid();
            if (mana.pay(ability, game, player.getId(), player.getId(), false)) {
                paid = true;
            }
        }

        return paid;
    }

    @Override
    public boolean canPay(UUID sourceId, UUID controllerId, Game game) {
        Player player = game.getPlayer(controllerId);
        if (player != null && player.getHand().count(filter, game) > 0) {
            return true;
        }

        if (mana.canPay(sourceId, controllerId, game)) {
            return true;
        }

        return false;
    }

    @Override
    public SilvergillAdeptCost copy() {
        return new SilvergillAdeptCost(this);
    }
}
