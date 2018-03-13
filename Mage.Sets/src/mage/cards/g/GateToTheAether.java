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
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public class GateToTheAether extends CardImpl {

    public GateToTheAether(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{6}");

        // At the beginning of each player's upkeep, that player reveals the top card of his or her library. If it's an artifact, creature, enchantment, or land card, the player may put it onto the battlefield.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new GateToTheAetherEffect(), TargetController.ANY, false, true));
    }

    public GateToTheAether(final GateToTheAether card) {
        super(card);
    }

    @Override
    public GateToTheAether copy() {
        return new GateToTheAether(this);
    }
}

class GateToTheAetherEffect extends OneShotEffect {

    GateToTheAetherEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "that player reveals the top card of his or her library. If it's an artifact, creature, enchantment, or land card, the player may put it onto the battlefield";
    }

    GateToTheAetherEffect(final GateToTheAetherEffect effect) {
        super(effect);
    }

    @Override
    public GateToTheAetherEffect copy() {
        return new GateToTheAetherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player activePlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (activePlayer != null) {
            Card card = activePlayer.getLibrary().getFromTop(game);
            if (card != null) {
                activePlayer.revealCards("Gate to the Aether", new CardsImpl(card), game);
                if (card.isArtifact()
                        || card.isCreature()
                        || card.isEnchantment()
                        || card.isLand()) {
                    if (activePlayer.chooseUse(Outcome.PutCardInPlay, "Put " + card.getName() + " onto the battlefield?", source, game)) {
                        activePlayer.moveCards(card, Zone.BATTLEFIELD, source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
