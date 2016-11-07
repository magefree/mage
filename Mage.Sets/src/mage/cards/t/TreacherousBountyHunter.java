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
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.PermanentHasCounterCondition;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Styxo
 */
public class TreacherousBountyHunter extends CardImpl {

    public TreacherousBountyHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add("Rodian");
        this.subtype.add("Hunter");
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Treacherous Bounty Hunter enters the battlefield, sacrifice a creature unless an opponet controls a creature with a bounty counter on it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TreacherousBountyHunterSacrificeUnlessConditionEffect()));

    }

    public TreacherousBountyHunter(final TreacherousBountyHunter card) {
        super(card);
    }

    @Override
    public TreacherousBountyHunter copy() {
        return new TreacherousBountyHunter(this);
    }
}

class TreacherousBountyHunterSacrificeUnlessConditionEffect extends OneShotEffect {

    public TreacherousBountyHunterSacrificeUnlessConditionEffect() {
        super(Outcome.Sacrifice);
        staticText = "sacrifice a creature unless an opponet controls a creature with a bounty counter on it";
    }

    public TreacherousBountyHunterSacrificeUnlessConditionEffect(final TreacherousBountyHunterSacrificeUnlessConditionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            if (new PermanentHasCounterCondition(CounterType.BOUNTY, 0, new FilterOpponentsCreaturePermanent(), PermanentHasCounterCondition.CountType.MORE_THAN).apply(game, source)) {
                return true;
            }
            Effect effect = new SacrificeControllerEffect(new FilterControlledCreaturePermanent(), 1, "");
            effect.apply(game, source);
            return true;
        }
        return false;
    }

    @Override
    public TreacherousBountyHunterSacrificeUnlessConditionEffect copy() {
        return new TreacherousBountyHunterSacrificeUnlessConditionEffect(this);
    }
}
