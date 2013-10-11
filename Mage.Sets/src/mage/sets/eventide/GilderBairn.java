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
package mage.sets.eventide;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.UntapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.Counter;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public class GilderBairn extends CardImpl<GilderBairn> {

    public GilderBairn(UUID ownerId) {
        super(ownerId, 152, "Gilder Bairn", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{G/U}{G/U}");
        this.expansionSetCode = "EVE";
        this.subtype.add("Ouphe");

        this.color.setBlue(true);
        this.color.setGreen(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {2}{GU}, {untap}: For each counter on target permanent, put another of those counters on that permanent.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GilderBairnEffect(), new ManaCostsImpl("{2}{G/U}"));
        ability.addCost(new UntapSourceCost());
        ability.addTarget(new TargetPermanent(true));
        this.addAbility(ability);

    }

    public GilderBairn(final GilderBairn card) {
        super(card);
    }

    @Override
    public GilderBairn copy() {
        return new GilderBairn(this);
    }
}

class GilderBairnEffect extends OneShotEffect<GilderBairnEffect> {

    public GilderBairnEffect() {
        super(Outcome.Benefit);
        this.staticText = "For each counter on target permanent, put another of those counters on that permanent";
    }

    public GilderBairnEffect(final GilderBairnEffect effect) {
        super(effect);
    }

    @Override
    public GilderBairnEffect copy() {
        return new GilderBairnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(source.getFirstTarget());
        if (target == null) {
            return false;
        }
        for (Counter counter : target.getCounters().values()) {
            Counter newCounter = new Counter(counter.getName(), counter.getCount());
            target.addCounters(newCounter, game);
        }
        return false;
    }
}
