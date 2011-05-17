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
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.BasicManaAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;

/**
 *
 * @author Loki
 */
public class RuggedPrairie extends CardImpl<RuggedPrairie> {

    public RuggedPrairie (UUID ownerId) {
        super(ownerId, 178, "Rugged Prairie", Rarity.RARE, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "EVE";
        this.addAbility(new ColorlessManaAbility());
        this.addAbility(new RuggedPrairieFirstManaAbility());
        this.addAbility(new RuggedPrairieSecondManaAbility());
        this.addAbility(new RuggedPrairieThirdManaAbility());
    }

    public RuggedPrairie (final RuggedPrairie card) {
        super(card);
    }

    @Override
    public RuggedPrairie copy() {
        return new RuggedPrairie(this);
    }
}

class RuggedPrairieFirstManaAbility extends BasicManaAbility<RuggedPrairieFirstManaAbility> {

    public RuggedPrairieFirstManaAbility() {
        super(new ManaEffect(new Mana(2, 0, 0, 0, 0, 0, 0)));
        this.addCost(new ManaCostsImpl("{R/W}"));
        this.netMana.setRed(2);
    }

    public RuggedPrairieFirstManaAbility(final RuggedPrairieFirstManaAbility ability) {
        super(ability);
    }

    @Override
    public RuggedPrairieFirstManaAbility copy() {
        return new RuggedPrairieFirstManaAbility(this);
    }
}

class RuggedPrairieSecondManaAbility extends BasicManaAbility<RuggedPrairieSecondManaAbility> {

    public RuggedPrairieSecondManaAbility() {
        super(new ManaEffect(new Mana(1, 0, 0, 1, 0, 0, 0)));
        this.addCost(new ManaCostsImpl("{R/W}"));
        this.netMana.setRed(1);
        this.netMana.setWhite(1);
    }

    public RuggedPrairieSecondManaAbility(final RuggedPrairieSecondManaAbility ability) {
        super(ability);
    }

    @Override
    public RuggedPrairieSecondManaAbility copy() {
        return new RuggedPrairieSecondManaAbility(this);
    }
}

class RuggedPrairieThirdManaAbility extends BasicManaAbility<RuggedPrairieThirdManaAbility> {

    public RuggedPrairieThirdManaAbility() {
        super(new ManaEffect(new Mana(0, 0, 0, 2, 0, 0, 0)));
        this.addCost(new ManaCostsImpl("{R/W}"));
        this.netMana.setWhite(2);
    }

    public RuggedPrairieThirdManaAbility(final RuggedPrairieThirdManaAbility ability) {
        super(ability);
    }

    @Override
    public RuggedPrairieThirdManaAbility copy() {
        return new RuggedPrairieThirdManaAbility(this);
    }
}