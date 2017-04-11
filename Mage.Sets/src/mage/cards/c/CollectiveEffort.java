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
package mage.cards.c;

import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.Mode;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.EscalateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.FilterPlayer;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetEnchantmentPermanent;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public class CollectiveEffort extends CardImpl {

    private static final FilterControlledCreaturePermanent filterUntapped = new FilterControlledCreaturePermanent("untapped creature you control");
    private static final FilterCreaturePermanent filterDestroyCreature = new FilterCreaturePermanent("creature with power 4 or greater");
    private static final FilterEnchantmentPermanent filterDestroyEnchantment = new FilterEnchantmentPermanent("enchantment to destroy");
    private static final FilterPlayer filterPlayer = new FilterPlayer("player whose creatures get +1/+1 counters");

    static {
        filterUntapped.add(Predicates.not(new TappedPredicate()));
        filterDestroyCreature.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
    }

    public CollectiveEffort(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{W}{W}");

        // Escalate &mdash; Tap an untapped creature you control.
        Cost cost = new TapTargetCost(new TargetControlledCreaturePermanent(filterUntapped));
        cost.setText("&mdash; Tap an untapped creature you control");
        this.addAbility(new EscalateAbility(cost));

        // Choose one or more &mdash;
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(3);

        // Destroy target creature with power 4 or greater.;
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filterDestroyCreature));

        // Destroy target enchantment.;
        Mode mode = new Mode();
        Effect effect = new DestroyTargetEffect();
        effect.setText("Destroy target enchantment");
        mode.getEffects().add(effect);
        mode.getTargets().add(new TargetEnchantmentPermanent(filterDestroyEnchantment));
        this.getSpellAbility().addMode(mode);

        // Put a +1/+1 counter on each creature target player controls.
        mode = new Mode();
        effect = new CollectiveEffortEffect();
        effect.setText("Put a +1/+1 counter on each creature target player controls");
        mode.getEffects().add(effect);
        mode.getTargets().add(new TargetPlayer(1, 1, false, filterPlayer));
        this.getSpellAbility().addMode(mode);
    }

    public CollectiveEffort(final CollectiveEffort card) {
        super(card);
    }

    @Override
    public CollectiveEffort copy() {
        return new CollectiveEffort(this);
    }
}

class CollectiveEffortEffect extends OneShotEffect {
    CollectiveEffortEffect() {
        super(Outcome.UnboostCreature);
        staticText = "Put a +1/+1 counter on each creature target player controls";
    }

    CollectiveEffortEffect(final CollectiveEffortEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player target = game.getPlayer(source.getFirstTarget());
        if (target != null) {
            for (Permanent p : game.getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), target.getId(), game)) {
                p.addCounters(CounterType.P1P1.createInstance(), source, game);
            }
            return true;
        }
        return false;
    }

    @Override
    public CollectiveEffortEffect copy() {
        return new CollectiveEffortEffect(this);
    }

}
