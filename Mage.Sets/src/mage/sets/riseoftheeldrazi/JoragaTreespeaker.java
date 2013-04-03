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
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.BasicManaEffect;
import mage.abilities.effects.common.continious.GainAbilityControlledEffect;
import mage.abilities.keyword.LevelUpAbility;
import mage.abilities.keyword.LevelerCardBuilder;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.LevelerCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;


/**
 *
 * @author BetaSteward_at_googlemail.com, noxx
 */
public class JoragaTreespeaker extends LevelerCard<JoragaTreespeaker> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Elves");

    static {
        filter.add(new SubtypePredicate("Elf"));
    }

    public JoragaTreespeaker(UUID ownerId) {
        super(ownerId, 190, "Joraga Treespeaker", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{G}");
        this.expansionSetCode = "ROE";
        this.subtype.add("Elf");
        this.subtype.add("Druid");

        this.color.setGreen(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Level up {1}{G} ({1}{G}: Put a level counter on this. Level up only as a sorcery.)
        this.addAbility(new LevelUpAbility(new ManaCostsImpl("{1}{G}")));

        // LEVEL 1-4
        // 1/2
        // {T}: Add {G}{G} to your mana pool.
        Abilities<Ability> abilities1 = new AbilitiesImpl<Ability>();
        abilities1.add(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.GreenMana(2), new TapSourceCost()));

        // LEVEL 5+
        // 1/4
        // Elves you control have "{T}: Add {G}{G} to your mana pool."
        Abilities<Ability> abilities2 = new AbilitiesImpl<Ability>();
        abilities2.add(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityControlledEffect(new SimpleManaAbility(Zone.BATTLEFIELD,
                    new BasicManaEffect(Mana.GreenMana(2)),
                    new TapSourceCost()),
                Duration.WhileOnBattlefield, filter)));

        LevelerCardBuilder.construct(this,
                new LevelerCardBuilder.LevelAbility(1, 4, abilities1, 1, 2),
                new LevelerCardBuilder.LevelAbility(5, -1, abilities2, 1, 4)
        );
    }

    public JoragaTreespeaker(final JoragaTreespeaker card) {
        super(card);
    }

    @Override
    public JoragaTreespeaker copy() {
        return new JoragaTreespeaker(this);
    }

}
