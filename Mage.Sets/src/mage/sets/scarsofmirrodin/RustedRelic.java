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

package mage.sets.scarsofmirrodin;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MetalcraftCondition;
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.effects.common.continious.BecomesCreatureSourceEffect;
import mage.cards.CardImpl;
import mage.game.permanent.token.Token;

/**
 *
 * @author Loki
 */
public class RustedRelic extends CardImpl<RustedRelic> {

    public RustedRelic (UUID ownerId) {
        super(ownerId, 199, "Rusted Relic", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.expansionSetCode = "SOM";
        this.addAbility(
                new SimpleStaticAbility(Zone.BATTLEFIELD,
                        new ConditionalContinousEffect(
                                new BecomesCreatureSourceEffect(new RustedRelicToken(), "artifact", Duration.Custom),
                                MetalcraftCondition.getInstance(),
                                "Metalcraft - Rusted Relic is a 5/5 Golem artifact creature as long as you control three or more artifacts")));
    }

    public RustedRelic (final RustedRelic card) {
        super(card);
    }

    @Override
    public RustedRelic copy() {
        return new RustedRelic(this);
    }
}

class RustedRelicToken extends Token {

    public RustedRelicToken() {
        super("Rusted Relic", "a 5/5 Golem artifact creature");
        cardType.add(Constants.CardType.CREATURE);
		subtype.add("Golem");
		power = new MageInt(5);
		toughness = new MageInt(5);
    }
}