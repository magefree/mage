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
package mage.sets.urzassaga;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Mana;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.continious.GainAbilityAllEffect;
import mage.abilities.mana.ManaAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledLandPermanent;

/**
 *
 * @author Plopman
 */
public class RainOfFilth extends CardImpl<RainOfFilth> {

    public RainOfFilth(UUID ownerId) {
        super(ownerId, 151, "Rain of Filth", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{B}");
        this.expansionSetCode = "USG";

        this.color.setBlack(true);

        // Until end of turn, lands you control gain "Sacrifice this land: Add {B} to your mana pool."
        ManaAbility ability = new SimpleManaAbility(Constants.Zone.BATTLEFIELD, Mana.BlackMana, new SacrificeSourceCost());
        this.getSpellAbility().addEffect(new GainAbilityAllEffect(ability, Constants.Duration.EndOfTurn, new FilterControlledLandPermanent()));
    }

    public RainOfFilth(final RainOfFilth card) {
        super(card);
    }

    @Override
    public RainOfFilth copy() {
        return new RainOfFilth(this);
    }
}
