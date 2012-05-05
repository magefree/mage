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
package mage.sets.avacynrestored;

import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.MiracleAbility;
import mage.cards.CardImpl;
import mage.game.permanent.token.AngelToken;

import java.util.UUID;

/**
 *
 * @author noxx

 */
public class EntreatTheAngels extends CardImpl<EntreatTheAngels> {

    public EntreatTheAngels(UUID ownerId) {
        super(ownerId, 20, "Entreat the Angels", Rarity.MYTHIC, new CardType[]{CardType.SORCERY}, "{X}{X}{W}{W}{W}");
        this.expansionSetCode = "AVR";

        this.color.setWhite(true);

        // Put X 4/4 white Angel creature tokens with flying onto the battlefield.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new AngelToken(), new ManacostVariableValue()));

        // Miracle {X}{W}{W}
        this.addAbility(new MiracleAbility(new ManaCostsImpl("{X}{W}{W}")));
    }

    public EntreatTheAngels(final EntreatTheAngels card) {
        super(card);
    }

    @Override
    public EntreatTheAngels copy() {
        return new EntreatTheAngels(this);
    }
}
