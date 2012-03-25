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
package mage.sets.morningtide;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.LoseLifeSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.game.permanent.token.Token;

/**
 *
 * @author Loki
 */
public class Bitterblossom extends CardImpl<Bitterblossom> {

    public Bitterblossom(UUID ownerId) {
        super(ownerId, 58, "Bitterblossom", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");
        this.expansionSetCode = "MOR";
        this.supertype.add("Tribal");
        this.subtype.add("Faerie");
        this.color.setBlack(true);
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new LoseLifeSourceEffect(1), Constants.TargetController.YOU, false);
        ability.addEffect(new CreateTokenEffect(new FaerieToken(), 1));
        this.addAbility(ability);
    }

    public Bitterblossom(final Bitterblossom card) {
        super(card);
    }

    @Override
    public Bitterblossom copy() {
        return new Bitterblossom(this);
    }
}

class FaerieToken extends Token {
    FaerieToken() {
        super("Faerie Rogue", "1/1 black Faerie Rogue creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add("Faerie");
        subtype.add("Rogue");
        power = new MageInt(1);
        toughness = new MageInt(1);
        this.addAbility(FlyingAbility.getInstance());
    }
}