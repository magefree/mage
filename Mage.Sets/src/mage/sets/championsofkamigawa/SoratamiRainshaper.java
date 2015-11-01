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

package mage.sets.championsofkamigawa;

import java.util.UUID;

import mage.constants.CardType;
import mage.constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetControlledPermanent;

/**
 * @author Loki
 */
public class SoratamiRainshaper extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledLandPermanent("a land");

    public SoratamiRainshaper(UUID ownerId) {
        super(ownerId, 89, "Soratami Rainshaper", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.expansionSetCode = "CHK";
        this.subtype.add("Moonfolk");
        this.subtype.add("Wizard");

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {3}, Return a land you control to its owner's hand: Target creature you control gains shroud until end of turn. (It can't be the target of spells or abilities.)
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilityTargetEffect(ShroudAbility.getInstance(), Duration.EndOfTurn), new GenericManaCost(3));
        ability.addCost(new ReturnToHandChosenControlledPermanentCost(new TargetControlledPermanent(filter)));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    public SoratamiRainshaper(final SoratamiRainshaper card) {
        super(card);
    }

    @Override
    public SoratamiRainshaper copy() {
        return new SoratamiRainshaper(this);
    }
}
