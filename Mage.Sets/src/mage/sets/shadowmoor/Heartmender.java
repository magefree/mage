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
package mage.sets.shadowmoor;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.PersistAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public class Heartmender extends CardImpl {

    public Heartmender(UUID ownerId) {
        super(ownerId, 228, "Heartmender", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{G/W}{G/W}");
        this.expansionSetCode = "SHM";
        this.subtype.add("Elemental");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of your upkeep, remove a -1/-1 counter from each creature you control.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new HeartmenderEffect(CounterType.M1M1.createInstance()), TargetController.YOU, false));

        // Persist
        this.addAbility(new PersistAbility());

    }

    public Heartmender(final Heartmender card) {
        super(card);
    }

    @Override
    public Heartmender copy() {
        return new Heartmender(this);
    }
}

class HeartmenderEffect extends OneShotEffect {

    private final Counter counter;

    public HeartmenderEffect(Counter counter) {
        super(Outcome.BoostCreature);
        this.counter = counter;
        staticText = "remove a -1/-1 counter from each creature you control";
    }

    public HeartmenderEffect(HeartmenderEffect effect) {
        super(effect);
        this.counter = effect.counter.copy();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean applied = false;
        FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();
        if (game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game).isEmpty()) {
            return true;
        }
        for (Permanent creature : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
            if (creature != null
                    && creature.getCounters().getCount(counter.getName()) >= counter.getCount()) {
                creature.removeCounters(counter.getName(), counter.getCount(), game);
                game.informPlayers(new StringBuilder("Removed ").append(counter.getCount()).append(" ").append(counter.getName())
                        .append(" counter from ").append(creature.getName()).toString());
                applied = true;
            }
        }
        return applied;
    }

    @Override
    public HeartmenderEffect copy() {
        return new HeartmenderEffect(this);
    }
}
