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

import java.util.UUID;

import mage.abilities.effects.CopyCardEffect;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.constants.Duration;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * The token will copy Pack Rat's two abilities. Its power and toughness will be
 * equal to the number of Rats you control (not the number of Rats you controlled
 * when the token entered the battlefield). It will also be able to create copies
 * of itself.
 *
 * The token won't copy counters on Pack Rat, nor will it copy other effects that
 * have changed Pack Rat's power, toughness, types, color, or so on. Normally,
 * this means the token will simply be a Pack Rat. But if any copy effects have
 * affected that Pack Rat, they're taken into account.
 *
 * If Pack Rat leaves the battlefield before its activated ability resolves,
 * the token will still enter the battlefield as a copy of Pack Rat, using
 * Pack Rat's copiable values from when it was last on the battlefield.
 *
 *
 * @author LevelX2
 */
public class PackRat extends CardImpl {
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Rats you control");

    static {
        filter.add(new SubtypePredicate("Rat"));
    }

    public PackRat(UUID ownerId) {
        super(ownerId, 73, "Pack Rat", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.expansionSetCode = "RTR";
        this.subtype.add("Rat");

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Pack Rat's power and toughness are each equal to the number of Rats you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetPowerToughnessSourceEffect(new PermanentsOnBattlefieldCount(filter), Duration.EndOfGame)));
        // {2}{B}, Discard a card: Put a token onto the battlefield that's a copy of Pack Rat.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CopyCardEffect(this, 1), new ManaCostsImpl("{2}{B}"));
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);
    }

    public PackRat(final PackRat card) {
        super(card);
    }

    @Override
    public PackRat copy() {
        return new PackRat(this);
    }
}

