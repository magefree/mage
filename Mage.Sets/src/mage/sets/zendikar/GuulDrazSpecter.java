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
package mage.sets.zendikar;

import java.util.Set;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.effects.common.DiscardTargetEffect;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.game.Game;

/**
 *
 * @author North
 */
public class GuulDrazSpecter extends CardImpl<GuulDrazSpecter> {

    private static final String ruleText = "{this} gets +3/+3 as long as an opponent has no cards in hand";

    public GuulDrazSpecter(UUID ownerId) {
        super(ownerId, 92, "Guul Draz Specter", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.expansionSetCode = "ZEN";
        this.subtype.add("Specter");

        this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(FlyingAbility.getInstance());
        // Guul Draz Specter gets +3/+3 as long as an opponent has no cards in hand.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinousEffect(
                new BoostSourceEffect(3, 3, Duration.WhileOnBattlefield),
                new GuulDrazSpecterCondition(),
                ruleText)));
        // Whenever Guul Draz Specter deals combat damage to a player, that player discards a card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new DiscardTargetEffect(1), false, true));
    }

    public GuulDrazSpecter(final GuulDrazSpecter card) {
        super(card);
    }

    @Override
    public GuulDrazSpecter copy() {
        return new GuulDrazSpecter(this);
    }
}

class GuulDrazSpecterCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = false;

        Set<UUID> opponents = game.getOpponents(source.getControllerId());
        for (UUID opponentId : opponents) {
            result |= game.getPlayer(opponentId).getHand().size() == 0;
        }

        return result;
    }
}