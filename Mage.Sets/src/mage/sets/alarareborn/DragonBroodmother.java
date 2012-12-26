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
package mage.sets.alarareborn;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DevourEffect;
import mage.abilities.keyword.DevourAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.game.permanent.token.Token;

/**
 *
 * @author LevelX2
 */
public class DragonBroodmother extends CardImpl<DragonBroodmother> {

    public DragonBroodmother(UUID ownerId) {
        super(ownerId, 53, "Dragon Broodmother", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{2}{R}{R}{R}{G}");
        this.expansionSetCode = "ARB";
        this.subtype.add("Dragon");
        this.color.setGreen(true);
        this.color.setRed(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of each upkeep, put a 1/1 red and green Dragon creature token with flying and devour 2 onto the battlefield. (As the token enters the battlefield, you may sacrifice any number of creatures. It enters the battlefield with twice that many +1/+1 counters on it.)
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new CreateTokenEffect(new DragonToken()), Constants.TargetController.ANY, false));
    }

    public DragonBroodmother(final DragonBroodmother card) {
        super(card);
    }

    @Override
    public DragonBroodmother copy() {
        return new DragonBroodmother(this);
    }
}

class DragonToken extends Token {
    DragonToken() {
        super("Dragon", "1/1 red and green Dragon creature token with flying and devour 2");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        color.setRed(true);
        subtype.add("Dragon");
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(FlyingAbility.getInstance());
        addAbility(new DevourAbility(DevourEffect.DevourFactor.Devour2));
    }
}