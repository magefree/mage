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
package mage.sets.avacynrestored;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MiracleAbility;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanentAmount;

/**
 *
 * @author North
 */
public class BlessingsOfNature extends CardImpl<BlessingsOfNature> {

    public BlessingsOfNature(UUID ownerId) {
        super(ownerId, 168, "Blessings of Nature", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{4}{G}");
        this.expansionSetCode = "AVR";

        this.color.setGreen(true);

        // Distribute four +1/+1 counters among any number of target creatures.
        this.getSpellAbility().addEffect(new BlessingsOfNatureEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanentAmount(4));

        this.addAbility(new MiracleAbility(new ManaCostsImpl("{G}")));
    }

    public BlessingsOfNature(final BlessingsOfNature card) {
        super(card);
    }

    @Override
    public BlessingsOfNature copy() {
        return new BlessingsOfNature(this);
    }
}

class BlessingsOfNatureEffect extends OneShotEffect<BlessingsOfNatureEffect> {

    public BlessingsOfNatureEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "Distribute four +1/+1 counters among any number of target creatures";
    }

    public BlessingsOfNatureEffect(final BlessingsOfNatureEffect effect) {
        super(effect);
    }

    @Override
    public BlessingsOfNatureEffect copy() {
        return new BlessingsOfNatureEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (source.getTargets().size() > 0) {
            Target multiTarget = source.getTargets().get(0);
            for (UUID target : multiTarget.getTargets()) {
                Permanent permanent = game.getPermanent(target);
                if (permanent != null) {
                    permanent.addCounters(CounterType.P1P1.createInstance(multiTarget.getTargetAmount(target)), game);
                }
            }
        }
        return true;
    }
}
