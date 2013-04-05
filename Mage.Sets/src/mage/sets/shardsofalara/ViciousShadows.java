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
package mage.sets.shardsofalara;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author Plopman
 */
public class ViciousShadows extends CardImpl<ViciousShadows> {

    public ViciousShadows(UUID ownerId) {
        super(ownerId, 119, "Vicious Shadows", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{6}{R}");
        this.expansionSetCode = "ALA";

        this.color.setRed(true);

        // Whenever a creature dies, you may have Vicious Shadows deal damage to target player equal to the number of cards in that player's hand.
        Ability ability = new DiesCreatureTriggeredAbility(new DamageTargetEffect(new TargetPlayerCardsInHandCount()), true);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public ViciousShadows(final ViciousShadows card) {
        super(card);
    }

    @Override
    public ViciousShadows copy() {
        return new ViciousShadows(this);
    }
}

class TargetPlayerCardsInHandCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility) {
        Player targetPlayer = game.getPlayer(sourceAbility.getFirstTarget());
        if (targetPlayer != null) {
            return targetPlayer.getHand().size();
        }

        return 0;
    }

    @Override
    public DynamicValue copy() {
        return new TargetPlayerCardsInHandCount();
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "target player's cards in hand";
    }
}