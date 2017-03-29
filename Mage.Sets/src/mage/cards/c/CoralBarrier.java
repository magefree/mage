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
package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.IslandwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.Token;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class CoralBarrier extends CardImpl {

    public CoralBarrier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add("Wall");

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
        // When Coral Barrier enters the battlefield, create a 1/1 blue Squid creature token with islandwalk.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new CoralBarrierSquidToken()), false));
        
    }

    public CoralBarrier(final CoralBarrier card) {
        super(card);
    }

    @Override
    public CoralBarrier copy() {
        return new CoralBarrier(this);
    }
}

class CoralBarrierSquidToken extends Token {

    public CoralBarrierSquidToken() {
        super("Squid", "1/1 blue Squid creature token with islandwalk");
        this.setOriginalExpansionSetCode("M15");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add("Squid");
        power = new MageInt(1);
        toughness = new MageInt(1);

        this.addAbility(new IslandwalkAbility());
    }

}