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
package mage.cards.g;

import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.PreventAllDamageToSourceEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.command.emblems.GideonAllyOfZendikarEmblem;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.IndestructibleAbility;
import mage.game.permanent.token.KnightAllyToken;
import mage.game.permanent.token.Token;

/**
 *
 * @author fireshoes
 */
public class GideonAllyOfZendikar extends CardImpl {

    public GideonAllyOfZendikar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{W}{W}");
        this.subtype.add("Gideon");

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(4));

        // +1: Until end of turn, Gideon, Ally of Zendikar becomes a 5/5 Human Soldier Ally creature with indestructible that's still a planeswalker. Prevent all damage that would be dealt to him this turn.
        LoyaltyAbility ability = new LoyaltyAbility(new BecomesCreatureSourceEffect(new GideonAllyOfZendikarToken(), "planeswalker", Duration.EndOfTurn), 1);
        Effect effect = new PreventAllDamageToSourceEffect(Duration.EndOfTurn);
        effect.setText("Prevent all damage that would be dealt to him this turn");
        ability.addEffect(effect);
        this.addAbility(ability);

        // 0: Create a 2/2 white Knight Ally creature token.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new KnightAllyToken()), 0));

        // -4: You get an emblem with "Creatures you control get +1/+1."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new GideonAllyOfZendikarEmblem()), -4));
    }

    public GideonAllyOfZendikar(final GideonAllyOfZendikar card) {
        super(card);
    }

    @Override
    public GideonAllyOfZendikar copy() {
        return new GideonAllyOfZendikar(this);
    }
}

class GideonAllyOfZendikarToken extends Token {

    public GideonAllyOfZendikarToken() {
        super("", "5/5 Human Soldier Ally creature with indestructible");
        cardType.add(CardType.CREATURE);
        subtype.add("Human");
        subtype.add("Soldier");
        subtype.add("Ally");
        power = new MageInt(5);
        toughness = new MageInt(5);

        addAbility(IndestructibleAbility.getInstance());
    }
}
