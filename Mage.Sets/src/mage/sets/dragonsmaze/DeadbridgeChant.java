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
package mage.sets.dragonsmaze;

import java.util.UUID;

import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutTopCardOfLibraryIntoGraveControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */


public class DeadbridgeChant extends CardImpl<DeadbridgeChant> {

    public DeadbridgeChant(UUID ownerId) {
        super(ownerId, 63, "Deadbridge Chant", Rarity.MYTHIC, new CardType[]{CardType.ENCHANTMENT}, "{4}{B}{G}");
        this.expansionSetCode = "DGM";

        this.color.setBlack(true);
        this.color.setGreen(true);

        // When Deadbridge Chant enters the battlefield, put the top ten cards of your library into your graveyard.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new PutTopCardOfLibraryIntoGraveControllerEffect(10)));

        // At the beginning of your upkeep, choose a card at random in your graveyard. If it's a creature card, put it onto the battlefield. Otherwise, put it into your hand.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new DeadbridgeChantEffect(), TargetController.YOU, false));
    }

    public DeadbridgeChant(final DeadbridgeChant card) {
        super(card);
    }

    @Override
    public DeadbridgeChant copy() {
        return new DeadbridgeChant(this);
    }
}

class DeadbridgeChantEffect extends OneShotEffect<DeadbridgeChantEffect> {

    public DeadbridgeChantEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "choose a card at random in your graveyard. If it's a creature card, put it onto the battlefield. Otherwise, put it into your hand";
    }

    public DeadbridgeChantEffect(final DeadbridgeChantEffect effect) {
        super(effect);
    }

    @Override
    public DeadbridgeChantEffect copy() {
        return new DeadbridgeChantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null && !player.getGraveyard().isEmpty()) {
            Card card = player.getGraveyard().getRandom(game);
            if (card != null) {
                Zone targetZone = Zone.HAND;
                String text = " put into hand of ";
                if (card.getCardType().contains(CardType.CREATURE)) {
                    targetZone = Zone.BATTLEFIELD;
                    text = " put onto battlefield for ";
                }
                card.moveToZone(targetZone, source.getId(), game, false);
                game.informPlayers(new StringBuilder("Deadbridge Chant: ").append(card.getName()).append(text).append(player.getName()).toString());
                return true;
            }
        }
        return false;
    }
}
