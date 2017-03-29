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
package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 */
public class GriefTyrant extends CardImpl {
    
    public GriefTyrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{B/R}");
        this.subtype.add("Horror");
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Grief Tyrant enters the battlefield with four -1/-1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.M1M1.createInstance(4))));

        // When Grief Tyrant dies, put a -1/-1 counter on target creature for each -1/-1 counter on Grief Tyrant.
        Ability ability = new DiesTriggeredAbility(new GriefTyrantEffect());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        
    }
    
    public GriefTyrant(final GriefTyrant card) {
        super(card);
    }
    
    @Override
    public GriefTyrant copy() {
        return new GriefTyrant(this);
    }
}

class GriefTyrantEffect extends OneShotEffect {
    
    public GriefTyrantEffect() {
        super(Outcome.Neutral);
        this.staticText = "put a -1/-1 counter on target creature for each -1/-1 counter on {this}";
    }
    
    public GriefTyrantEffect(final GriefTyrantEffect effect) {
        super(effect);
    }
    
    @Override
    public GriefTyrantEffect copy() {
        return new GriefTyrantEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = game.getPermanent(targetPointer.getFirst(game, source));
        Permanent griefTyrant = game.getPermanentOrLKIBattlefield(source.getSourceId());
        int countersOnGriefTyrant = griefTyrant.getCounters(game).getCount(CounterType.M1M1);
        if (targetCreature != null) {
            targetCreature.addCounters(CounterType.M1M1.createInstance(countersOnGriefTyrant), source, game);
            return true;
        }
        return false;
    }
}
