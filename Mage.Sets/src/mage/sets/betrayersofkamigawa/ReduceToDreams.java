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
package mage.sets.betrayersofkamigawa;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class ReduceToDreams extends CardImpl<ReduceToDreams> {

    public ReduceToDreams(UUID ownerId) {
        super(ownerId, 49, "Reduce to Dreams", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{3}{U}{U}");
        this.expansionSetCode = "BOK";

        this.color.setBlue(true);

        // Return all artifacts and enchantments to their owners' hands.
        this.getSpellAbility().addEffect(new ReduceToDreamsEffect());
    }

    public ReduceToDreams(final ReduceToDreams card) {
        super(card);
    }

    @Override
    public ReduceToDreams copy() {
        return new ReduceToDreams(this);
    }
}

class ReduceToDreamsEffect extends OneShotEffect<ReduceToDreamsEffect> {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("artifacts and enchantments");
    static {
        filter.add(Predicates.or(
                    new CardTypePredicate(CardType.ARTIFACT),
                    new CardTypePredicate(CardType.ENCHANTMENT)
                ));
    }
    
    public ReduceToDreamsEffect() {
        super(Constants.Outcome.ReturnToHand);
        staticText = "Return all artifacts and enchantments to their owners' hands";
    }

    public ReduceToDreamsEffect(final ReduceToDreamsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent creature : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
            creature.moveToZone(Constants.Zone.HAND, source.getSourceId(), game, true);
        }
        return true;
    }

    @Override
    public ReduceToDreamsEffect copy() {
        return new ReduceToDreamsEffect(this);
    }
}
