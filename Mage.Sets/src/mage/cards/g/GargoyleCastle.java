/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
 */
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.permanent.token.Token;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class GargoyleCastle extends CardImpl {

    public GargoyleCastle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, null);
        // {T}: Add {C} to your mana pool.

        this.addAbility(new ColorlessManaAbility());
        // {T}, {5}, Sacrifice Gargoyle Castle: Put a 3/4 colorless Gargoyle artifact creature token with flying onto the battlefield.
        this.addAbility(new GargoyleCastleAbility());
    }

    public GargoyleCastle(final GargoyleCastle card) {
        super(card);
    }

    @Override
    public GargoyleCastle copy() {
        return new GargoyleCastle(this);
    }

}

class GargoyleCastleAbility extends ActivatedAbilityImpl {

    public GargoyleCastleAbility() {
        super(Zone.BATTLEFIELD, null);
        addCost(new TapSourceCost());
        addCost(new GenericManaCost(5));
        addCost(new SacrificeSourceCost());
        addEffect(new CreateTokenEffect(new GargoyleToken()));
    }

    public GargoyleCastleAbility(final GargoyleCastleAbility ability) {
        super(ability);
    }

    @Override
    public GargoyleCastleAbility copy() {
        return new GargoyleCastleAbility(this);
    }

}

class GargoyleToken extends Token {

    public GargoyleToken() {
        super("Gargoyle", "3/4 colorless Gargoyle artifact creature token with flying");
        cardType.add(CardType.CREATURE);
        cardType.add(CardType.ARTIFACT);
        subtype.add("Gargoyle");
        power = new MageInt(3);
        toughness = new MageInt(4);
        addAbility(FlyingAbility.getInstance());
    }
}
