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
package mage.sets.riseoftheeldrazi;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.LevelUpAbility;
import mage.abilities.keyword.LevelerCardBuilder;
import mage.abilities.keyword.ShroudAbility;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.cards.LevelerCard;
import mage.constants.CardType;
import mage.constants.Rarity;

/**
 *
 * @author North, noxx
 */
public class HadaSpyPatrol extends LevelerCard {

    public HadaSpyPatrol(UUID ownerId) {
        super(ownerId, 71, "Hada Spy Patrol", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.expansionSetCode = "ROE";
        this.subtype.add("Human");
        this.subtype.add("Rogue");

        this.color.setBlue(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Level up 2Blue ({U}{U}: Put a level counter on this. Level up only as a sorcery.)
        this.addAbility(new LevelUpAbility(new ManaCostsImpl("{2}{U}")));

        // LEVEL 1-2
        // 2/2
        // Hada Spy Patrol can't be blocked.
        Abilities<Ability> abilities1 = new AbilitiesImpl<>();
        abilities1.add(new CantBeBlockedSourceAbility());

        // LEVEL 3+
        // 3/3
        // Shroud (This creature can't be the target of spells or abilities.)
        // Hada Spy Patrol can't be blocked.
        Abilities<Ability> abilities2 = new AbilitiesImpl<>();
        abilities2.add(ShroudAbility.getInstance());
        abilities2.add(new CantBeBlockedSourceAbility());
        
        LevelerCardBuilder.construct(this,
                new LevelerCardBuilder.LevelAbility(1, 2, abilities1, 2, 2),
                new LevelerCardBuilder.LevelAbility(3, -1, abilities2, 3, 3)
        );
    }

    public HadaSpyPatrol(final HadaSpyPatrol card) {
        super(card);
    }

    @Override
    public HadaSpyPatrol copy() {
        return new HadaSpyPatrol(this);
    }
}
