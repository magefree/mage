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

import java.util.UUID;
import mage.MageInt;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.PreventAllDamageToSourceEffect;
import mage.abilities.effects.common.PreventDamageByTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.game.command.emblems.GideonOfTheTrialsEmblem;
import mage.game.permanent.token.Token;
import mage.target.TargetPermanent;

/**
 *
 * @author JRHerlehy
 */
public class GideonOfTheTrials extends CardImpl {

    public GideonOfTheTrials(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{W}{W}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GIDEON);

        //Starting Loyalty: 3
        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(3));

        // +1: Until your next turn, prevent all damage target permanent would deal.
        Effect effect = new PreventDamageByTargetEffect(Duration.UntilYourNextTurn);
        effect.setText("Until your next turn, prevent all damage target permanent would deal");
        LoyaltyAbility ability = new LoyaltyAbility(effect, 1);
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);

        // 0: Until end of turn, Gideon of the Trials becomes a 4/4 Human Soldier creature with indestructible that's still a planeswalker. Prevent all damage that would be dealt to him this turn.
        ability = new LoyaltyAbility(new BecomesCreatureSourceEffect(new GideonOfTheTrialsToken(), "planeswalker", Duration.EndOfTurn), 0);
        effect = new PreventAllDamageToSourceEffect(Duration.EndOfTurn);
        effect.setText("Prevent all damage that would be dealt to him this turn");
        ability.addEffect(effect);
        this.addAbility(ability);

        // 0: You get an emblem with "As long as you control a Gideon planeswalker, you can't lose the game and your opponent can't win the game."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new GideonOfTheTrialsEmblem()), 0));

    }

    public GideonOfTheTrials(final GideonOfTheTrials card) {
        super(card);
    }

    @Override
    public GideonOfTheTrials copy() {
        return new GideonOfTheTrials(this);
    }
}

class GideonOfTheTrialsToken extends Token {

    public GideonOfTheTrialsToken() {
        super("", "a 4/4 Human Soldier creature with indestructible");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.HUMAN);
        subtype.add(SubType.SOLDIER);
        power = new MageInt(4);
        toughness = new MageInt(4);
        this.addAbility(IndestructibleAbility.getInstance());
    }
}
