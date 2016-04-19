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
package mage.sets.shadowsoverinnistrad;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.game.Game;
import mage.target.common.TargetCreatureOrPlaneswalker;

/**
 *
 * @author fireshoes
 */
public class WolfOfDevilsBreach extends CardImpl {

    public WolfOfDevilsBreach(UUID ownerId) {
        super(ownerId, 192, "Wolf of Devil's Breach", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");
        this.expansionSetCode = "SOI";
        this.subtype.add("Elemental");
        this.subtype.add("Wolf");
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Whenever Wolf of Devil's Breach attacks, you may pay {1}{R} and discard a card. If you do, Wolf of Devil's Breach deals
        // damage to target creature or planeswalker equal to the discarded card's converted mana cost.
        Costs toPay = new CostsImpl<>();
        toPay.add(new ManaCostsImpl<>("{1}{R}"));
        toPay.add(new DiscardCardCost());
        Ability ability = new AttacksTriggeredAbility(new DoIfCostPaid(new DamageTargetEffect(new WolfOfDevilsBreachDiscardCostCardConvertedMana()), toPay,
                "Pay {1}{R} and discard a card to let {this} do damage to target creature or planeswalker equal to the discarded card's converted mana cost?", true), false,
                "Whenever {this} attacks you may pay {1}{R} and discard a card. If you do, {this} deals damage to target creature or planeswalker "
                + "equal to the discarded card's converted mana cost.");
        ability.addTarget(new TargetCreatureOrPlaneswalker());
        this.addAbility(ability);
    }

    public WolfOfDevilsBreach(final WolfOfDevilsBreach card) {
        super(card);
    }

    @Override
    public WolfOfDevilsBreach copy() {
        return new WolfOfDevilsBreach(this);
    }
}

class WolfOfDevilsBreachDiscardCostCardConvertedMana implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        for (Effect sourceEffect : sourceAbility.getEffects()) {
            if (sourceEffect instanceof DoIfCostPaid) {
                Cost doCosts = ((DoIfCostPaid) sourceEffect).getCost();
                if (doCosts instanceof Costs) {
                    Costs costs = (Costs) doCosts;
                    for (Object cost : costs) {
                        if (cost instanceof DiscardCardCost) {
                            DiscardCardCost discardCost = (DiscardCardCost) cost;
                            int cmc = 0;
                            for (Card card : discardCost.getCards()) {
                                cmc += card.getConvertedManaCost();
                            }
                            return cmc;
                        }
                    }
                }
            }
        }
        return 0;
    }

    @Override
    public WolfOfDevilsBreachDiscardCostCardConvertedMana copy() {
        return new WolfOfDevilsBreachDiscardCostCardConvertedMana();
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public String getMessage() {
        return "the discarded card's converted mana cost";
    }
}
