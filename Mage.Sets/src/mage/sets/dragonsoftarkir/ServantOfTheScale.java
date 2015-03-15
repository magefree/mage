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
package mage.sets.dragonsoftarkir;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class ServantOfTheScale extends CardImpl {

    public ServantOfTheScale(UUID ownerId) {
        super(ownerId, 203, "Servant of the Scale", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{G}");
        this.expansionSetCode = "DTK";
        this.subtype.add("Human");
        this.subtype.add("Soldier");
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Servant of the Scale enters the battlefield with a +1/+1 counter on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)),
                "with a +1/+1 counters on it"));

        // When Servant of the Scale dies, put X +1/+1 counters on target creature you control, where X is the number of +1/+1 counter on Servant of the Scale.
        Ability ability = new DiesTriggeredAbility(new ServantOfTheScaleEffect(), false);
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    public ServantOfTheScale(final ServantOfTheScale card) {
        super(card);
    }

    @Override
    public ServantOfTheScale copy() {
        return new ServantOfTheScale(this);
    }
}

class ServantOfTheScaleEffect extends OneShotEffect {

    public ServantOfTheScaleEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "put X +1/+1 counters on target creature you control, where X is the number of +1/+1 counter on {this}";
    }

    public ServantOfTheScaleEffect(final ServantOfTheScaleEffect effect) {
        super(effect);
    }

    @Override
    public ServantOfTheScaleEffect copy() {
        return new ServantOfTheScaleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject != null && controller != null && sourceObject instanceof Permanent) {
            int amount = ((Permanent)sourceObject).getCounters().getCount(CounterType.P1P1);
            if (amount > 0) {
                Effect effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance(amount));
                effect.setTargetPointer(targetPointer);
                effect.apply(game, source);
            }
            return true;
        }
        return false;
    }
}
