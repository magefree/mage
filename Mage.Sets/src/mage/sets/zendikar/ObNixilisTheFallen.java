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

package mage.sets.zendikar;

import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 *
 * @author Loki
 */
public class ObNixilisTheFallen extends CardImpl<ObNixilisTheFallen> {

    public ObNixilisTheFallen (UUID ownerId) {
        super(ownerId, 107, "Ob Nixilis, the Fallen", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.expansionSetCode = "ZEN";
		this.supertype.add("Legendary");
        this.subtype.add("Demon");
        this.color.setBlack(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Landfall - Whenever a land enters the battlefield under your control, you may have target player lose 3 life. If you do, put three +1/+1 counters on Ob Nixilis, the Fallen.
        Ability ability = new LandfallAbility(new LoseLifeTargetEffect(3), true);
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance(3)));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public ObNixilisTheFallen (final ObNixilisTheFallen card) {
        super(card);
    }

    @Override
    public ObNixilisTheFallen copy() {
        return new ObNixilisTheFallen(this);
    }
}
