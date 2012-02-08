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
package mage.sets.darkascension;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.condition.common.FatefulHourCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.game.permanent.token.Token;

/**
 *
 * @author anonymous
 */
public class GatherTheTownsfolk extends CardImpl<GatherTheTownsfolk> {

    public GatherTheTownsfolk(UUID ownerId) {
        super(ownerId, 8, "Gather the Townsfolk", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{1}{W}");
        this.expansionSetCode = "DKA";

        this.color.setWhite(true);

        // Put two 1/1 white Human creature tokens onto the battlefield.
        // Fateful hour - If you have 5 or less life, put five of those tokens onto the battlefield instead.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new CreateTokenEffect(new HumanToken(), 5), new CreateTokenEffect(new HumanToken(), 2),
                FatefulHourCondition.getInstance(), "Put two 1/1 white Human creature tokens onto the battlefield. If you have 5 or less life, put five of those tokens onto the battlefield instead"));
    }

    public GatherTheTownsfolk(final GatherTheTownsfolk card) {
        super(card);
    }

    @Override
    public GatherTheTownsfolk copy() {
        return new GatherTheTownsfolk(this);
    }
}

class HumanToken extends Token {

    public HumanToken() {
        super("Human", "1/1 white Human creature token");
        cardType.add(CardType.CREATURE);
        color = ObjectColor.WHITE;
        subtype.add("Human");
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

}
