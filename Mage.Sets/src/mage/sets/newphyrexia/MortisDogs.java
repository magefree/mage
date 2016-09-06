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
package mage.sets.newphyrexia;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.target.TargetPlayer;

/**
 *
 * @author North
 */
public class MortisDogs extends CardImpl {

    public MortisDogs(UUID ownerId) {
        super(ownerId, 66, "Mortis Dogs", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.expansionSetCode = "NPH";
        this.subtype.add("Hound");

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Mortis Dogs attacks, it gets +2/+0 until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(2, 0, Duration.EndOfTurn), false));
        // When Mortis Dogs dies, target player loses life equal to its power.
        Ability ability = new DiesTriggeredAbility(new LoseLifeTargetEffect(new SourcePermanentPowerCount()));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public MortisDogs(final MortisDogs card) {
        super(card);
    }

    @Override
    public MortisDogs copy() {
        return new MortisDogs(this);
    }
}