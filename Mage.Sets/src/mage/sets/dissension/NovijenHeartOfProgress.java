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
package mage.sets.dissension;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author someoneseth@gmail.com
 */
public class NovijenHeartOfProgress extends CardImpl {

    public NovijenHeartOfProgress(UUID ownerId) {
        super(ownerId, 175, "Novijen, Heart of Progress", Rarity.UNCOMMON, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "DIS";

        // {T}: Add {1} to your mana pool.        
        this.addAbility(new ColorlessManaAbility());
        
	// {G}{U}, {T}: Put a +1/+1 counter on each creature that entered the battlefield this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new NovijenHeartOfProgressEffect(), new ManaCostsImpl<>("{G}{U}"));        
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }   

    public NovijenHeartOfProgress(final NovijenHeartOfProgress card) {
        super(card);
    }

    @Override
    public NovijenHeartOfProgress copy() {
        return new NovijenHeartOfProgress(this);
    }
}

class NovijenHeartOfProgressEffect extends OneShotEffect {

    public NovijenHeartOfProgressEffect() {
        super(Outcome.BoostCreature);
        staticText = "put a +1/+1 counter on each creature that came into play this turn";
    }

    public NovijenHeartOfProgressEffect(final NovijenHeartOfProgressEffect effect) {
        super(effect);
    }

    @Override
    public NovijenHeartOfProgressEffect copy() {
        return new NovijenHeartOfProgressEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            for (Permanent permanent: game.getBattlefield().getActivePermanents(new FilterCreaturePermanent(), source.getControllerId(), game)) {
                if (permanent.getTurnsOnBattlefield() == 0) {
                    permanent.addCounters(CounterType.P1P1.createInstance(), game);
                    game.informPlayers(sourceObject.getLogName()+ ": " + controller.getLogName() + " puts a +1/+1 counter on " + permanent.getLogName());
                }
            }
            return true;
        }
        return false;
    }
}