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
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Plopman
 */
public class PerniciousDeed extends CardImpl<PerniciousDeed> {

  
    
    public PerniciousDeed(UUID ownerId) {
        super(ownerId, 114, "Pernicious Deed", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}{G}");
        this.expansionSetCode = "APC";

        this.color.setGreen(true);
        this.color.setBlack(true);

        // {X}, Sacrifice Pernicious Deed: Destroy each artifact, creature, and enchantment with converted mana cost X or less.
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new PerniciousDeedEffect(), new VariableManaCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    public PerniciousDeed(final PerniciousDeed card) {
        super(card);
    }

    @Override
    public PerniciousDeed copy() {
        return new PerniciousDeed(this);
    }
}


class PerniciousDeedEffect extends OneShotEffect {


    public PerniciousDeedEffect() {
        super(Constants.Outcome.DestroyPermanent);
        staticText = "Destroy each artifact, creature, and enchantment with converted mana cost X or less";
    }


    public PerniciousDeedEffect(final PerniciousDeedEffect effect) {
        super(effect);
    }

    @Override
    public PerniciousDeedEffect copy() {
        return new PerniciousDeedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
 
        
        FilterPermanent filter = new FilterPermanent("artifacts, creatures, and enchantments");
    
    
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.ENCHANTMENT)));
        filter.add(new ConvertedManaCostPredicate(Filter.ComparisonType.LessThan, source.getManaCostsToPay().getX() + 1));

    
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
            permanent.destroy(source.getId(), game, false);
        }
        return true;
    }

}