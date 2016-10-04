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
package mage.cards.n;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.VariableCostImpl;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.dynamicvalue.common.DiscardCostCardConvertedMana;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCreatureOrPlaneswalker;

/**
 *
 * @author LevelX2
 */
public class NahirisWrath extends CardImpl {

    public NahirisWrath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{R}");

        // As an additional cost to cast Nahiri's Wrath, discard X cards.
        this.getSpellAbility().addCost(new NahirisWrathAdditionalCost());

        // Nahiri's Wrath deals damage equal to the total converted mana cost of the discarded cards to each of up to X target creatures and/or planeswalkers.
        Effect effect = new DamageTargetEffect(new DiscardCostCardConvertedMana());
        effect.setText("{this} deals damage equal to the total converted mana cost of the discarded cards to each of up to X target creatures and/or planeswalkers");
        this.getSpellAbility().addEffect(effect);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        int numTargets = 0;
        for (VariableCost cost : ability.getCosts().getVariableCosts()) {
            if (cost instanceof NahirisWrathAdditionalCost) {
                numTargets = ((NahirisWrathAdditionalCost) cost).getAmount();
                break;
            }
        }
        if (numTargets > 0) {
            ability.addTarget(new TargetCreatureOrPlaneswalker(0, numTargets, new FilterCreatureOrPlaneswalkerPermanent(), false));
        }
    }

    public NahirisWrath(final NahirisWrath card) {
        super(card);
    }

    @Override
    public NahirisWrath copy() {
        return new NahirisWrath(this);
    }
}

class NahirisWrathAdditionalCost extends VariableCostImpl {

    NahirisWrathAdditionalCost() {
        super("cards to discard");
        this.text = "As an additional cost to cast {this}, discard X cards";
    }

    NahirisWrathAdditionalCost(final NahirisWrathAdditionalCost cost) {
        super(cost);
    }

    @Override
    public NahirisWrathAdditionalCost copy() {
        return new NahirisWrathAdditionalCost(this);
    }

    @Override
    public int getMaxValue(Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            return controller.getHand().size();
        }
        return 0;
    }

    @Override
    public Cost getFixedCostsFromAnnouncedValue(int xValue) {
        TargetCardInHand target = new TargetCardInHand(xValue, new FilterCard("cards to discard"));
        return new DiscardTargetCost(target);
    }
}
