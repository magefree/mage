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
package mage.sets.urzasdestiny;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.EchoAbility;
import mage.cards.CardImpl;
import mage.game.permanent.token.GoblinToken;

/**
 *
 * @author Backfir3
 */
public class GoblinMarshal extends CardImpl<GoblinMarshal> {

    public GoblinMarshal(UUID ownerId) {
        super(ownerId, 85, "Goblin Marshal", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");
        this.expansionSetCode = "UDS";
        this.subtype.add("Goblin");
        this.subtype.add("Warrior");
        this.color.setRed(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(new EchoAbility("{4}{R}{R}"));
        // When Goblin Marshal enters the battlefield or dies, put two 1/1 red Goblin creature tokens onto the battlefield.
        Ability enterAbility = new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new GoblinToken(), 2), false);
        this.addAbility(enterAbility);
        // When Goblin Marshal enters the battlefield or dies, put two 1/1 red Goblin creature tokens onto the battlefield.
        Ability diesAbility = new DiesTriggeredAbility(new CreateTokenEffect(new GoblinToken(), 2), false);
        this.addAbility(diesAbility);
    }

    public GoblinMarshal(final GoblinMarshal card) {
        super(card);
    }

    @Override
    public GoblinMarshal copy() {
        return new GoblinMarshal(this);
    }
}
