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
package mage.sets.lorwyn;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.ChampionAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.SetTargetPointer;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.permanent.token.Token;

/**
 *
 * @author fireshoes
 */
public class BoggartMob extends CardImpl {
    
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a Goblin you control");

    static {
        filter.add(new SubtypePredicate("Goblin"));
    }

    public BoggartMob(UUID ownerId) {
        super(ownerId, 104, "Boggart Mob", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.expansionSetCode = "LRW";
        this.subtype.add("Goblin");
        this.subtype.add("Warrior");
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Champion a Goblin
        this.addAbility(new ChampionAbility(this, "Goblin"));
        
        // Whenever a Goblin you control deals combat damage to a player, you may put a 1/1 black Goblin Rogue creature token onto the battlefield.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new CreateTokenEffect(new BlackGoblinRogueToken()), 
                filter, true, SetTargetPointer.NONE, true));
    }

    public BoggartMob(final BoggartMob card) {
        super(card);
    }

    @Override
    public BoggartMob copy() {
        return new BoggartMob(this);
    }
}

class BlackGoblinRogueToken extends Token {
    BlackGoblinRogueToken() {
        super("Goblin Rogue", "1/1 black Goblin Rogue creature token");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add("Goblin");
        subtype.add("Rogue");
        power = new MageInt(1);
        toughness = new MageInt(1);
    }
}