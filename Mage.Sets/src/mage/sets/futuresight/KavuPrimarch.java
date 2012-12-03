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
package mage.sets.futuresight;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.costs.mana.KickerManaCost;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.counters.CounterType;

/**
 *
 * @author LevelX2
 */
public class KavuPrimarch extends CardImpl<KavuPrimarch> {

    public KavuPrimarch(UUID ownerId) {
        super(ownerId, 128, "Kavu Primarch", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.expansionSetCode = "FUT";
        this.subtype.add("Kavu");
        this.color.setGreen(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Convoke (Each creature you tap while casting this spell reduces its cost by {1} or by one mana of that creature's color.)
        this.addAbility(new ConvokeAbility());

        // Kicker {4} (You may pay an additional {4} as you cast this spell.)
        this.getSpellAbility().addOptionalCost(new KickerManaCost("{4}"));

        // If Kavu Primarch was kicked, it enters the battlefield with four +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(4)),KickedCondition.getInstance(), true,
                "If Kavu Primarch was kicked, it enters the battlefield with four +1/+1 counters on it.", ""));
    }

    public KavuPrimarch(final KavuPrimarch card) {
        super(card);
    }

    @Override
    public KavuPrimarch copy() {
        return new KavuPrimarch(this);
    }
}
