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
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public class LurkingEvil extends CardImpl {

    public LurkingEvil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{B}{B}{B}");

        // Pay half your life, rounded up: Lurking Evil becomes a 4/4 Horror creature with flying.
        Effect effect = new BecomesCreatureSourceEffect(new LurkingEvilToken(), null, Duration.EndOfGame, true, false);
        effect.setText("{this} becomes a 4/4 Horror creature with flying");
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new LurkingEvilCost()));
    }

    public LurkingEvil(final LurkingEvil card) {
        super(card);
    }

    @Override
    public LurkingEvil copy() {
        return new LurkingEvil(this);
    }
}

class LurkingEvilCost extends CostImpl {

    LurkingEvilCost() {
        this.text = "Pay half your life, rounded up";
    }

    LurkingEvilCost(LurkingEvilCost cost) {
        super(cost);
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        Player controller = game.getPlayer(controllerId);
        return controller != null && (controller.getLife() < 1 || controller.canPayLifeCost());
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            int currentLife = controller.getLife();
            int lifeToPay = (currentLife + currentLife % 2) / 2; // Divide by two and round up.
            if (lifeToPay < 0) {
                this.paid = true;
            } else {
                this.paid = (controller.loseLife(lifeToPay, game, false) == lifeToPay);
            }
            return this.paid;
        }
        return false;
    }

    @Override
    public LurkingEvilCost copy() {
        return new LurkingEvilCost(this);
    }
}

class LurkingEvilToken extends Token {

    LurkingEvilToken() {
        super("Horror", "4/4 Horror creature with flying");
        power = new MageInt(4);
        toughness = new MageInt(4);
        subtype.add("Horror");
        cardType.add(CardType.CREATURE);
        this.addAbility(FlyingAbility.getInstance());
    }
}
