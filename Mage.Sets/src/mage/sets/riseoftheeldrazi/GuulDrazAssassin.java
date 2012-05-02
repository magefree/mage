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

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continious.BoostTargetEffect;
import mage.abilities.keyword.LevelUpAbility;
import mage.abilities.keyword.LevelerCardBuilder;
import mage.cards.LevelerCard;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author Loki, noxx
 */
public class GuulDrazAssassin extends LevelerCard<GuulDrazAssassin> {

    public GuulDrazAssassin (UUID ownerId) {
        super(ownerId, 112, "Guul Draz Assassin", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{B}");
        this.expansionSetCode = "ROE";
        this.subtype.add("Vampire");
        this.subtype.add("Assassin");
		this.color.setBlack(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(new LevelUpAbility(new ManaCostsImpl("{1}{B}")));

        Abilities<Ability> abilities1 = new AbilitiesImpl<Ability>();
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new BoostTargetEffect(-2, -2, Constants.Duration.EndOfTurn), new ManaCostsImpl("{B}"));
        ability.addTarget(new TargetCreaturePermanent());
        ability.addCost(new TapSourceCost());
        abilities1.add(ability);

        Abilities<Ability> abilities2 = new AbilitiesImpl<Ability>();
        Ability ability2 = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new BoostTargetEffect(-4, -4, Constants.Duration.EndOfTurn), new ManaCostsImpl("{B}"));
        ability2.addTarget(new TargetCreaturePermanent());
        ability2.addCost(new TapSourceCost());
        abilities2.add(ability2);

        LevelerCardBuilder.construct(this,
                new LevelerCardBuilder.LevelAbility(2, 3, abilities1, 2, 2),
                new LevelerCardBuilder.LevelAbility(4, -1, abilities2, 4, 4)
        );
    }

    public GuulDrazAssassin (final GuulDrazAssassin card) {
        super(card);
    }

    @Override
    public GuulDrazAssassin copy() {
        return new GuulDrazAssassin(this);
    }

}
