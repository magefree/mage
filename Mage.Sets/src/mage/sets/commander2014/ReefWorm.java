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
package mage.sets.commander2014;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.game.permanent.token.Token;

/**
 *
 * @author LevelX2
 */
public class ReefWorm extends CardImpl {

    public ReefWorm(UUID ownerId) {
        super(ownerId, 16, "Reef Worm", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.expansionSetCode = "C14";
        this.subtype.add("Worm");

        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // When Reef Worm dies, put a 3/3 blue Fish creature token onto the battlefield with "When this creature dies, put a 6/6 blue Whale creature token onto the battlefield with "When this creature dies, put a 9/9 blue Kraken creature token onto the battlefield.""
        addAbility(new DiesTriggeredAbility(new CreateTokenEffect(new ReefWormFishToken())));
    }

    public ReefWorm(final ReefWorm card) {
        super(card);
    }

    @Override
    public ReefWorm copy() {
        return new ReefWorm(this);
    }
}

class ReefWormFishToken extends Token {

    ReefWormFishToken() {
        super("Fish", "a 3/3 blue Fish creature token onto the battlefield with \"When this creature dies, put a 6/6 blue Whale creature token onto the battlefield with \"When this creature dies, put a 9/9 blue Kraken creature token onto the battlefield.\"\"");
        setOriginalExpansionSetCode("C14");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add("Fish");
        power = new MageInt(3);
        toughness = new MageInt(3);

        addAbility(new DiesTriggeredAbility(new CreateTokenEffect(new ReefWormWhaleToken())));
    }
}

class ReefWormWhaleToken extends Token {

    ReefWormWhaleToken() {
        super("Whale", "a 6/6 blue Whale creature token with \"When this creature dies, put a 9/9 blue Kraken creature token onto the battlefield.\"");
        setOriginalExpansionSetCode("C14");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add("Whale");
        power = new MageInt(6);
        toughness = new MageInt(6);

        addAbility(new DiesTriggeredAbility(new CreateTokenEffect(new ReefWormKrakenToken())));
    }
}

class ReefWormKrakenToken extends Token {

    ReefWormKrakenToken() {
        super("Kraken", "a 9/9 blue Kraken creature token");
        setOriginalExpansionSetCode("C14");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add("Kraken");
        power = new MageInt(9);
        toughness = new MageInt(9);
    }
}
