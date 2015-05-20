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
package mage.sets.khansoftarkir;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.counters.CounterType;
import mage.filter.Filter;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetCreaturePermanentAmount;

/**
 *
 * @author LevelX2
 */
public class AbzanCharm extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with power 3 or greater");

    static {
        filter.add(new PowerPredicate(Filter.ComparisonType.GreaterThan, 2));
    }

    public AbzanCharm(UUID ownerId) {
        super(ownerId, 161, "Abzan Charm", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{W}{B}{G}");
        this.expansionSetCode = "KTK";


        // Choose one -
        // *Exile target creature with power 3 or greater
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addEffect(new ExileTargetEffect());

        // *You draw two cards and you lose 2 life
        Mode mode = new Mode();
        mode.getEffects().add(new DrawCardSourceControllerEffect(2));
        mode.getEffects().add(new LoseLifeSourceControllerEffect(2));
        this.getSpellAbility().addMode(mode);

        // *Distribute two +1/+1 counters among one or two target creatures.
        mode = new Mode();
        mode.getEffects().add(new AbzanCharmDistributeEffect());
        mode.getTargets().add(new TargetCreaturePermanentAmount(2));
        this.getSpellAbility().addMode(mode);

    }

    public AbzanCharm(final AbzanCharm card) {
        super(card);
    }

    @Override
    public AbzanCharm copy() {
        return new AbzanCharm(this);
    }
}

class AbzanCharmDistributeEffect extends OneShotEffect {

    public AbzanCharmDistributeEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "Distribute two +1/+1 counters among one or two target creatures";
    }

    public AbzanCharmDistributeEffect(final AbzanCharmDistributeEffect effect) {
        super(effect);
    }

    @Override
    public AbzanCharmDistributeEffect copy() {
        return new AbzanCharmDistributeEffect(this);
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
