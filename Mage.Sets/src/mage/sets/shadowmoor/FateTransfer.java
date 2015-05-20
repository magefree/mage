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
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.counters.Counter;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public class FateTransfer extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("target creature to move all counters from");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("target creature to move all counters to");

    public FateTransfer(UUID ownerId) {
        super(ownerId, 161, "Fate Transfer", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{U/B}");
        this.expansionSetCode = "SHM";


        // Move all counters from target creature onto another target creature.
        this.getSpellAbility().addEffect(new FateTransferEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter2));

    }

    public FateTransfer(final FateTransfer card) {
        super(card);
    }

    @Override
    public FateTransfer copy() {
        return new FateTransfer(this);
    }
}

class FateTransferEffect extends OneShotEffect {

    public FateTransferEffect() {
        super(Outcome.AIDontUseIt);
        staticText = "Move all counters from target creature onto another target creature";
    }

    public FateTransferEffect(final FateTransferEffect effect) {
        super(effect);
    }

    @Override
    public FateTransferEffect copy() {
        return new FateTransferEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creatureToMoveCountersFrom = game.getPermanent(source.getTargets().get(0).getFirstTarget());
        Permanent creatureToMoveCountersTo = game.getPermanent(source.getTargets().get(1).getFirstTarget());

        if (creatureToMoveCountersFrom != null
                && creatureToMoveCountersTo != null) {
            Permanent copyCreature = creatureToMoveCountersFrom.copy();
            for (Counter counter : copyCreature.getCounters().values()) {
                Counter newCounterTest = new Counter(counter.getName(), counter.getCount());
                creatureToMoveCountersFrom.removeCounters(newCounterTest, game);
                creatureToMoveCountersTo.addCounters(newCounterTest, game);
            }
            return true;
        }
        return false;
    }
}
