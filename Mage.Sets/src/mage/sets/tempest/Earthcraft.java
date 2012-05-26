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
package mage.sets.tempest;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetControlledPermanent;

/**
 * @author Loki
 */
public class Earthcraft extends CardImpl<Earthcraft> {

    private final static FilterControlledCreaturePermanent filterCreature = new FilterControlledCreaturePermanent("untapped creature you control");
    private final static FilterControlledPermanent filterLand = new FilterControlledPermanent("basic land");

    static {
        filterCreature.setUseTapped(true);
        filterCreature.setTapped(false);
        filterCreature.setScopeCardType(Filter.ComparisonScope.Any);
        filterCreature.setScopeSubtype(Filter.ComparisonScope.Any);
        filterLand.getCardType().add(CardType.LAND);
        filterLand.setScopeCardType(Filter.ComparisonScope.Any);
        filterLand.getSupertype().add("Basic");
        filterLand.setScopeSupertype(Filter.ComparisonScope.Any);
    }

    public Earthcraft(UUID ownerId) {
        super(ownerId, 116, "Earthcraft", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");
        this.expansionSetCode = "TMP";
        this.color.setGreen(true);
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new UntapTargetEffect(), new TapTargetCost(new TargetControlledCreaturePermanent(1, 1, filterCreature, false)));
        ability.addTarget(new TargetControlledPermanent(filterLand));
        this.addAbility(ability);
    }

    public Earthcraft(final Earthcraft card) {
        super(card);
    }

    @Override
    public Earthcraft copy() {
        return new Earthcraft(this);
    }
}
