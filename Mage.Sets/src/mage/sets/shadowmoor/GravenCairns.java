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
public class GravenCairns extends CardImpl<GravenCairns> {

    public GravenCairns(UUID ownerId) {
        super(ownerId, 272, "Graven Cairns", Rarity.RARE, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "SHM";

        // {tap}: Add {1} to your mana pool.
        this.addAbility(new ColorlessManaAbility());
        // {BR}, {tap}: Add {B}{B}, {B}{R}, or {R}{R} to your mana pool.
        this.addAbility(new GravenCairnsFirstManaAbility());
        this.addAbility(new GravenCairnsSecondManaAbility());
        this.addAbility(new GravenCairnsThirdManaAbility());
    }

    public GravenCairns(final GravenCairns card) {
        super(card);
    }

    @Override
    public GravenCairns copy() {
        return new GravenCairns(this);
    }
}

class GravenCairnsFirstManaAbility extends BasicManaAbility<GravenCairnsFirstManaAbility> {

    public GravenCairnsFirstManaAbility() {
        super(new BasicManaEffect(new Mana(0, 0, 0, 0, 2, 0, 0)));
        this.addManaCost(new ManaCostsImpl("{B/R}"));
        this.netMana.setBlack(2);
    }

    public GravenCairnsFirstManaAbility(final GravenCairnsFirstManaAbility ability) {
        super(ability);
    }

    @Override
    public GravenCairnsFirstManaAbility copy() {
        return new GravenCairnsFirstManaAbility(this);
    }
}

class GravenCairnsSecondManaAbility extends BasicManaAbility<GravenCairnsSecondManaAbility> {

    public GravenCairnsSecondManaAbility() {
        super(new BasicManaEffect(new Mana(1, 0, 0, 0, 1, 0, 0)));
        this.addManaCost(new ManaCostsImpl("{B/R}"));
        this.netMana.setBlack(1);
        this.netMana.setRed(1);
    }

    public GravenCairnsSecondManaAbility(final GravenCairnsSecondManaAbility ability) {
        super(ability);
    }

    @Override
    public GravenCairnsSecondManaAbility copy() {
        return new GravenCairnsSecondManaAbility(this);
    }
}

class GravenCairnsThirdManaAbility extends BasicManaAbility<GravenCairnsThirdManaAbility> {

    public GravenCairnsThirdManaAbility() {
        super(new BasicManaEffect(new Mana(2, 0, 0, 0, 0, 0, 0)));
        this.addManaCost(new ManaCostsImpl("{B/R}"));
        this.netMana.setRed(2);
    }

    public GravenCairnsThirdManaAbility(final GravenCairnsThirdManaAbility ability) {
        super(ability);
    }

    @Override
    public GravenCairnsThirdManaAbility copy() {
        return new GravenCairnsThirdManaAbility(this);
    }
}