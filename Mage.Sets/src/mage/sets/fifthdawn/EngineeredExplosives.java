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
package mage.sets.fifthdawn;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.SunburstAbility;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.common.FilterNonlandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Plopman
 */
public class EngineeredExplosives extends CardImpl<EngineeredExplosives> {


    public EngineeredExplosives(UUID ownerId) {
        super(ownerId, 118, "Engineered Explosives", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{X}");
        this.expansionSetCode = "5DN";

        // Sunburst
        this.addAbility(new SunburstAbility());
        // {2}, Sacrifice Engineered Explosives: Destroy each nonland permanent with converted mana cost equal to the number of charge counters on Engineered Explosives.
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new EngineeredExplosivesEffect(), new ManaCostsImpl("{2}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    public EngineeredExplosives(final EngineeredExplosives card) {
        super(card);
    }

    @Override
    public EngineeredExplosives copy() {
        return new EngineeredExplosives(this);
    }
}

class EngineeredExplosivesEffect extends OneShotEffect {

    private static final FilterNonlandPermanent filter = new FilterNonlandPermanent();
   

    public EngineeredExplosivesEffect() {
        super(Constants.Outcome.DestroyPermanent);
        staticText = "Destroy each nonland permanent with converted mana cost equal to the number of charge counters on Engineered Explosives";
    }


    public EngineeredExplosivesEffect(final EngineeredExplosivesEffect effect) {
        super(effect);
    }

    @Override
    public EngineeredExplosivesEffect copy() {
        return new EngineeredExplosivesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject engineeredExplosives = game.getLastKnownInformation(source.getSourceId(), Constants.Zone.BATTLEFIELD);
        if(engineeredExplosives != null && engineeredExplosives instanceof Permanent){
            int count = ((Permanent)engineeredExplosives).getCounters().getCount(CounterType.CHARGE);
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
                if(permanent.getManaCost().convertedManaCost() == count){
                    permanent.destroy(source.getId(), game, false);
                }
            }
            return true;
        }
        return false;
    }

}
