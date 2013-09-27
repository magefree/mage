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
package mage.sets.odyssey;

import java.util.UUID;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TimingRule;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.FlashbackAbility;
import mage.filter.FilterCard;

/**
 *
 * @author cbt33
 */
 
public class AncestralTribute extends CardImpl<AncestralTribute> {

    public AncestralTribute(UUID ownerId) {
        super(ownerId, 2, "Ancestral Tribute", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{5}{W}{W}");
        this.expansionSetCode = "ODY";

        this.color.setWhite(true);
        
        
        // You gain 2 life for each card in your graveyard.
		this.getSpellAbility().addEffect(new GainLifeEffect((new CardsInControllerGraveyardCount(new FilterCard(), 2))));
        
		// Flashback {9}{W}{W}{W}
		this.addAbility(new FlashbackAbility(new ManaCostsImpl("{9}{W}{W}{W}"), TimingRule.SORCERY));

        
    }

    public AncestralTribute(final AncestralTribute card) {
        super(card);
    }

    @Override
    public AncestralTribute copy() {
        return new AncestralTribute(this);
    }
}
