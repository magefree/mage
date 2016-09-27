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
package mage.sets.starwars;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.conditional.ConditionalSpellManaBuilder;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.permanent.token.TrooperToken;

/**
 *
 * @author Styxo
 */
public class KaminoCloningFacility extends CardImpl {

    private static final FilterSpell FILTER = new FilterSpell("a Trooper spell");

    static {
        FILTER.add(new SubtypePredicate("Trooper"));
    }

    public KaminoCloningFacility(UUID ownerId) {
        super(ownerId, 246, "Kamino Cloning Facility", Rarity.RARE, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "SWS";

        // {T}: Add {C} to your mana pool.
        this.addAbility(new ColorlessManaAbility());

        // {T} Add one mana of any color to your mana pool. Spend this mana only to cast a Trooper spell.
        this.addAbility(new ConditionalAnyColorManaAbility(new TapSourceCost(), 1, new ConditionalSpellManaBuilder(FILTER), true));

        // {5}, {T}: Put a 1/1 white Trooper creature tokens onto the battlefield.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new TrooperToken(), 1), new ManaCostsImpl("{5}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public KaminoCloningFacility(final KaminoCloningFacility card) {
        super(card);
    }

    @Override
    public KaminoCloningFacility copy() {
        return new KaminoCloningFacility(this);
    }
}
