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
package mage.sets.commander2014;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.token.WolfToken;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class WolfcallersHowl extends CardImpl {

    public WolfcallersHowl(UUID ownerId) {
        super(ownerId, 52, "Wolfcaller's Howl", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");
        this.expansionSetCode = "C14";


        // At the beginning of your upkeep, put X 2/2 green Wolf creature tokens onto the battlefield, where X is the number of your opponents with four or more cards in hand.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new WolfcallersHowlEffect(), TargetController.YOU, false));
    }

    public WolfcallersHowl(final WolfcallersHowl card) {
        super(card);
    }

    @Override
    public WolfcallersHowl copy() {
        return new WolfcallersHowl(this);
    }
}

class WolfcallersHowlEffect extends OneShotEffect {

    public WolfcallersHowlEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "put X 2/2 green Wolf creature tokens onto the battlefield, where X is the number of your opponents with four or more cards in hand";
    }

    public WolfcallersHowlEffect(final WolfcallersHowlEffect effect) {
        super(effect);
    }

    @Override
    public WolfcallersHowlEffect copy() {
        return new WolfcallersHowlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int count = 0;
            for(UUID playerId :controller.getInRange()) {
                if (controller.hasOpponent(playerId, game)) {
                    Player opponent = game.getPlayer(playerId);
                    if (opponent != null) {
                        if (opponent.getHand().size() >= 4) {
                            count++;
                        }
                    }
                }
            }
            if (count > 0) {
                return new CreateTokenEffect(new WolfToken("C14"), count).apply(game, source);
            }
            return true;
        }
        return false;
    }
}
