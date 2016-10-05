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
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.ParleyCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public class SelvalasEnforcer extends CardImpl {

    public SelvalasEnforcer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add("Elf");
        this.subtype.add("Warrior");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Parley - When Selvala's Enforcer enters the battlefield, each player reveals the top card of his or her library. 
        // For each nonland card revealed this way, put a +1/+1 counter on Selvala's Enforcer. Then each player draws a card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new SelvalasEnforcerEffect(), false, "<i>Parley &mdash; </i>");
        Effect effect = new DrawCardAllEffect(1);
        effect.setText("Then each player draws a card");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    public SelvalasEnforcer(final SelvalasEnforcer card) {
        super(card);
    }

    @Override
    public SelvalasEnforcer copy() {
        return new SelvalasEnforcer(this);
    }
}

class SelvalasEnforcerEffect extends OneShotEffect {

    public SelvalasEnforcerEffect() {
        super(Outcome.Benefit);
        this.staticText = "each player reveals the top card of his or her library. For each nonland card revealed this way, put a +1/+1 counter on {this}";
    }

    public SelvalasEnforcerEffect(final SelvalasEnforcerEffect effect) {
        super(effect);
    }

    @Override
    public SelvalasEnforcerEffect copy() {
        return new SelvalasEnforcerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int parley = ParleyCount.getInstance().calculate(game, source, this);
            if (parley > 0) {
                Permanent sourcePermanent = game.getPermanent(source.getSourceId());
                if (sourcePermanent != null) {
                    sourcePermanent.addCounters(CounterType.P1P1.createInstance(parley), game);
                }
            }
            return true;
        }
        return false;
    }
}
