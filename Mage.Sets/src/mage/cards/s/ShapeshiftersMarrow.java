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
package mage.cards.s;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * author HCrescent
 */
public class ShapeshiftersMarrow extends CardImpl {

    public ShapeshiftersMarrow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{U}");

        // At the beginning of each opponent's upkeep, that player reveals the top card of his or her library. If it's a creature card, the player puts the card into his or her graveyard and Shapeshifter's Marrow becomes a copy of that card. (If it does, it loses this ability.)
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new ShapeshiftersMarrowEffect(), TargetController.OPPONENT, false));
    }

    public ShapeshiftersMarrow(final ShapeshiftersMarrow card) {
        super(card);
    }

    @Override
    public ShapeshiftersMarrow copy() {
        return new ShapeshiftersMarrow(this);
    }

    static class ShapeshiftersMarrowEffect extends OneShotEffect {

        public ShapeshiftersMarrowEffect() {
            super(Outcome.BecomeCreature);
            this.staticText = "that player reveals the top card of his or her library. If it's a creature card, the player puts the card into his or her graveyard and {this} becomes a copy of that card. (If it does, it loses this ability.)";
        }

        public ShapeshiftersMarrowEffect(final ShapeshiftersMarrowEffect effect) {
            super(effect);
        }

        @Override
        public ShapeshiftersMarrowEffect copy() {
            return new ShapeshiftersMarrowEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Player activePlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
            MageObject sourceObject = game.getObject(source.getSourceId());
            if (activePlayer != null && sourceObject != null) {
                Card card = activePlayer.getLibrary().getFromTop(game);
                if (card != null) {
                    activePlayer.revealCards(sourceObject.getIdName(), new CardsImpl(card), game);
                    if (card.isCreature()) {
                        activePlayer.moveCards(activePlayer.getLibrary().getTopCards(game, 1), Zone.GRAVEYARD, source, game);
                        CopyEffect copyEffect = new CopyEffect(Duration.Custom, card, source.getSourceId());
                        game.addEffect(copyEffect, source);
                    }
                }

                return true;
            }
            return false;
        }

    }

}
