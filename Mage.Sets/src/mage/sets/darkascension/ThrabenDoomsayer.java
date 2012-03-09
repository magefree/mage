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
package mage.sets.darkascension;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.FatefulHourCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continious.BoostControlledEffect;
import mage.cards.CardImpl;

/**
 *
 * @author anonymous
 */
public class ThrabenDoomsayer extends CardImpl<ThrabenDoomsayer> {

    public ThrabenDoomsayer(UUID ownerId) {
        super(ownerId, 25, "Thraben Doomsayer", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");
        this.expansionSetCode = "DKA";
        this.subtype.add("Human");
        this.subtype.add("Cleric");

        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {tap}: Put a 1/1 white Human creature token onto the battlefield.
        this.addAbility(new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new CreateTokenEffect(new HumanToken()), new TapSourceCost()));
        // Fateful hour - As long as you have 5 or less life, other creatures you control get +2/+2.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new ConditionalContinousEffect(new BoostControlledEffect(2, 2, Constants.Duration.WhileOnBattlefield, false),
                FatefulHourCondition.getInstance(), "As long as you have 5 or less life, other creatures you control get +2/+2")));
    }

    public ThrabenDoomsayer(final ThrabenDoomsayer card) {
        super(card);
    }

    @Override
    public ThrabenDoomsayer copy() {
        return new ThrabenDoomsayer(this);
    }
}
