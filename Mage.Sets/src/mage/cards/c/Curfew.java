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
package mage.cards.c;

import java.util.List;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author maxlebedev
 */
public class Curfew extends CardImpl {

    public Curfew(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");

        // Each player returns a creature he or she controls to its owner's hand.
        this.getSpellAbility().addEffect(new CurfewEffect());
    }

    public Curfew(final Curfew card) {
        super(card);
    }

    @Override
    public Curfew copy() {
        return new Curfew(this);
    }
}

class CurfewEffect extends OneShotEffect{

    public CurfewEffect() {
        super(Outcome.ReturnToHand);
        staticText = "Each player returns a creature he or she controls to its owner's hand";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.informPlayers("Each player returns a creature he or she controls to its owner's hand");
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                TargetControlledCreaturePermanent target = new TargetControlledCreaturePermanent();
                List<Permanent> liste = game.getBattlefield().getActivePermanents(new FilterControlledCreaturePermanent(), playerId, game);
                if(!liste.isEmpty()){
                    player.choose(Outcome.ReturnToHand, target, source.getSourceId(), game);

                    Permanent permanent = game.getPermanent(target.getFirstTarget());
                    if (permanent != null) {
                        permanent.moveToZone(Zone.HAND, source.getSourceId(), game, false);
                    }
                }
            }
        }
        return true;
    }


    @Override
    public Effect copy() {
        return new CurfewEffect();
    }
}

