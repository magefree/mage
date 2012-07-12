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
package mage.sets.ravnika;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 * @author Loki
 */
public class LeaveNoTrace extends CardImpl<LeaveNoTrace> {
    static final FilterPermanent filter = new FilterPermanent("enchantment");

    static {
        filter.add(new CardTypePredicate(CardType.ENCHANTMENT));
    }

    public LeaveNoTrace(UUID ownerId) {
        super(ownerId, 23, "Leave No Trace", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{W}");
        this.expansionSetCode = "RAV";

        this.color.setWhite(true);

        // Radiance - Destroy target enchantment and each other enchantment that shares a color with it.
        this.getSpellAbility().addEffect(new LeaveNoTraceEffect());
        this.getSpellAbility().addTarget(new TargetPermanent());
    }

    public LeaveNoTrace(final LeaveNoTrace card) {
        super(card);
    }

    @Override
    public LeaveNoTrace copy() {
        return new LeaveNoTrace(this);
    }
}

class LeaveNoTraceEffect extends OneShotEffect<LeaveNoTraceEffect> {
    static final FilterPermanent filter = new FilterPermanent("enchantment");

    static {
        filter.add(new CardTypePredicate(CardType.ENCHANTMENT));
    }

    LeaveNoTraceEffect() {
        super(Constants.Outcome.DestroyPermanent);
        staticText = "Radiance - Destroy target enchantment and each other enchantment that shares a color with it";
    }

    LeaveNoTraceEffect(final LeaveNoTraceEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(targetPointer.getFirst(game, source));
        if (target != null) {
            ObjectColor color = target.getColor();
            target.destroy(source.getSourceId(), game, false);
            for (Permanent p : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
                if (p.getColor().shares(color)) {
                    p.destroy(source.getSourceId(), game, false);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public LeaveNoTraceEffect copy() {
        return new LeaveNoTraceEffect(this);
    }
}