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
package mage.sets.apocalypse;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardControllerEffect;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author anonymous
 */
public class TranquilPath extends CardImpl<TranquilPath> {

    public TranquilPath(UUID ownerId) {
        super(ownerId, 89, "Tranquil Path", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{4}{G}");
        this.expansionSetCode = "APC";
        this.color.setGreen(true);
        this.getSpellAbility().addEffect(new TranquilPathEffect());
        this.getSpellAbility().addEffect(new DrawCardControllerEffect(1));
    }

    public TranquilPath(final TranquilPath card) {
        super(card);
    }

    @Override
    public TranquilPath copy() {
        return new TranquilPath(this);
    }
}

class TranquilPathEffect extends OneShotEffect<TranquilPathEffect> {

    private final static FilterPermanent filter = new FilterPermanent("");

    static {
        filter.add(new CardTypePredicate(CardType.ENCHANTMENT));
    }

    public TranquilPathEffect() {
        super(Constants.Outcome.DestroyPermanent);
        staticText = "Destroy all enchantments";
    }

    public TranquilPathEffect(final TranquilPathEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, game)) {
            permanent.destroy(source.getId(), game, false);
        }
        return true;
    }

    @Override
    public TranquilPathEffect copy() {
        return new TranquilPathEffect(this);
    }

}