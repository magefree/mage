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
package mage.sets.weatherlight;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continious.SetPowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 *
 * @author noxx
 */
public class MaraxusOfKeld extends CardImpl<MaraxusOfKeld> {

    private static FilterControlledPermanent filterUntapped = new FilterControlledPermanent("untapped artifacts, creatures, and lands you control");

    static {
        filterUntapped.setUseTapped(true);
        filterUntapped.setTapped(false);

        filterUntapped.getCardType().add(CardType.CREATURE);
        filterUntapped.getCardType().add(CardType.ARTIFACT);
        filterUntapped.getCardType().add(CardType.LAND);
        filterUntapped.setScopeCardType(Filter.ComparisonScope.Any);
    }

    public MaraxusOfKeld(UUID ownerId) {
        super(ownerId, 111, "Maraxus of Keld", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");
        this.expansionSetCode = "WTH";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Warrior");

        this.color.setRed(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Maraxus of Keld's power and toughness are each equal to the number of untapped artifacts, creatures, and lands you control.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.ALL, new SetPowerToughnessSourceEffect(new PermanentsOnBattlefieldCount(filterUntapped), Constants.Duration.EndOfGame)));
    }

    public MaraxusOfKeld(final MaraxusOfKeld card) {
        super(card);
    }

    @Override
    public MaraxusOfKeld copy() {
        return new MaraxusOfKeld(this);
    }
}
