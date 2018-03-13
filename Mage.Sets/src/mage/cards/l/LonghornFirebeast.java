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
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author noxx & L_J

 */
public class LonghornFirebeast extends CardImpl {

    public LonghornFirebeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.OX);
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Longhorn Firebeast enters the battlefield, any opponent may have it deal 5 damage to him or her. If a player does, sacrifice Longhorn Firebeast.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LonghornFirebeastEffect(), false));
    }

    public LonghornFirebeast(final LonghornFirebeast card) {
        super(card);
    }

    @Override
    public LonghornFirebeast copy() {
        return new LonghornFirebeast(this);
    }
}

class LonghornFirebeastEffect extends OneShotEffect {

    public LonghornFirebeastEffect() {
        super(Outcome.Neutral);
        staticText = "any opponent may have it deal 5 damage to him or her. If a player does, sacrifice {this}";
    }

    LonghornFirebeastEffect(final LonghornFirebeastEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (controller != null && permanent != null) {
            for (UUID opponentUuid : game.getOpponents(source.getControllerId())) {
                Player opponent = game.getPlayer(opponentUuid);
                if (opponent != null && opponent.chooseUse(Outcome.LoseLife, "Make " + permanent.getLogName() + " deal 5 damage to you?", source, game)) {
                    game.informPlayers(opponent.getLogName() + " has chosen to receive 5 damage from " + permanent.getLogName());
                    opponent.damage(5, permanent.getId(), game, false, true);
                    permanent.sacrifice(source.getSourceId(), game);
                    return true;
                }
            }
            game.informPlayers("5 damage wasn't dealt so " + permanent.getLogName() + " won't be sacrificed.");
            return true;
        }
        return false;
    }

    @Override
    public LonghornFirebeastEffect copy() {
        return new LonghornFirebeastEffect(this);
    }

}
