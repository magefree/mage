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
package mage.sets.saviorsofkamigawa;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class AdamaroFirstToDesire extends CardImpl {

    public AdamaroFirstToDesire(UUID ownerId) {
        super(ownerId, 91, "Adamaro, First to Desire", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");
        this.expansionSetCode = "SOK";
        this.supertype.add("Legendary");
        this.subtype.add("Spirit");

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Adamaro, First to Desire's power and toughness are each equal to the number of cards in the hand of the opponent with the most cards in hand.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetPowerToughnessSourceEffect(new MostCardsInOpponentsHandCount(), Duration.WhileOnBattlefield)));
    }

    public AdamaroFirstToDesire(final AdamaroFirstToDesire card) {
        super(card);
    }

    @Override
    public AdamaroFirstToDesire copy() {
        return new AdamaroFirstToDesire(this);
    }
}

class MostCardsInOpponentsHandCount implements DynamicValue {
    @Override
    public int calculate(Game game, Ability source, Effect effect) {
        int maxCards = 0;
        for (UUID opponentId: game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent != null) {
                int cards = opponent.getHand().size();
                if (cards > maxCards) {
                    maxCards = cards;
                }
            }
        }
        return maxCards;
    }

    @Override
    public DynamicValue copy() {
        return new mage.abilities.dynamicvalue.common.CardsInControllerHandCount();
    }

    @Override
    public String getMessage() {
        return "cards in the hand of the opponent with the most cards in hand";
    }

    @Override
    public String toString() {
        return "1";
    }
}
