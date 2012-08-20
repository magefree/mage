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
package mage.sets.shadowmoor;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.Mana;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;

/**
 *
 * @author jonubuu
 */
public class SunkenRuins extends CardImpl<SunkenRuins> {

    public SunkenRuins(UUID ownerId) {
        super(ownerId, 280, "Sunken Ruins", Rarity.RARE, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "SHM";

        // {tap}: Add {1} to your mana pool.
        this.addAbility(new ColorlessManaAbility());
        // {UB}, {tap}: Add {U}{U}, {U}{B}, or {B}{B} to your mana pool.
        SimpleManaAbility ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.BlueMana(2), new ManaCostsImpl("{U/B}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        ability = new SimpleManaAbility(Zone.BATTLEFIELD, new Mana(0, 0, 1, 0, 1, 0, 0), new ManaCostsImpl("{U/B}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.BlackMana(2), new ManaCostsImpl("{U/B}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public SunkenRuins(final SunkenRuins card) {
        super(card);
    }

    @Override
    public SunkenRuins copy() {
        return new SunkenRuins(this);
    }
}
