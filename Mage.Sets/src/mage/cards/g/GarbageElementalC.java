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
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.BattleCryAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.CounterPredicate;
import mage.game.Game;
import mage.game.permanent.token.GoblinToken;
import mage.game.permanent.token.TokenImpl;
import mage.game.permanent.token.Token;
import mage.players.Player;

/**
 *
 * @author spjspj
 */
public class GarbageElementalC extends CardImpl {

    public GarbageElementalC(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Battle cry
        this.addAbility(new BattleCryAbility());

        // When Garbage Elemental enters the battlefield, roll two six-sided dice. Create a number of 1/1 red Goblin creature tokens equal to the difference between those results.
        this.addAbility(new EntersBattlefieldAbility(new GarbageElementalEffect(),
                null,
                "When {this} enters the battlefield, roll two six-sided dice. Create a number of 1/1 red Goblin creature tokens equal to the difference between those results",
                null));

    }

    public GarbageElementalC(final GarbageElementalC card) {
        super(card);
    }

    @Override
    public GarbageElementalC copy() {
        return new GarbageElementalC(this);
    }
}

class GarbageElementalEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent("permanent with a counter");

    static {
        filter.add(new CounterPredicate(null));
    }

    GarbageElementalEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "roll two six-sided dice. Create a number of 1/1 red Goblin creature tokens equal to the difference between those results";
    }

    GarbageElementalEffect(final GarbageElementalEffect effect) {
        super(effect);
    }

    @Override
    public GarbageElementalEffect copy() {
        return new GarbageElementalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int thisRoll = controller.rollDice(game, 6);
            int thatRoll = controller.rollDice(game, 6);

            Token token = new GoblinToken();
            return token.putOntoBattlefield(Math.abs(thatRoll - thisRoll), game, source.getSourceId(), source.getControllerId());
        }
        return false;
    }
}
