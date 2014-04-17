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
package mage.sets.journeyintonyx;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.permanent.token.Token;

/**
 *
 * @author LevelX2
 */
public class RenownedWeaver extends CardImpl<RenownedWeaver> {

    public RenownedWeaver(UUID ownerId) {
        super(ownerId, 137, "Renowned Weaver", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{G}");
        this.expansionSetCode = "JOU";
        this.subtype.add("Human");
        this.subtype.add("Shaman");

        this.color.setGreen(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{G}, Sacrifice Renowned Weaver: Put a 1/3 green Spider enchantment creature token with reach onto the battlefield.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new RenownedWeaverSpiderToken(), 1), new ManaCostsImpl("{1}{G}")) ;
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
        
    }

    public RenownedWeaver(final RenownedWeaver card) {
        super(card);
    }

    @Override
    public RenownedWeaver copy() {
        return new RenownedWeaver(this);
    }
}

class RenownedWeaverSpiderToken extends Token {

    public RenownedWeaverSpiderToken() {
        super("Spider", "1/3 green Spider enchantment creature token with reach");
        this.setOriginalExpansionSetCode("JOU");
        cardType.add(CardType.ENCHANTMENT);
        cardType.add(CardType.CREATURE);        
        color.setColor(ObjectColor.GREEN);
        subtype.add("Spider");
        power = new MageInt(1);
        toughness = new MageInt(3);
        this.addAbility(ReachAbility.getInstance());
    }
}
