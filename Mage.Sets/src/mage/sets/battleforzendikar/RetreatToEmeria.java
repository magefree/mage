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
package mage.sets.battleforzendikar;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Mode;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.game.permanent.token.Token;

/**
 *
 * @author fireshoes
 */
public class RetreatToEmeria extends CardImpl {

    public RetreatToEmeria(UUID ownerId) {
        super(ownerId, 44, "Retreat to Emeria", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");
        this.expansionSetCode = "BFZ";

        // <i>Landfall</i> - Whenever a land enters the battlefield under you control, choose one - Put a 1/1 white Kor Ally creature token onto the battlefield; or Creatures you control get +1/+1 until end of turn.
        LandfallAbility ability = new LandfallAbility(new CreateTokenEffect(new KorAllyToken()), false);
        Mode mode = new Mode();
        mode.getEffects().add(new BoostControlledEffect(1, 1, Duration.EndOfTurn));
        ability.addMode(mode);
        this.addAbility(ability);
    }

    public RetreatToEmeria(final RetreatToEmeria card) {
        super(card);
    }

    @Override
    public RetreatToEmeria copy() {
        return new RetreatToEmeria(this);
    }
}

class KorAllyToken extends Token {

    public KorAllyToken() {
        super("Kor Ally", "1/1 white Kor Ally creature token");
        cardType.add(CardType.CREATURE);
        subtype.add("Kor");
        subtype.add("Ally");
        color.setWhite(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }
}