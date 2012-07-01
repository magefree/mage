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
package mage.sets.fifthedition;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.game.permanent.token.Token;

/**
 *
 * @author jeffwadsworth
 */
public class BreedingPit extends CardImpl<BreedingPit> {

    public BreedingPit(UUID ownerId) {
        super(ownerId, 10, "Breeding Pit", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}");
        this.expansionSetCode = "5ED";

        this.color.setBlack(true);

        // At the beginning of your upkeep, sacrifice Breeding Pit unless you pay {B}{B}.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Constants.Zone.BATTLEFIELD, new SacrificeSourceUnlessPaysEffect(new ManaCostsImpl("{B}{B}")), Constants.TargetController.YOU, false));
        
        // At the beginning of your end step, put a 0/1 black Thrull creature token onto the battlefield.
        this.addAbility(new BeginningOfYourEndStepTriggeredAbility(new CreateTokenEffect(new ThrullToken()), false));
    }

    public BreedingPit(final BreedingPit card) {
        super(card);
    }

    @Override
    public BreedingPit copy() {
        return new BreedingPit(this);
    }
}

class ThrullToken extends Token {
    
    ThrullToken() {
        super("Thrull", "0/1 black Thrull creature token");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add("Thrull");
        power = new MageInt(0);
        toughness = new MageInt(1);
    }
}
