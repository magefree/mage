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
import mage.abilities.effects.common.PreventAllDamageToSourceEffect;
import mage.abilities.effects.common.TapAllEffect;
import mage.abilities.effects.common.UntapAllEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.game.permanent.token.Token;

/**
 *
 * @author fireshoes
 */
public class GideonMartialParagon extends CardImpl {

    public GideonMartialParagon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{W}");
        this.addSuperType(SuperType.LEGENDARY);

        this.subtype.add("Gideon");

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(5));

        // +2: Untap all creatures you control. Those creatures get +1/+1 until end of turn.
        LoyaltyAbility ability = new LoyaltyAbility(new UntapAllEffect(new FilterControlledCreaturePermanent()), 2);
        Effect effect = new BoostControlledEffect(1, 1, Duration.EndOfTurn);
        effect.setText("Those creatures get +1/+1 until end of turn");
        ability.addEffect(effect);
        this.addAbility(ability);

        // 0: Until end of turn, Gideon, Martial Paragon, becomes a 5/5 Human Soldier creature with indestructible that's still a planeswalker.
        // Prevent all damage that would be dealt to him this turn.
        ability = new LoyaltyAbility(new BecomesCreatureSourceEffect(new GideonMartialParagonToken(), "planeswalker", Duration.EndOfTurn), 0);
        effect = new PreventAllDamageToSourceEffect(Duration.EndOfTurn);
        effect.setText("Prevent all damage that would be dealt to him this turn");
        ability.addEffect(effect);
        this.addAbility(ability);

        // -10: Creatures you control get +2/+2 until end of turn. Tap all creatures your opponents control.
        ability = new LoyaltyAbility(new BoostControlledEffect(2, 2, Duration.EndOfTurn), -10);
        effect = new TapAllEffect(new FilterOpponentsCreaturePermanent());
        effect.setText("Tap all creatures your opponents control");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    public GideonMartialParagon(final GideonMartialParagon card) {
        super(card);
    }

    @Override
    public GideonMartialParagon copy() {
        return new GideonMartialParagon(this);
    }
}

class GideonMartialParagonToken extends Token {

    public GideonMartialParagonToken() {
        super("", "5/5 Human Soldier creature with indestructible");
        cardType.add(CardType.CREATURE);
        subtype.add("Human");
        subtype.add("Soldier");
        power = new MageInt(5);
        toughness = new MageInt(5);

        addAbility(IndestructibleAbility.getInstance());
    }
}
