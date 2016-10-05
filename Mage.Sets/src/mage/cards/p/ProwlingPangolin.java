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
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public class ProwlingPangolin extends CardImpl {

    public ProwlingPangolin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}");
        this.subtype.add("Beast");
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // When Prowling Pangolin enters the battlefield, any player may sacrifice two creatures. If a player does, sacrifice Prowling Pangolin.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ProwlingPangolinEffect(), false));
    }

    public ProwlingPangolin(final ProwlingPangolin card) {
        super(card);
    }

    @Override
    public ProwlingPangolin copy() {
        return new ProwlingPangolin(this);
    }
}

class ProwlingPangolinEffect extends OneShotEffect {

    ProwlingPangolinEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "any player may sacrifice two creatures. If a player does, sacrifice {this}";
    }

    ProwlingPangolinEffect(final ProwlingPangolinEffect effect) {
        super(effect);
    }

    @Override
    public ProwlingPangolinEffect copy() {
        return new ProwlingPangolinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            boolean costPaid = false;
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Cost cost = new SacrificeTargetCost(new TargetControlledPermanent(2, 2, new FilterControlledCreaturePermanent("two creatures"), true));
                Player player = game.getPlayer(playerId);
                if (player != null
                        && cost.canPay(source, source.getSourceId(), playerId, game)
                        && player.chooseUse(Outcome.Sacrifice, "Sacrifice two creatures?", source, game)
                        && cost.pay(source, game, source.getSourceId(), playerId, true, null)) {
                    costPaid = true;
                }
            }
            if (costPaid) {
                Permanent sourceObject = game.getPermanent(source.getSourceId());
                if (sourceObject != null) {
                    sourceObject.sacrifice(source.getSourceId(), game);
                }
            }
            return true;
        }
        return false;
    }
}
