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
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.keyword.FearAbility;
import mage.abilities.keyword.TransmuteAbility;
import mage.cards.CardImpl;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author Loki
 */
public class DimirHouseGuard extends CardImpl<DimirHouseGuard> {

    public DimirHouseGuard(UUID ownerId) {
        super(ownerId, 83, "Dimir House Guard", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.expansionSetCode = "RAV";
        this.subtype.add("Skeleton");

        this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        this.addAbility(FearAbility.getInstance());
        // Sacrifice a creature: Regenerate Dimir House Guard.
        this.addAbility(new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new RegenerateSourceEffect(), new SacrificeTargetCost(new TargetControlledCreaturePermanent())));
        // Transmute {1}{B}{B}
        this.addAbility(new TransmuteAbility("{1}{B}{B}"));
    }

    public DimirHouseGuard(final DimirHouseGuard card) {
        super(card);
    }

    @Override
    public DimirHouseGuard copy() {
        return new DimirHouseGuard(this);
    }
}
