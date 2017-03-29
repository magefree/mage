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
package mage.cards.q;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.BecomesMonarchSourceEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.token.Token;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class QueenMarchesa extends CardImpl {

    public QueenMarchesa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}{B}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Human");
        this.subtype.add("Assassin");
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());
        // Haste
        this.addAbility(HasteAbility.getInstance());
        // When Queen Marchesa enters the battlefield, you become the monarch.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BecomesMonarchSourceEffect(), false));

        // At the beginning of your upkeep, if an opponent is the monarch, create a 1/1 black Assassin creature token with deathtouch and haste.
        this.addAbility(new ConditionalTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(new CreateTokenEffect(new QueenMarchesaAssassinToken()), TargetController.YOU, false),
                OpponentIsMonarchCondition.getInstance(),
                "At the beginning of your upkeep, if an opponent is the monarch, create a 1/1 black Assassin creature token with deathtouch and haste."));
    }

    public QueenMarchesa(final QueenMarchesa card) {
        super(card);
    }

    @Override
    public QueenMarchesa copy() {
        return new QueenMarchesa(this);
    }
}

class OpponentIsMonarchCondition implements Condition {

    private final static OpponentIsMonarchCondition instance = new OpponentIsMonarchCondition();

    public static Condition getInstance() {
        return instance;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getOpponents(source.getControllerId()).contains(game.getMonarchId());
    }

    @Override
    public String toString() {
        return "an opponent is the monarch";
    }
}

class QueenMarchesaAssassinToken extends Token {

    QueenMarchesaAssassinToken() {
        super("Assassin", "1/1 black Assassin creature tokens with deathtouch and haste");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add("Assassin");
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(DeathtouchAbility.getInstance());
        addAbility(HasteAbility.getInstance());
    }
}
