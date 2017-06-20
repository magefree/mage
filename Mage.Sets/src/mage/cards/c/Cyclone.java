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

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;



/**
 *
 * @author MarcoMarin
 */
public class Cyclone extends CardImpl {
    
    public Cyclone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{G}{G}");

        // At the beginning of your upkeep, put a wind counter on Cyclone, then sacrifice Cyclone unless you pay {G} for each wind counter on it. If you pay, Cyclone deals damage equal to the number of wind counters on it to each creature and each player.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.WIND.createInstance()), TargetController.YOU, false);
        ability.addEffect(new CycloneEffect());
        this.addAbility(ability);        
    }

    public Cyclone(final Cyclone card) {
        super(card);
    }

    @Override
    public Cyclone copy() {
        return new Cyclone(this);
    }
}
class CycloneEffect extends OneShotEffect {

    public CycloneEffect() {
        super(Outcome.Damage);
        this.staticText = "Pay Green Mana for each counter to damage everything or sacrifice Cyclone.";
    }

    public CycloneEffect(final CycloneEffect effect) {
        super(effect);
    }

    @Override
    public CycloneEffect copy() {
        return new CycloneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        int total = permanent.getCounters(game).getCount(CounterType.WIND);
        StringBuilder greens = new StringBuilder(total);
        for (int i=0; i < total; i++){
            greens.append("{G}");
        }
                  
        if(this.choice(game, source, player, new ManaCostsImpl(greens.toString()))){
            DamageEverythingEffect dmg = new DamageEverythingEffect(total);
            dmg.apply(game, source);
        } else {            
            permanent.sacrifice(source.getSourceId(), game);
        }
        return true;
    }
    
    private boolean choice(Game game, Ability source, Player player, Cost counters) {
        return counters.canPay(source, source.getSourceId(), player.getId(), game)
                    && player.chooseUse(Outcome.Damage, "Pay Cyclone's Upkeep?", source, game);
    }
        
}