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
package mage.sets.lorwyn;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continious.BoostControlledEffect;
import mage.abilities.effects.common.continious.GainAbilityControlledEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author Loki
 */
public class ScionOfOona extends CardImpl<ScionOfOona> {

    private final static FilterCreaturePermanent filter1 = new FilterCreaturePermanent("Faerie creatures");
    private final static FilterControlledPermanent filter2 = new FilterControlledPermanent("Faeries");

    static {
        filter1.getSubtype().add("Faerie");
        filter1.setScopeSubtype(Filter.ComparisonScope.Any);
        filter2.getSubtype().add("Faerie");
        filter2.setScopeSubtype(Filter.ComparisonScope.Any);
    }

    public ScionOfOona(UUID ownerId) {
        super(ownerId, 83, "Scion of Oona", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.expansionSetCode = "LRW";
        this.subtype.add("Faerie");
        this.subtype.add("Soldier");
        this.color.setBlue(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(FlashAbility.getInstance());
        this.addAbility(FlyingAbility.getInstance());
        // Other Faerie creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Constants.Duration.WhileOnBattlefield, filter1, true)));
        // Other Faeries you control have shroud.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new GainAbilityControlledEffect(ShroudAbility.getInstance(), Constants.Duration.EndOfTurn, filter2, true)));
    }

    public ScionOfOona(final ScionOfOona card) {
        super(card);
    }

    @Override
    public ScionOfOona copy() {
        return new ScionOfOona(this);
    }
}
