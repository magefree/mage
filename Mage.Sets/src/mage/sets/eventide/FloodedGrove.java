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

package mage.sets.eventide;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Mana;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.BasicManaEffect;
import mage.abilities.mana.BasicManaAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;

/**
 *
 * @author Loki
 */
public class FloodedGrove extends CardImpl<FloodedGrove> {

    public FloodedGrove (UUID ownerId) {
        super(ownerId, 177, "Flooded Grove", Rarity.RARE, new CardType[]{CardType.LAND}, null);
        this.expansionSetCode = "EVE";
        this.addAbility(new ColorlessManaAbility());
        this.addAbility(new FloodedGroveFirstManaAbility());
        this.addAbility(new FloodedGroveSecondManaAbility());
        this.addAbility(new FloodedGroveThirdManaAbility());
    }

    public FloodedGrove (final FloodedGrove card) {
        super(card);
    }

    @Override
    public FloodedGrove copy() {
        return new FloodedGrove(this);
    }

}

class FloodedGroveFirstManaAbility extends BasicManaAbility<FloodedGroveFirstManaAbility> {

    public FloodedGroveFirstManaAbility() {
        super(new BasicManaEffect(new Mana(0, 2, 0, 0, 0, 0, 0)));
        this.addManaCost(new ManaCostsImpl("{G/U}"));
        this.netMana.setGreen(2);
    }

    public FloodedGroveFirstManaAbility(final FloodedGroveFirstManaAbility ability) {
        super(ability);
    }

    @Override
    public FloodedGroveFirstManaAbility copy() {
        return new FloodedGroveFirstManaAbility(this);
    }
}

class FloodedGroveSecondManaAbility extends BasicManaAbility<FloodedGroveSecondManaAbility> {

    public FloodedGroveSecondManaAbility() {
        super(new BasicManaEffect(new Mana(0, 1, 1, 0, 0, 0, 0)));
        this.addManaCost(new ManaCostsImpl("{G/U}"));
        this.netMana.setGreen(1);
        this.netMana.setBlue(1);
    }

    public FloodedGroveSecondManaAbility(final FloodedGroveSecondManaAbility ability) {
        super(ability);
    }

    @Override
    public FloodedGroveSecondManaAbility copy() {
        return new FloodedGroveSecondManaAbility(this);
    }
}

class FloodedGroveThirdManaAbility extends BasicManaAbility<FloodedGroveThirdManaAbility> {

    public FloodedGroveThirdManaAbility() {
        super(new BasicManaEffect(new Mana(0, 0, 2, 0, 0, 0, 0)));
        this.addManaCost(new ManaCostsImpl("{G/U}"));
        this.netMana.setBlue(2);
    }

    public FloodedGroveThirdManaAbility(final FloodedGroveThirdManaAbility ability) {
        super(ability);
    }

    @Override
    public FloodedGroveThirdManaAbility copy() {
        return new FloodedGroveThirdManaAbility(this);
    }
}