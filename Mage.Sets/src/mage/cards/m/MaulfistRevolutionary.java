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
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrDiesSourceTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetPermanentOrPlayer;

/**
 *
 * @author fireshoes
 */
public class MaulfistRevolutionary extends CardImpl {

    public MaulfistRevolutionary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");

        this.subtype.add("Human");
        this.subtype.add("Warrior");
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Maulfist Revolutionary enters the battlefield or dies, for each kind of counter on target permanent or player,
        // give that permanent or player another counter of that kind.
        Ability ability = new EntersBattlefieldOrDiesSourceTriggeredAbility(new MaulfistRevolutionaryEffect(), false);
        ability.addTarget(new TargetPermanentOrPlayer());
        this.addAbility(ability);
    }

    public MaulfistRevolutionary(final MaulfistRevolutionary card) {
        super(card);
    }

    @Override
    public MaulfistRevolutionary copy() {
        return new MaulfistRevolutionary(this);
    }
}

class MaulfistRevolutionaryEffect extends OneShotEffect {

    public MaulfistRevolutionaryEffect() {
        super(Outcome.Neutral);
        this.staticText = "for each kind of counter on target permanent or player, give that permanent or player another counter of that kind";
    }

    public MaulfistRevolutionaryEffect(final MaulfistRevolutionaryEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
            if (player != null) {
                for (Counter counter : player.getCounters().values()) {
                    counter.increase();
                }
            }
            Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (permanent != null) {
                for (Counter counter : permanent.getCounters(game).values()) {
                    counter.increase();
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public Effect copy() {
        return new MaulfistRevolutionaryEffect(this);
    }

}
