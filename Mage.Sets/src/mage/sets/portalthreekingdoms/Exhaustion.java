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
package mage.sets.portalthreekingdoms;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author emerald000
 */
public class Exhaustion extends CardImpl {

    public Exhaustion(UUID ownerId) {
        super(ownerId, 42, "Exhaustion", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{2}{U}");
        this.expansionSetCode = "PTK";


        // Creatures and lands target opponent controls don't untap during his or her next untap step.
        this.getSpellAbility().addEffect(new ExhaustionEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    public Exhaustion(final Exhaustion card) {
        super(card);
    }

    @Override
    public Exhaustion copy() {
        return new Exhaustion(this);
    }
}

class ExhaustionEffect extends OneShotEffect {
    
    private static final FilterPermanent filter = new FilterPermanent();
    
    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.CREATURE), new CardTypePredicate(CardType.LAND)));
    }
    
    ExhaustionEffect() {
        super(Outcome.Detriment);
        this.staticText = "Creatures and lands target opponent controls don't untap during his or her next untap step.";
    }
    
    ExhaustionEffect(final ExhaustionEffect effect) {
        super(effect);
    }
    
    @Override
    public ExhaustionEffect copy() {
        return new ExhaustionEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, player.getId(), game)) {
                ContinuousEffect effect = new DontUntapInControllersNextUntapStepTargetEffect();
                effect.setTargetPointer(new FixedTarget(permanent.getId()));
                game.addEffect(effect, source);                      
            }
            return true;
        }
        return false;
    }
}
