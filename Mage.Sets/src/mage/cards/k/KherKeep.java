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
package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.permanent.token.Token;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class KherKeep extends CardImpl {

    public KherKeep(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        addSuperType(SuperType.LEGENDARY);

        // {tap}: Add {C} to your mana pool.
        this.addAbility(new ColorlessManaAbility());
        // {1}{R}, {tap}: Create a 0/1 red Kobold creature token named Kobolds of Kher Keep.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new KherKeepKoboldToken()), new ManaCostsImpl("{1}{R}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public KherKeep(final KherKeep card) {
        super(card);
    }

    @Override
    public KherKeep copy() {
        return new KherKeep(this);
    }
}

class KherKeepKoboldToken extends Token {

    public KherKeepKoboldToken() {
        super("Kobolds of Kher Keep", "0/1 red Kobold creature token named Kobolds of Kher Keep");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add("Kobold");
        power = new MageInt(0);
        toughness = new MageInt(1);
    }

}
