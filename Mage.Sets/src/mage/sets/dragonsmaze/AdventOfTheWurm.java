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

package mage.sets.dragonsmaze;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.game.permanent.token.Token;

/**
 *
 * @author LevelX2
 */


public class AdventOfTheWurm extends CardImpl<AdventOfTheWurm> {

    public AdventOfTheWurm(UUID ownerId) {
        super(ownerId, 51, "Advent of the Wurm", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{1}{G}{G}{W}");
        this.expansionSetCode = "DGM";

        this.color.setGreen(true);
        this.color.setWhite(true);

        // Put a 5/5 green Wurm creature token with trample onto the battlefield.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new WurmToken()));
    }

    public AdventOfTheWurm(final AdventOfTheWurm card) {
        super(card);
    }

    @Override
    public AdventOfTheWurm copy() {
        return new AdventOfTheWurm(this);
    }
    private class WurmToken extends Token {

        private WurmToken() {
            super("Wurm", "5/5 green Wurm creature token with trample");
            cardType.add(Constants.CardType.CREATURE);
            color = ObjectColor.GREEN;
            subtype.add("Wurm");
            power = new MageInt(5);
            toughness = new MageInt(5);
            addAbility(TrampleAbility.getInstance());
        }

    }
}
