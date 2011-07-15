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
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.PutLibraryIntoGraveTargetEffect;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.permanent.token.Token;
import mage.target.TargetPlayer;

/**
 *
 * @author Loki
 */
public class LichLordOfUnx extends CardImpl<LichLordOfUnx> {
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Zombies you control");

    static {
        filter.getSubtype().add("Zombie");
        filter.setScopeSubtype(Filter.ComparisonScope.Any);
    }

    public LichLordOfUnx (UUID ownerId) {
        super(ownerId, 24, "Lich Lord of Unx", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{U}{B}");
        this.expansionSetCode = "ARB";
        this.subtype.add("Zombie");
        this.subtype.add("Wizard");

		this.color.setBlue(true);
		this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new CreateTokenEffect(new ZombieWizardToken()), new ManaCostsImpl("{U}{B}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
        ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new LoseLifeTargetEffect(new PermanentsOnBattlefieldCount(filter)), new ManaCostsImpl("{U}{U}{B}{B}"));
        ability.addEffect(new PutLibraryIntoGraveTargetEffect(new PermanentsOnBattlefieldCount(filter, 1)));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public LichLordOfUnx (final LichLordOfUnx card) {
        super(card);
    }

    @Override
    public LichLordOfUnx copy() {
        return new LichLordOfUnx(this);
    }

}

class ZombieWizardToken extends Token {
    ZombieWizardToken() {
        super("Zombie Wizard", "a 1/1 blue and black Zombie Wizard creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        color.setBlack(true);
        subtype.add("Zombie");
        subtype.add("Wizard");
        power = new MageInt(1);
		toughness = new MageInt(1);
    }
}
