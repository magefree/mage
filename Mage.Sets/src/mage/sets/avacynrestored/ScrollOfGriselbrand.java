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
package mage.sets.avacynrestored;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.ControlsPermanentCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DiscardTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.FilterPermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Loki
 */
public class ScrollOfGriselbrand extends CardImpl<ScrollOfGriselbrand> {
    private static FilterPermanent filter = new FilterPermanent("a Demon");

    static {
        filter.getSubtype().add("Demon");
        filter.setScopeSubtype(Filter.ComparisonScope.Any);
    }

    public ScrollOfGriselbrand(UUID ownerId) {
        super(ownerId, 221, "Scroll of Griselbrand", Rarity.COMMON, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.expansionSetCode = "AVR";

        // {1}, Sacrifice Scroll of Griselbrand: Target opponent discards a card. If you control a Demon, that player loses 3 life.
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new DiscardTargetEffect(1), new GenericManaCost(1));
        ability.addCost(new SacrificeSourceCost());
        ability.addEffect(new ConditionalOneShotEffect(new LoseLifeTargetEffect(3), new ControlsPermanentCondition(filter), "If you control a Demon, that player loses 3 life"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    public ScrollOfGriselbrand(final ScrollOfGriselbrand card) {
        super(card);
    }

    @Override
    public ScrollOfGriselbrand copy() {
        return new ScrollOfGriselbrand(this);
    }
}
