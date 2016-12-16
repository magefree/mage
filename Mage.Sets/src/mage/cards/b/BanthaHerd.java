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
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesMonstrousSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.MonstrosityAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.players.Player;

/**
 *
 * @author Styxo
 */
public class BanthaHerd extends CardImpl {

    public BanthaHerd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");
        this.subtype.add("Beast");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {X}{W}{W}: Monstrosity X.
        this.addAbility(new MonstrosityAbility("{X}{W}{W}", Integer.MAX_VALUE));

        // When Batha Herd becomes monstrous, create X 1/1 white Tusken Raider tokens.
        this.addAbility(new BecomesMonstrousSourceTriggeredAbility(new BathaHerdEffect()));
    }

    public BanthaHerd(final BanthaHerd card) {
        super(card);
    }

    @Override
    public BanthaHerd copy() {
        return new BanthaHerd(this);
    }
}

class BathaHerdEffect extends OneShotEffect {

    public BathaHerdEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "create X 1/1 white Tusken Raider tokens";
    }

    public BathaHerdEffect(final BathaHerdEffect effect) {
        super(effect);
    }

    @Override
    public BathaHerdEffect copy() {
        return new BathaHerdEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int xValue = ((BecomesMonstrousSourceTriggeredAbility) source).getMonstrosityValue();
            return new CreateTokenEffect(new TuskenRaiderToken(), xValue).apply(game, source);
        }
        return false;
    }
}

class TuskenRaiderToken extends Token {

    public TuskenRaiderToken() {
        super("Tusken Raider", "white Tusken Raider creature token", 1, 1);
        this.setOriginalExpansionSetCode("SWS");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add("Tusken");
        subtype.add("Raider");
    }
}
