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
package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SoldierToken;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public class EntrapmentManeuver extends CardImpl {

    public EntrapmentManeuver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{W}");

        // Target player sacrifices an attacking creature. You create X 1/1 white Soldier creature tokens, where X is that creature's toughness.
        this.getSpellAbility().addEffect(new EntrapmentManeuverSacrificeEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public EntrapmentManeuver(final EntrapmentManeuver card) {
        super(card);
    }

    @Override
    public EntrapmentManeuver copy() {
        return new EntrapmentManeuver(this);
    }
}

class EntrapmentManeuverSacrificeEffect extends OneShotEffect {

    public EntrapmentManeuverSacrificeEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "Target player sacrifices an attacking creature. You create X 1/1 white Soldier creature tokens, where X is that creature's toughness";
    }

    public EntrapmentManeuverSacrificeEffect(final EntrapmentManeuverSacrificeEffect effect) {
        super(effect);
    }

    @Override
    public EntrapmentManeuverSacrificeEffect copy() {
        return new EntrapmentManeuverSacrificeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player == null) {
            return false;
        }
        FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();
        filter.add(new AttackingPredicate());
        int realCount = game.getBattlefield().countAll(filter, player.getId(), game);
        if (realCount > 0) {
            Target target = new TargetControlledPermanent(1, 1, filter, true);
            while (player.canRespond() && !target.isChosen() && target.canChoose(player.getId(), game)) {
                player.chooseTarget(Outcome.Sacrifice, target, source, game);
            }
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent != null) {
                int amount = permanent.getToughness().getValue();
                permanent.sacrifice(source.getSourceId(), game);
                new CreateTokenEffect(new SoldierToken(), amount).apply(game, source);
            } else{
                return false;
            }
        }
        return true;
    }
}
