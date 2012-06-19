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
package mage.sets.fifthedition;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.OnlyDuringUpkeepCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.SkipUntapSourceEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;

/**
 *
 * @author jeffwadsworth
 */
public class ColossusOfSardia extends CardImpl<ColossusOfSardia> {

    public ColossusOfSardia(UUID ownerId) {
        super(ownerId, 358, "Colossus of Sardia", Rarity.RARE, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{9}");
        this.expansionSetCode = "5ED";
        this.subtype.add("Golem");

        this.power = new MageInt(9);
        this.toughness = new MageInt(9);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Colossus of Sardia doesn't untap during your untap step.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SkipUntapSourceEffect()));

        // {9}: Untap Colossus of Sardia. Activate this ability only during your upkeep.
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new UntapSourceEffect(), new ManaCostsImpl("{9}"));
        ability.addCost(new OnlyDuringUpkeepCost());
        this.addAbility(ability);
    }

    public ColossusOfSardia(final ColossusOfSardia card) {
        super(card);
    }

    @Override
    public ColossusOfSardia copy() {
        return new ColossusOfSardia(this);
    }
}
