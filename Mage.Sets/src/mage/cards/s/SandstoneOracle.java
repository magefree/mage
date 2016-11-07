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
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author emerald000
 */
public class SandstoneOracle extends CardImpl {

    public SandstoneOracle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{7}");
        this.subtype.add("Sphinx");
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Sandstone Oracle enters the battlefield, choose an opponent. If that player has more cards in hand that you, draw cards equal to the difference.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SandstoneOracleEffect()));
    }

    public SandstoneOracle(final SandstoneOracle card) {
        super(card);
    }

    @Override
    public SandstoneOracle copy() {
        return new SandstoneOracle(this);
    }
}

class SandstoneOracleEffect extends OneShotEffect {

    SandstoneOracleEffect() {
        super(Outcome.DrawCard);
        this.staticText = "choose an opponent. If that player has more cards in hand than you, draw cards equal to the difference";
    }

    SandstoneOracleEffect(final SandstoneOracleEffect effect) {
        super(effect);
    }

    @Override
    public SandstoneOracleEffect copy() {
        return new SandstoneOracleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            TargetOpponent target = new TargetOpponent(true);
            if (controller.choose(Outcome.DrawCard, target, source.getSourceId(), game)) {
                Player opponent = game.getPlayer(target.getFirstTarget());
                if (opponent != null) {
                    game.informPlayers(sourceObject.getLogName() + ": " + controller.getLogName() + " has chosen " + opponent.getLogName());
                    int cardsDiff = opponent.getHand().size() - controller.getHand().size();
                    if (cardsDiff > 0) {
                        controller.drawCards(cardsDiff, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
