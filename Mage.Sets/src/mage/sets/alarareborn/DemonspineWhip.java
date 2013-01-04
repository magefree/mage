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
package mage.sets.alarareborn;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continious.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.game.Game;

/**
 *
 * @author jeffwadsworth
 */
public class DemonspineWhip extends CardImpl<DemonspineWhip> {

    public DemonspineWhip(UUID ownerId) {
        super(ownerId, 39, "Demonspine Whip", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{B}{R}");
        this.expansionSetCode = "ARB";
        this.subtype.add("Equipment");

        this.color.setRed(true);
        this.color.setBlack(true);

        // {X}: Equipped creature gets +X/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new BoostEquippedEffect(new XPaid(), new StaticValue(0), Constants.Duration.EndOfTurn), new ManaCostsImpl("{X}")));

        // Equip {1}
        this.addAbility(new EquipAbility(Constants.Outcome.AddAbility, new GenericManaCost(1)));
    }

    public DemonspineWhip(final DemonspineWhip card) {
        super(card);
    }

    @Override
    public DemonspineWhip copy() {
        return new DemonspineWhip(this);
    }
}

class XPaid implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility) {
        int paid = sourceAbility.getManaCostsToPay().getX();
        return paid;
    }

    @Override
    public DynamicValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "X paid";
    }

    @Override
    public String toString() {
        return "X";
    }
}
