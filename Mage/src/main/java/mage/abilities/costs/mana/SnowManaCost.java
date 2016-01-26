/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package mage.abilities.costs.mana;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.constants.ColoredManaSymbol;
import mage.filter.FilterObject;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.game.Game;
import mage.players.ManaPool;

public class SnowManaCost extends ManaCostImpl {

    private static final FilterObject filter = new FilterObject("Snow object");

    static {
        filter.add(new SupertypePredicate("Snow"));
    }

    public SnowManaCost() {
        this.cost = Mana.GenericMana(1);
        this.options.addMana(Mana.GenericMana(1));
        this.setSourceFilter(filter);
    }

    public SnowManaCost(SnowManaCost manaCost) {
        super(manaCost);
    }

    @Override
    public int convertedManaCost() {
        return 1;
    }

    @Override
    public void assignPayment(Game game, Ability ability, ManaPool pool, Cost costToPay) {
        this.assignGeneric(ability, game, pool, 1, costToPay);
    }

    @Override
    public boolean isPaid() {
        if (paid) {
            return true;
        }
        return this.isColorlessPaid(1);
    }

    @Override
    public String getText() {
        return "{S}";
    }

    @Override
    public SnowManaCost getUnpaid() {
        return this;
    }

    @Override
    public boolean testPay(Mana testMana) {
        return testMana.count() > 0;
    }

    @Override
    public SnowManaCost copy() {
        return new SnowManaCost(this);
    }

    @Override
    public boolean containsColor(ColoredManaSymbol coloredManaSymbol) {
        return false;
    }

}
