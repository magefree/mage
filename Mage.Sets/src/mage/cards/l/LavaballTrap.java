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
package mage.cards.l;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetLandPermanent;
import mage.watchers.common.PermanentsEnteredBattlefieldWatcher;

/**
 *
 * @author jeffwadsworth
 */
public class LavaballTrap extends CardImpl {

    public LavaballTrap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{6}{R}{R}");
        this.subtype.add("Trap");

        // If an opponent had two or more lands enter the battlefield under his or her control this turn, you may pay {3}{R}{R} rather than pay Lavaball Trap's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new ManaCostsImpl("{3}{R}{R}"), LavaballTrapCondition.getInstance()), new PermanentsEnteredBattlefieldWatcher());

        // Destroy two target lands. Lavaball Trap deals 4 damage to each creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new DamageAllEffect(4, new FilterCreaturePermanent()));
        this.getSpellAbility().addTarget(new TargetLandPermanent(2, 2, new FilterLandPermanent("lands"), false));

    }

    public LavaballTrap(final LavaballTrap card) {
        super(card);
    }

    @Override
    public LavaballTrap copy() {
        return new LavaballTrap(this);
    }
}

class LavaballTrapCondition implements Condition {

    private static final LavaballTrapCondition fInstance = new LavaballTrapCondition();

    public static Condition getInstance() {
        return fInstance;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        PermanentsEnteredBattlefieldWatcher watcher = (PermanentsEnteredBattlefieldWatcher) game.getState().getWatchers().get(PermanentsEnteredBattlefieldWatcher.class.getName());
        if (watcher != null) {
            for (UUID opponentId : game.getOpponents(source.getControllerId())) {
                List<Permanent> permanents = watcher.getThisTurnEnteringPermanents(opponentId);
                if (permanents != null) {
                    int count = 0;
                    for (Permanent permanent : permanents) {
                        if (permanent.isLand()) {
                            count++;
                            if (count == 2) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "If an opponent had two or more lands enter the battlefield under his or her control this turn";
    }
}
