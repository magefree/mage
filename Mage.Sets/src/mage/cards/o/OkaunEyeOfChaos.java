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
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.WinsCoinFlipTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.dynamicvalue.common.SourcePermanentToughnessValue;
import mage.abilities.effects.common.FlipUntilLoseEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.PartnerWithAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;

/**
 *
 * @author TheElk801
 */
public final class OkaunEyeOfChaos extends CardImpl {

    public OkaunEyeOfChaos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.CYCLOPS);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Partner with Zndrsplt, Eye of Wisdom (When this creature enters the battlefield, target player may put Zndrsplt into their hand from their library, then shuffle.)
        this.addAbility(new PartnerWithAbility("Zndrsplt, Eye of Wisdom", true));

        // At the beginning of combat on your turn, flip a coin until you lose a flip.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new FlipUntilLoseEffect(), TargetController.YOU, false));

        // Whenever a player wins a coin flip, double Okaun's power and toughness until end of turn.
        DynamicValue sourcePower = new SourcePermanentPowerCount();
        DynamicValue sourceToughness = new SourcePermanentToughnessValue();
        this.addAbility(new WinsCoinFlipTriggeredAbility(
                new BoostSourceEffect(
                        sourcePower,
                        sourceToughness,
                        Duration.EndOfTurn,
                        true
                ).setText("double {this}'s power and toughness until end of turn")
        ));
    }

    public OkaunEyeOfChaos(final OkaunEyeOfChaos card) {
        super(card);
    }

    @Override
    public OkaunEyeOfChaos copy() {
        return new OkaunEyeOfChaos(this);
    }
}
