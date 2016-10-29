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
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ZombieToken;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public class NoosegrafMob extends CardImpl {

    public NoosegrafMob(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}{B}");
        this.subtype.add("Zombie");
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Noosegraf Mob enters the battlefield with five +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(5)), "with five +1/+1 counters on it"));

        // Whenever a player casts a spell, remove a +1/+1 counter from Noosegraf Mob. If you do, create a 2/2 black Zombie creature token.
        this.addAbility(new SpellCastAllTriggeredAbility(new NoosegrafMobEffect(), false));
    }

    public NoosegrafMob(final NoosegrafMob card) {
        super(card);
    }

    @Override
    public NoosegrafMob copy() {
        return new NoosegrafMob(this);
    }
}

class NoosegrafMobEffect extends OneShotEffect {

    public NoosegrafMobEffect() {
        super(Outcome.Benefit);
        staticText = "remove a +1/+1 counter from Noosegraf Mob. If you do, create a 2/2 black Zombie creature token";
    }

    public NoosegrafMobEffect(final NoosegrafMobEffect effect) {
        super(effect);
    }

    @Override
    public NoosegrafMobEffect copy() {
        return new NoosegrafMobEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && permanent != null && permanent.getCounters(game).getCount(CounterType.P1P1) > 0) {
            permanent.removeCounters(CounterType.P1P1.createInstance(), game);
            Effect effect = new CreateTokenEffect(new ZombieToken());
            return effect.apply(game, source);
            }
        return false;
    }
}
