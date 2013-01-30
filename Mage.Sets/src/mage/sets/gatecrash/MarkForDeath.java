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
package mage.sets.gatecrash;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.BlocksIfAbleTargetEffect;
import mage.abilities.effects.common.CantBlockAllEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardIdPredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public class MarkForDeath extends CardImpl<MarkForDeath> {

    final private static FilterCreaturePermanent filter = new FilterCreaturePermanent("creature an opponent controls");

    static {
        filter.add(new ControllerPredicate(Constants.TargetController.OPPONENT));
    }

    public MarkForDeath(UUID ownerId) {
        super(ownerId, 99, "Mark for Death", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{3}{R}");
        this.expansionSetCode = "GTC";

        this.color.setRed(true);

        // Target creature an opponent controls blocks this turn if able. Untap that creature. Other creatures that player controls can't block this turn.
        this.getSpellAbility().addEffect(new MarkForDeathEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
    }

    public MarkForDeath(final MarkForDeath card) {
        super(card);
    }

    @Override
    public MarkForDeath copy() {
        return new MarkForDeath(this);
    }
}

class MarkForDeathEffect extends OneShotEffect<MarkForDeathEffect> {

    public MarkForDeathEffect() {
        super(Constants.Outcome.Damage);
        staticText = "Target creature an opponent controls blocks this turn if able. Untap that creature. Other creatures that player controls can't block this turn";
    }

    public MarkForDeathEffect(final MarkForDeathEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(source.getFirstTarget());
        if (target == null) {
            return false;
        }
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(new ControllerIdPredicate(target.getControllerId()));
        filter.add(Predicates.not(new CardIdPredicate(target.getId())));
        
        ContinuousEffect effect = new BlocksIfAbleTargetEffect(Constants.Duration.EndOfTurn);
        effect.setTargetPointer(new FixedTarget(target.getId()));
        game.addEffect(effect, source);
        
        target.untap(game);
        
        ContinuousEffect effect2 = new CantBlockAllEffect(filter, Constants.Duration.EndOfTurn);
        game.addEffect(effect2, source);
        return true;
    }

    @Override
    public MarkForDeathEffect copy() {
        return new MarkForDeathEffect(this);
    }
}