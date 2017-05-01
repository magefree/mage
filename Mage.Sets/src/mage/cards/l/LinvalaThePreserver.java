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
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.OpponentControlsMoreCondition;
import mage.abilities.condition.common.OpponentHasMoreLifeCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.LinvalaAngelToken;

/**
 *
 * @author fireshoes
 */
public class LinvalaThePreserver extends CardImpl {

    public LinvalaThePreserver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Angel");
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Linvala, the Preserver enters the battlefield, if an opponent has more life than you, you gain 5 life.
        this.addAbility(new ConditionalTriggeredAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(5), false),
                OpponentHasMoreLifeCondition.instance,
                "When {this} enters the battlefield, if an opponent has more life than you, you gain 5 life."));

        // When Linvala enters the battlefield, if an opponent controls more creatures than you, create a 3/3 white Angel creature token with flying.
        this.addAbility(new ConditionalTriggeredAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new LinvalaAngelToken()), false),
                new OpponentControlsMoreCondition(new FilterCreaturePermanent()),
                "When {this} enters the battlefield, if an opponent controls more creatures than you, create a 3/3 white Angel creature token with flying."));
    }

    public LinvalaThePreserver(final LinvalaThePreserver card) {
        super(card);
    }

    @Override
    public LinvalaThePreserver copy() {
        return new LinvalaThePreserver(this);
    }
}
