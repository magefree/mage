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
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTappedTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.permanent.CounterPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author TheElk801
 */
public class MineLayer extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("land with a mine counter on it");

    static {
        filter.add(new CounterPredicate(CounterType.MINE));
    }

    public MineLayer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.DWARF);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{R}, {tap}: Put a mine counter on target land.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.MINE.createInstance()), new TapSourceCost());
        ability.addCost(new ManaCostsImpl("{1}{R}"));
        ability.addTarget(new TargetLandPermanent());
        this.addAbility(ability);

        // Whenever a land with a mine counter on it becomes tapped, destroy it.
        this.addAbility(new BecomesTappedTriggeredAbility(new DestroyTargetEffect().setText("destroy that land"), false, filter, true));

        // When Mine Layer leaves the battlefield, remove all mine counters from all lands.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new RemoveAllMineCountersEffect(), false));
    }

    public MineLayer(final MineLayer card) {
        super(card);
    }

    @Override
    public MineLayer copy() {
        return new MineLayer(this);
    }
}

class RemoveAllMineCountersEffect extends OneShotEffect {

    public RemoveAllMineCountersEffect() {
        super(Outcome.Neutral);
        this.staticText = "remove all mine counters from all lands";
    }

    public RemoveAllMineCountersEffect(final RemoveAllMineCountersEffect effect) {
        super(effect);
    }

    @Override
    public RemoveAllMineCountersEffect copy() {
        return new RemoveAllMineCountersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(CardType.LAND)) {
            if (permanent != null) {
                permanent.getCounters(game).removeAllCounters(CounterType.MINE);
            }
        }
        return true;
    }
}
