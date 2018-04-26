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

import java.util.Objects;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToOpponentTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.SubType;
import mage.abilities.keyword.MeleeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public class GrenzosRuffians extends CardImpl {

    public GrenzosRuffians(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Melee
        this.addAbility(new MeleeAbility());

        // Whenever Grenzo's Ruffians deals combat damage to a opponent, it deals that much damage to each other opponent.
        this.addAbility(new DealsDamageToOpponentTriggeredAbility(new GrenzosRuffiansEffect(), false, true, true));
    }

    public GrenzosRuffians(final GrenzosRuffians card) {
        super(card);
    }

    @Override
    public GrenzosRuffians copy() {
        return new GrenzosRuffians(this);
    }
}

class GrenzosRuffiansEffect extends OneShotEffect {

    public GrenzosRuffiansEffect() {
        super(Outcome.Benefit);
        this.staticText = "it deals that much damage to each other opponent";
    }

    public GrenzosRuffiansEffect(final GrenzosRuffiansEffect effect) {
        super(effect);
    }

    @Override
    public GrenzosRuffiansEffect copy() {
        return new GrenzosRuffiansEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID damagedOpponent = this.getTargetPointer().getFirst(game, source);
        int amount = (Integer) getValue("damage");
        MageObject object = game.getObject(source.getSourceId());
        if (object != null && amount > 0 && damagedOpponent != null) {
            for (UUID playerId : game.getOpponents(source.getControllerId())) {
                if (!Objects.equals(playerId, damagedOpponent)) {
                    Player opponent = game.getPlayer(playerId);
                    if (opponent != null) {
                        int dealtDamage = opponent.damage(amount, source.getSourceId(), game, false, true);
                        game.informPlayers(object.getLogName() + " deals " + dealtDamage + " damage to " + opponent.getLogName());
                    }
                }
            }
            return true;
        }
        return false;
    }
}
