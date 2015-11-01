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

package mage.sets.conflux;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.BasicManaEffect;
import mage.abilities.mana.BasicManaAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

/**
 *
 * @author Loki
 */
public class KnotvineMystic extends CardImpl{

    public KnotvineMystic(UUID ownerId) {
        super(ownerId, 114, "Knotvine Mystic", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{R}{G}{W}");
        this.expansionSetCode = "CON";


        this.subtype.add("Elf");
        this.subtype.add("Druid");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        
        // {1}, {T}: Add {R}{G}{W} to your mana pool.
        Ability ability = new KnotvineMysticManaAbility();
        ability.addManaCost(new GenericManaCost(1));
        this.addAbility(ability);
    }

    public KnotvineMystic(final KnotvineMystic card) {
        super(card);
    }

    @Override
    public KnotvineMystic copy() {
        return new KnotvineMystic(this);
    }

}

class KnotvineMysticManaAbility extends BasicManaAbility {

    public KnotvineMysticManaAbility() {
        super(new BasicManaEffect(new Mana(1, 1, 0, 1, 0, 0, 0)));
        this.netMana.add(new Mana(1, 1, 0, 1, 0, 0, 0));
    }

    public KnotvineMysticManaAbility(final KnotvineMysticManaAbility ability) {
        super(ability);
    }

    @Override
    public KnotvineMysticManaAbility copy() {
        return new KnotvineMysticManaAbility(this);
    }
}
