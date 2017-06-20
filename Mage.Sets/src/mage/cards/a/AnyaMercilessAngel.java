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
package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author emerald000
 */
public class AnyaMercilessAngel extends CardImpl {

    public AnyaMercilessAngel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Angel");
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Anya, Merciless Angel gets +3/+3 for each opponent whose life total is less than half his or her starting life total.
        DynamicValue dValue = new MultipliedValue(new AnyaMercilessAngelDynamicValue(), 3);
        Effect effect = new BoostSourceEffect(dValue, dValue, Duration.WhileOnBattlefield);
        effect.setText("{this} gets +3/+3 for each opponent whose life total is less than half his or her starting life total");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceEffect(dValue, dValue, Duration.WhileOnBattlefield)));
        
        // As long as an opponent's life total is less than half his or her starting life total, Anya has indestructible.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield),
                        AnyaMercilessAngelCondition.instance,
                        "As long as an opponent's life total is less than half his or her starting life total, {this} has indestructible")));
    }

    public AnyaMercilessAngel(final AnyaMercilessAngel card) {
        super(card);
    }

    @Override
    public AnyaMercilessAngel copy() {
        return new AnyaMercilessAngel(this);
    }
}

class AnyaMercilessAngelDynamicValue implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int opponentCount = 0;
        Player controller = game.getPlayer(sourceAbility.getControllerId());
        if (controller != null) {
            int startingLifeTotal = game.getLife();
            for (UUID opponentId : game.getOpponents(controller.getId())) {
                Player opponent = game.getPlayer(opponentId);
                if (opponent != null && opponent.getLife() < startingLifeTotal / 2) {
                    opponentCount++;
                }
            }
        }
        return opponentCount;
    }

    @Override
    public AnyaMercilessAngelDynamicValue copy() {
        return new AnyaMercilessAngelDynamicValue();
    }

    @Override
    public String getMessage() {
        return "number of opponents whose life total is less than half his or her starting life total";
    }

    @Override
    public String toString() {
        return "X";
    }
}

enum AnyaMercilessAngelCondition implements Condition {

    instance;
    @Override
    public boolean apply(Game game, Ability source) {
        return new AnyaMercilessAngelDynamicValue().calculate(game, source, null) > 0;
    }

    @Override
    public String toString() {
        return "an opponent's life total is less than half his or her starting life total";
    }
}
