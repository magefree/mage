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
package mage.abilities.keyword;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.token.ServoToken;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author emerald000
 */
public class FabricateAbility extends EntersBattlefieldTriggeredAbility {

    public FabricateAbility(int value) {
        super(new FabricateEffect(value), false, true);
    }

    public FabricateAbility(final FabricateAbility ability) {
        super(ability);
    }

    @Override
    public FabricateAbility copy() {
        return new FabricateAbility(this);
    }
}

class FabricateEffect extends OneShotEffect {

    private final int value;

    FabricateEffect(int value) {
        super(Outcome.Benefit);
        this.value = value;
        this.staticText = "Fabricate " + value
                + " <i>(When this creature enters the battlefield, put " + CardUtil.numberToText(value, "a") + " +1/+1 counter" + (value > 1 ? "s" : "")
                + " on it or create " + CardUtil.numberToText(value, "a") + " 1/1 colorless Servo artifact creature token" + (value > 1 ? "s" : "") + ".)</i>";
    }

    FabricateEffect(final FabricateEffect effect) {
        super(effect);
        this.value = effect.value;
    }

    @Override
    public FabricateEffect copy() {
        return new FabricateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            MageObject sourceObject = source.getSourceObjectIfItStillExists(game);
            if (sourceObject != null && controller.chooseUse(
                    Outcome.BoostCreature,
                    "Fabricate " + value,
                    null,
                    "Put " + CardUtil.numberToText(value, "a") + " +1/+1 counter" + (value > 1 ? "s" : ""),
                    "Create " + CardUtil.numberToText(value, "a") + " 1/1 token" + (value > 1 ? "s" : ""),
                    source,
                    game)) {
                ((Card) sourceObject).addCounters(CounterType.P1P1.createInstance(value), source, game);
            }
            else {
                new ServoToken().putOntoBattlefield(value, game, source.getSourceId(), controller.getId());
            }
            return true;
        }
        return false;
    }
}
