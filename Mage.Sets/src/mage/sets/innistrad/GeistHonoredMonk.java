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
package mage.sets.innistrad;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continious.SetPowerToughnessSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.permanent.token.SpiritWhiteToken;

import java.util.UUID;

/**
 * @author nantuko
 */
public class GeistHonoredMonk extends CardImpl<GeistHonoredMonk> {

    public GeistHonoredMonk(UUID ownerId) {
        super(ownerId, 17, "Geist-Honored Monk", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
        this.expansionSetCode = "ISD";
        this.subtype.add("Human");
        this.subtype.add("Monk");

        this.color.setWhite(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        this.addAbility(VigilanceAbility.getInstance());

        // Geist-Honored Monk's power and toughness are each equal to the number of creatures you control.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.ALL, new SetPowerToughnessSourceEffect(new PermanentsOnBattlefieldCount(new FilterControlledCreaturePermanent()), Constants.Duration.EndOfGame)));

        // When Geist-Honored Monk enters the battlefield, put two 1/1 white Spirit creature tokens with flying onto the battlefield.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new SpiritWhiteToken(), 2)));
    }

    public GeistHonoredMonk(final GeistHonoredMonk card) {
        super(card);
    }

    @Override
    public GeistHonoredMonk copy() {
        return new GeistHonoredMonk(this);
    }
}
