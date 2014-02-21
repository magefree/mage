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
package mage.sets.alliances;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.counters.BoostCounter;
import mage.filter.common.FilterOwnedCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardIdPredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCreaturePermanentAmount;

/**
 *
 * @author Plopman
 */
public class Contagion extends CardImpl<Contagion> {
   
    public Contagion(UUID ownerId) {
        super(ownerId, 4, "Contagion", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{3}{B}{B}");
        this.expansionSetCode = "ALL";

        this.color.setBlack(true);

        FilterOwnedCard filter = new FilterOwnedCard("black card from your hand");
        filter.add(new ColorPredicate(ObjectColor.BLACK));
        filter.add(Predicates.not(new CardIdPredicate(this.getId()))); // the exile cost can never be paid with the card itself
        
        // You may pay 1 life and exile a black card from your hand rather than pay Contagion's mana cost.
        AlternativeCostSourceAbility ability = new AlternativeCostSourceAbility(new PayLifeCost(1));
        ability.addCost(new ExileFromHandCost(new TargetCardInHand(filter)));
        this.addAbility(ability);  
        
        // Distribute two -2/-1 counters among one or two target creatures.
        this.getSpellAbility().addTarget(new TargetCreaturePermanentAmount(2));
        this.getSpellAbility().addEffect(new DistributeCountersEffect());
    }

    public Contagion(final Contagion card) {
        super(card);
    }

    @Override
    public Contagion copy() {
        return new Contagion(this);
    }
}

class ContagionCounter extends BoostCounter<ContagionCounter> {

    public ContagionCounter(int count) {
        super(-2, -1);
        this.count = count;
    }

    public ContagionCounter(final ContagionCounter counter) {
        super(counter);
    }

    @Override
    public ContagionCounter copy() {
        return new ContagionCounter(this);
    }
}

class DistributeCountersEffect extends OneShotEffect<DistributeCountersEffect> {


    public DistributeCountersEffect() {
        super(Outcome.UnboostCreature);
    }

    public DistributeCountersEffect(final DistributeCountersEffect effect) {
        super(effect);
    }

    @Override
    public DistributeCountersEffect copy() {
        return new DistributeCountersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (source.getTargets().size() > 0) {
            Target multiTarget = source.getTargets().get(0);
            for (UUID target: multiTarget.getTargets()) {
                Permanent permanent = game.getPermanent(target);
                if (permanent != null) {
                    permanent.addCounters(new ContagionCounter(multiTarget.getTargetAmount(target)), game);
                }
            }
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        return "Distribute two -2/-1 counters among one or two target creatures";
    }
}
