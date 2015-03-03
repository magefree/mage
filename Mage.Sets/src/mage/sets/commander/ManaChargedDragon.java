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
package mage.sets.commander;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public class ManaChargedDragon extends CardImpl {

    public ManaChargedDragon(UUID ownerId) {
        super(ownerId, 129, "Mana-Charged Dragon", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");
        this.expansionSetCode = "CMD";
        this.subtype.add("Dragon");
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Trample
        this.addAbility(TrampleAbility.getInstance());
        
        // Join forces - Whenever Mana-Charged Dragon attacks or blocks, each player starting with you may pay any amount of mana. Mana-Charged Dragon gets +X/+0 until end of turn, where X is the total amount of mana paid this way.
        this.addAbility(new ManaChargedDragonTriggeredAbility());
    }

    public ManaChargedDragon(final ManaChargedDragon card) {
        super(card);
    }

    @Override
    public ManaChargedDragon copy() {
        return new ManaChargedDragon(this);
    }
}

class ManaChargedDragonTriggeredAbility extends AttacksOrBlocksTriggeredAbility {
    
    ManaChargedDragonTriggeredAbility() {
        super(new ManaChargedDragonEffect(), false);
    }
    
    @Override
    public String getRule() {
        return "Join forces &mdash; Whenever {this} attacks or blocks, each player starting with you may pay any amount of mana. {this} gets +X/+0 until end of turn, where X is the total amount of mana paid this way";
    }
}

class ManaChargedDragonEffect extends OneShotEffect {
    
    protected static int playerPaysXGenericMana(Player player, Ability source, Game game) {
        int xValue = 0;
        boolean payed = false;
        while (player.isInGame() && !payed) {
            xValue = player.announceXMana(0, Integer.MAX_VALUE, "How much mana will you pay?", game, source);
            if (xValue > 0) {
                Cost cost = new GenericManaCost(xValue);
                payed = cost.pay(source, game, source.getSourceId(), player.getId(), false);
            } else {
                payed = true;
            }
        }
        game.informPlayers(new StringBuilder(player.getName()).append(" pays {").append(xValue).append("}.").toString());
        return xValue;
    }
    
    ManaChargedDragonEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "each player starting with you may pay any amount of mana. {this} gets +X/+0 until end of turn, where X is the total amount of mana paid this way";
    }
    
    ManaChargedDragonEffect(final ManaChargedDragonEffect effect) {
        super(effect);
    }
    
    @Override
    public ManaChargedDragonEffect copy() {
        return new ManaChargedDragonEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int xSum = 0;
            xSum += playerPaysXGenericMana(controller, source, game);
            for (UUID playerId : controller.getInRange()) {
                if (playerId != controller.getId()) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        xSum += playerPaysXGenericMana(player, source, game);

                    }
                }
            }
            ContinuousEffect effect = new BoostSourceEffect(xSum, 0, Duration.EndOfTurn);
            game.addEffect(effect, source);
            // prevent undo
            controller.resetStoredBookmark(game);
            return true;
        }
        return false;
    }
}
