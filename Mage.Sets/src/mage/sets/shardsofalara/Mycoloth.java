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
package mage.sets.shardsofalara;

import java.util.UUID;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DevourEffect.DevourFactor;
import mage.abilities.keyword.DevourAbility;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.permanent.token.SaprolingToken;

/**
 *
 * @author LevelX2
 */
public class Mycoloth extends CardImpl {

    public Mycoloth(UUID ownerId) {
        super(ownerId, 140, "Mycoloth", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");
        this.expansionSetCode = "ALA";
        this.subtype.add("Fungus");

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Devour 2 (As this enters the battlefield, you may sacrifice any number of creatures. This creature enters the battlefield with twice that many +1/+1 counters on it.)
        this.addAbility(new DevourAbility(DevourFactor.Devour2));

        // At the beginning of your upkeep, put a 1/1 green Saproling creature token onto the battlefield for each +1/+1 counter on Mycoloth.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new CreateTokenEffect(new SaprolingToken(),new CountersSourceCount(CounterType.P1P1)),
                TargetController.YOU,
                false
            ));
    }

    public Mycoloth(final Mycoloth card) {
        super(card);
    }

    @Override
    public Mycoloth copy() {
        return new Mycoloth(this);
    }
}
