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
package mage.sets.urzaslegacy;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.common.continious.SetPowerToughnessSourceEffect;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Plopman
 */
public class MultaniMaroSorcerer extends CardImpl<MultaniMaroSorcerer> {

    public MultaniMaroSorcerer(UUID ownerId) {
        super(ownerId, 107, "Multani, Maro-Sorcerer", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");
        this.expansionSetCode = "ULG";
        this.supertype.add("Legendary");
        this.subtype.add("Elemental");

        this.color.setGreen(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Shroud
        this.addAbility(ShroudAbility.getInstance());
        // Multani, Maro-Sorcerer's power and toughness are each equal to the total number of cards in all players' hands.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.ALL, new SetPowerToughnessSourceEffect(new CardsInHandCount(), Constants.Duration.WhileOnBattlefield)));
    }

    public MultaniMaroSorcerer(final MultaniMaroSorcerer card) {
        super(card);
    }

    @Override
    public MultaniMaroSorcerer copy() {
        return new MultaniMaroSorcerer(this);
    }
}

class CardsInHandCount implements DynamicValue {
    @Override
    public int calculate(Game game, Ability sourceAbility) {
        int count = 0;
        for (UUID playerId: game.getPlayer(sourceAbility.getControllerId()).getInRange()) {
            Player player = game.getPlayer(playerId);
            if (player != null)
            {
                count += player.getHand().size();
            }
        }
        return count;
    }

    @Override
    public DynamicValue clone() {
        return new CardsInHandCount();
    }

    @Override
    public String getMessage() {
        return "the total number of cards in all players' hands";
    }

    @Override
    public String toString() {
        return "1";
    }
}
