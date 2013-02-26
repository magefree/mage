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
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CastCardFromOutsideTheGameEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.AnnihilatorAbility;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.permanent.token.EldraziSpawnToken;

import java.util.UUID;

/**
 * @author magenoxx_at_gmail.com
 */
public class SpawnsireOfUlamog extends CardImpl<SpawnsireOfUlamog> {
    
    private static final String ruleText = "Cast any number of Eldrazi cards you own from outside the game without paying their mana costs";

    private static final FilterCard filter = new FilterCard("Eldrazi cards");

    static {
        filter.add(new SubtypePredicate("Eldrazi"));
    }
    
    public SpawnsireOfUlamog(UUID ownerId) {
        super(ownerId, 11, "Spawnsire of Ulamog", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{10}");
        this.expansionSetCode = "ROE";
        this.subtype.add("Eldrazi");

        this.power = new MageInt(7);
        this.toughness = new MageInt(11);

        // Annihilator 1
        this.addAbility(new AnnihilatorAbility(1));

        // {4}: Put two 0/1 colorless Eldrazi Spawn creature tokens onto the battlefield. They have "Sacrifice this creature: Add {1} to your mana pool."
        this.addAbility(new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new CreateTokenEffect(new EldraziSpawnToken(), 2), new GenericManaCost(4)));

        // {20}: Cast any number of Eldrazi cards you own from outside the game without paying their mana costs.
        this.addAbility(new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new CastCardFromOutsideTheGameEffect(filter, ruleText), new GenericManaCost(20)));
    }

    public SpawnsireOfUlamog(final SpawnsireOfUlamog card) {
        super(card);
    }

    @Override
    public SpawnsireOfUlamog copy() {
        return new SpawnsireOfUlamog(this);
    }
}
