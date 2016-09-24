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
package mage.sets.iceage;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.PlayWithHandRevealedEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author Quercitron
 */
public class ZursWeirding extends CardImpl {

    public ZursWeirding(UUID ownerId) {
        super(ownerId, 112, "Zur's Weirding", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}");
        this.expansionSetCode = "ICE";

        // Players play with their hands revealed.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PlayWithHandRevealedEffect(TargetController.ANY)));

        // If a player would draw a card, he or she reveals it instead. Then any other player may pay 2 life. If a player does, put that card into its owner's graveyard. Otherwise, that player draws a card.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ZursWeirdingReplacementEffect()));
    }

    public ZursWeirding(final ZursWeirding card) {
        super(card);
    }

    @Override
    public ZursWeirding copy() {
        return new ZursWeirding(this);
    }
}

class ZursWeirdingReplacementEffect extends ReplacementEffectImpl {

    public ZursWeirdingReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        this.staticText = "If a player would draw a card, he or she reveals it instead. Then any other player may pay 2 life. If a player does, put that card into its owner's graveyard. Otherwise, that player draws a card.";
    }

    public ZursWeirdingReplacementEffect(final ZursWeirdingReplacementEffect effect) {
        super(effect);
    }

    @Override
    public ZursWeirdingReplacementEffect copy() {
        return new ZursWeirdingReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getTargetId());
        MageObject sourceObject = source.getSourceObject(game);
        if (player != null && sourceObject != null) {
            Card card = player.getLibrary().getFromTop(game);
            if (card != null) {
                // reveals it instead
                player.revealCards(sourceObject.getIdName() + " next draw of " + player.getName() + " (" + game.getTurnNum() + "|" + game.getPhase().getType() + ")", new CardsImpl(card), game);

                // Then any other player may pay 2 life. If a player does, put that card into its owner's graveyard
                String message = "Pay 2 life to put " + card.getLogName() + " into graveyard?";
                for (UUID playerId : game.getState().getPlayersInRange(player.getId(), game)) {
                    if (playerId.equals(player.getId())) {
                        continue;
                    }
                    Player otherPlayer = game.getPlayer(playerId);
                    if (otherPlayer.canPayLifeCost()
                            && otherPlayer.getLife() >= 2
                            && otherPlayer.chooseUse(Outcome.Benefit, message, source, game)) {
                        otherPlayer.loseLife(2, game);
                        player.moveCards(card, Zone.GRAVEYARD, source, game);
                        break;
                    }
                }

            }
            return true;
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return true;
    }

}
