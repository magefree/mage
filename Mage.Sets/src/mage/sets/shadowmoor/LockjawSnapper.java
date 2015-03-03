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
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.WitherAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.CounterPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public class LockjawSnapper extends CardImpl {

    public LockjawSnapper(UUID ownerId) {
        super(ownerId, 255, "Lockjaw Snapper", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");
        this.expansionSetCode = "SHM";
        this.subtype.add("Scarecrow");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Wither
        this.addAbility(WitherAbility.getInstance());
        
        // When Lockjaw Snapper dies, put a -1/-1 counter on each creature with a -1/-1 counter on it.
        this.addAbility(new DiesTriggeredAbility(new LockjawSnapperEffect()));
        
    }

    public LockjawSnapper(final LockjawSnapper card) {
        super(card);
    }

    @Override
    public LockjawSnapper copy() {
        return new LockjawSnapper(this);
    }
}

class LockjawSnapperEffect extends OneShotEffect {

    public LockjawSnapperEffect() {
        super(Outcome.Neutral);
        this.staticText = "put a -1/-1 counter on each creature with a -1/-1 counter on it";
    }

    public LockjawSnapperEffect(final LockjawSnapperEffect effect) {
        super(effect);
    }

    @Override
    public LockjawSnapperEffect copy() {
        return new LockjawSnapperEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean applied = false;
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(new CounterPredicate(CounterType.M1M1));
        if (game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game).isEmpty()) {
            return true;
        }
        for (Permanent creature : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
            if (creature != null) {
                creature.addCounters(CounterType.M1M1.createInstance(), game);
                applied = true;
            }
        }
        return applied;
    }
}