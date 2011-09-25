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

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.common.DrawCardControllerEffect;
import mage.abilities.effects.common.continious.SetPowerToughnessSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;

/**
 *
 * @author North
 */
public class Sturmgeist extends CardImpl<Sturmgeist> {

    public Sturmgeist(UUID ownerId) {
        super(ownerId, 82, "Sturmgeist", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        this.expansionSetCode = "ISD";
        this.subtype.add("Spirit");

        this.color.setBlue(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        this.addAbility(FlyingAbility.getInstance());
        // Sturmgeist's power and toughness are each equal to the number of cards in your hand.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetPowerToughnessSourceEffect(new CardsInControllerHandCount(), Duration.WhileOnBattlefield)));
        // Whenever Sturmgeist deals combat damage to a player, draw a card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new DrawCardControllerEffect(1), false));
    }

    public Sturmgeist(final Sturmgeist card) {
        super(card);
    }

    @Override
    public Sturmgeist copy() {
        return new Sturmgeist(this);
    }
}
