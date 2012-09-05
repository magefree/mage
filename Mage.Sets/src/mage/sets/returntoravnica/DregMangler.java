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
package mage.sets.returntoravnica;

import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.ScavengeAbility;
import mage.cards.CardImpl;

import java.util.UUID;

/**
 *
 * @author magenoxx_at_gmail.com
 */
public class DregMangler extends CardImpl<DregMangler> {

    public DregMangler(UUID ownerId) {
        super(ownerId, 158, "Dreg Mangler", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{B}{G}");
        this.expansionSetCode = "RTR";
        this.subtype.add("Plant");
        this.subtype.add("Zombie");

        this.color.setGreen(true);
        this.color.setBlack(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        // Scavenge {3}{B}{G}
        this.addAbility(new ScavengeAbility(new ManaCostsImpl("{3}{B}{G}")));
    }

    public DregMangler(final DregMangler card) {
        super(card);
    }

    @Override
    public DregMangler copy() {
        return new DregMangler(this);
    }
}
