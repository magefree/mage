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
package mage.sets.onslaught;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.common.continious.SetPowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public class SoullessOne extends CardImpl<SoullessOne> {

    public SoullessOne(UUID ownerId) {
        super(ownerId, 171, "Soulless One", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.expansionSetCode = "ONS";
        this.subtype.add("Zombie");
        this.subtype.add("Avatar");

        this.color.setBlack(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Soulless One's power and toughness are each equal to the number of Zombies on the battlefield plus the number of Zombie cards in all graveyards.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new SetPowerToughnessSourceEffect(new SoullessOneDynamicCount(), Constants.Duration.WhileOnBattlefield)));
    }

    public SoullessOne(final SoullessOne card) {
        super(card);
    }

    @Override
    public SoullessOne copy() {
        return new SoullessOne(this);
    }
}

class SoullessOneDynamicCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility) {
        FilterPermanent zombiesBattlefield = new FilterPermanent("Zombies on the battlefield");
        FilterCard zombiesInGraveyard = new FilterCard("Zombie cards in all graveyards");
        zombiesBattlefield.add(new SubtypePredicate("Zombie"));
        zombiesInGraveyard.add(new SubtypePredicate("Zombie"));
        
        int count = game.getBattlefield().count(zombiesBattlefield, sourceAbility.getSourceId(), sourceAbility.getControllerId(), game);
        for (Player player : game.getPlayers().values()) {
            if (player != null) {
                count += player.getGraveyard().count(zombiesInGraveyard, game);
            }
        }
        return count;
    }

    @Override
    public DynamicValue copy() {
        return new SoullessOneDynamicCount();
    }
    
    @Override
    public String getMessage() {
        return "Zombies on the battlefield plus the number of Zombie cards in all graveyards";
    }

    @Override
    public String toString() {
        return "1";
    }
}
