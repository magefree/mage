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
package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.CounterPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author Styxo
 */
public class FulfillContract extends CardImpl {

    private static final FilterCreaturePermanent filterBountyCreature = new FilterCreaturePermanent("creature with a bounty counter on it");
    private static final FilterControlledCreaturePermanent filterRogueOrHunter = new FilterControlledCreaturePermanent("Rogue or Hunter you control");

    static {
        filterBountyCreature.add(new CounterPredicate(CounterType.BOUNTY));
        filterRogueOrHunter.add(Predicates.or(new SubtypePredicate("Rogue"), new SubtypePredicate("Hunter")));
    }

    public FulfillContract(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B/R}{B/R}");

        // Destroy target creature with a bounty counter on it. If that creature is destroyed this way, you may put a +1/+1 counter on target Rogue or Hunter you control.
        this.getSpellAbility().addEffect(new FulfillContractEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filterBountyCreature));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent(filterRogueOrHunter));

    }

    public FulfillContract(final FulfillContract card) {
        super(card);
    }

    @Override
    public FulfillContract copy() {
        return new FulfillContract(this);
    }
}

class FulfillContractEffect extends OneShotEffect {

    public FulfillContractEffect() {
        super(Outcome.Benefit);
        this.staticText = "Destroy target creature with a bounty counter on it. If that creature is destroyed this way, you may put a +1/+1 counter on target Rogue or Hunter you control";
    }

    public FulfillContractEffect(final FulfillContractEffect effect) {
        super(effect);
    }

    @Override
    public FulfillContractEffect copy() {
        return new FulfillContractEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanentToDestroy = game.getPermanent(getTargetPointer().getFirst(game, source));
        Permanent permanentToPutCounter = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (controller != null) {
            if (permanentToDestroy != null && permanentToDestroy.destroy(source.getSourceId(), game, false)) {
                if (permanentToPutCounter != null) {
                    permanentToPutCounter.addCounters(CounterType.P1P1.createInstance(), source, game);
                }
            }
            return true;
        }
        return false;
    }
}
