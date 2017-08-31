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
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public class TreasureMap extends CardImpl {

    public TreasureMap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.transformable = true;
        this.secondSideCardClazz = TreasureCove.class;

        // {1}, {T}: Scry 1. Put a landmark counter on Treasure Map. Then if there are three or more landmark counters on it, remove those counters, transform Treasure Map, and create three colorless Treasure artifact tokens with "{T}, Sacrifice this artifact: Add one mana of any color to your mana pool."
        this.addAbility(new TransformAbility());
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TreasureMapEffect(), new ManaCostsImpl("{1}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public TreasureMap(final TreasureMap card) {
        super(card);
    }

    @Override
    public TreasureMap copy() {
        return new TreasureMap(this);
    }
}

class TreasureMapEffect extends OneShotEffect {

    TreasureMapEffect() {
        super(Outcome.Benefit);
        this.staticText = "Scry 1. Put a landmark counter on {this}. "
                + "Then if there are three or more landmark counters on it, "
                + "remove those counters, transform {this}, and create "
                + "three colorless Treasure artifact tokens with \"{T}, Sacrifice this artifact: Add one mana of any color to your mana pool.";
    }

    TreasureMapEffect(final TreasureMapEffect effect) {
        super(effect);
    }

    @Override
    public TreasureMapEffect copy() {
        return new TreasureMapEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.scry(1, source, game);
            if (permanent != null) {
                permanent.addCounters(CounterType.LANDMARK.createInstance(), source, game);
                int counters = permanent.getCounters(game).getCount(CounterType.LANDMARK);
                if (counters > 2) {
                    permanent.removeCounters("landmark", counters, game);
                    new TransformSourceEffect(true).apply(game, source);
                    new CreateTokenEffect(new TreasureToken("XLN"), 3).apply(game, source);
                }
                return true;
            }
        }
        return false;
    }
}
