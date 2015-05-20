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

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.common.DiscardXTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TimingRule;
import mage.filter.FilterCard;
import mage.target.common.TargetCreatureOrPlayerAmount;

/**
 *
 * @author LevelX2
 */
public class Conflagrate extends CardImpl {

    public Conflagrate(UUID ownerId) {
        super(ownerId, 151, "Conflagrate", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{X}{X}{R}");
        this.expansionSetCode = "TSP";


        // Conflagrate deals X damage divided as you choose among any number of target creatures and/or players.
        DynamicValue xValue = new ManacostVariableValue();
        this.getSpellAbility().addEffect(new DamageMultiEffect(xValue));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlayerAmount(xValue));        
        
        // Flashback-{R}{R}, Discard X cards.
        Ability ability = new FlashbackAbility(new ManaCostsImpl("{R}{R}"), TimingRule.SORCERY);
        ability.addCost(new DiscardXTargetCost(new FilterCard("cards")));
        this.addAbility(ability);
        
    }

    public Conflagrate(final Conflagrate card) {
        super(card);
    }

    @Override
    public Conflagrate copy() {
        return new Conflagrate(this);
    }
}
