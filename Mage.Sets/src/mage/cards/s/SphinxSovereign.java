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
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author North
 */
public class SphinxSovereign extends CardImpl {

    public SphinxSovereign(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}{W}{U}{U}{B}");
        this.subtype.add("Sphinx");

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // At the beginning of your end step, you gain 3 life if Sphinx Sovereign is untapped. Otherwise, each opponent loses 3 life.
        this.addAbility(new BeginningOfYourEndStepTriggeredAbility(new SphinxSovereignEffect(), false));
    }

    public SphinxSovereign(final SphinxSovereign card) {
        super(card);
    }

    @Override
    public SphinxSovereign copy() {
        return new SphinxSovereign(this);
    }
}

class SphinxSovereignEffect extends OneShotEffect {

    public SphinxSovereignEffect() {
        super(Outcome.Benefit);
        this.staticText = "you gain 3 life if {this} is untapped. Otherwise, each opponent loses 3 life";
    }

    public SphinxSovereignEffect(final SphinxSovereignEffect effect) {
        super(effect);
    }

    @Override
    public SphinxSovereignEffect copy() {
        return new SphinxSovereignEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = (Permanent) source.getSourceObject(game);
        if (controller != null && permanent != null) {
            if (!permanent.isTapped()) {
                controller.gainLife(3, game);
            } else {
                for (UUID opponentId : game.getState().getPlayersInRange(controller.getId(), game)) {
                    if (controller.hasOpponent(opponentId, game)) {
                    Player opponent = game.getPlayer(opponentId);
                        if (opponent != null) {
                            opponent.loseLife(3, game, false);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
