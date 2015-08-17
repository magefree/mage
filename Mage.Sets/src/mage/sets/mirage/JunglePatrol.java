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
package mage.sets.mirage;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.BasicManaEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.Token;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public class JunglePatrol extends CardImpl {
    
    private static final FilterControlledPermanent  filter = new FilterControlledPermanent("a token named Wood");
    
    static {
        filter.add(new NamePredicate("Wood"));
        filter.add(new TokenPredicate());
    }

    public JunglePatrol(UUID ownerId) {
        super(ownerId, 121, "Jungle Patrol", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.expansionSetCode = "MIR";
        this.subtype.add("Human");
        this.subtype.add("Soldier");
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // {1}{G}, {tap}: Put a 0/1 green Wall creature token with defender named Wood onto the battlefield.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new WoodToken()), new ManaCostsImpl("{1}{G}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
        
        // Sacrifice a token named Wood: Add {R} to your mana pool.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, 
                new BasicManaEffect(Mana.RedMana),
                new SacrificeTargetCost(new TargetControlledPermanent(1, 1, filter, true))));
    }

    public JunglePatrol(final JunglePatrol card) {
        super(card);
    }

    @Override
    public JunglePatrol copy() {
        return new JunglePatrol(this);
    }
}

class WoodToken extends Token {

    public WoodToken() {
        super("Wood", "0/1 green Wall creature token with defender named Wood");
        this.setOriginalExpansionSetCode("MIR");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add("Wall");
        power = new MageInt(0);
        toughness = new MageInt(1);

        this.addAbility(DefenderAbility.getInstance());
    }
}