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
package mage.sets.shardsofalara;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.TapForManaAllTriggeredManaAbility;
import mage.abilities.effects.common.AddManaOfAnyTypeProducedEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.SetTargetPointer;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author Plopman
 */
public class KeeperOfProgenitus extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("a player taps a Mountain, Forest, or Plains");

    static {
        filter.add(Predicates.or(
            new SubtypePredicate("Mountain"),
            new SubtypePredicate("Forest"),
            new SubtypePredicate("Plains")
            ));
    }

    public KeeperOfProgenitus(UUID ownerId) {
        super(ownerId, 135, "Keeper of Progenitus", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.expansionSetCode = "ALA";
        this.subtype.add("Elf");
        this.subtype.add("Druid");

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever a player taps a Mountain, Forest, or Plains for mana, that player adds one mana to his or her mana pool of any type that land produced.
        this.addAbility(new TapForManaAllTriggeredManaAbility(
                new AddManaOfAnyTypeProducedEffect(),
                filter, SetTargetPointer.PERMANENT));
    }

    public KeeperOfProgenitus(final KeeperOfProgenitus card) {
        super(card);
    }

    @Override
    public KeeperOfProgenitus copy() {
        return new KeeperOfProgenitus(this);
    }
}
