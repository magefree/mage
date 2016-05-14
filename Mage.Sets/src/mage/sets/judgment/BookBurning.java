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
package mage.sets.judgment;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author tomd1990
 */
public class BookBurning extends CardImpl {

    public BookBurning(UUID ownerId) {
        super(ownerId, 80, "Book Burning", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{1}{R}");
        this.expansionSetCode = "JUD";

        // Any player may have Book Burning deal 6 damage to him or her. If no one does, target player puts the top six cards of his or her library into his or her graveyard.
        this.getSpellAbility().addEffect(new BookBurningMillEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public BookBurning(final BookBurning card) {
        super(card);
    }

    @Override
    public BookBurning copy() {
        return new BookBurning(this);
    }
}

class BookBurningMillEffect extends OneShotEffect {

    public BookBurningMillEffect() {
        super(Outcome.Detriment);
        staticText = "Any player may have {source} deal 6 damage to him or her. If no one does, target player puts the top six cards of his or her library into his or her graveyard";
    }

    public BookBurningMillEffect(final BookBurningMillEffect effect) {
        super(effect);
    }

    @Override
    public BookBurningMillEffect copy() {
        return new BookBurningMillEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject != null) {
            boolean millCards = true;
            for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null && player.chooseUse(Outcome.Detriment, "Have " + sourceObject.getLogName() + " deal 6 damage to you?", source, game)) {
                    millCards = false;
                    player.damage(6, source.getSourceId(), game, false, true);
                    game.informPlayers(player.getLogName() + " has " + sourceObject.getLogName() + " deal 6 damage to him or her");
                }
            }
            if (millCards) {
                Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
                if (targetPlayer != null) {
                    targetPlayer.moveCards(targetPlayer.getLibrary().getTopCards(game, 6), Zone.GRAVEYARD, source, game);
                }
            }
            return true;
        }
        return false;
    }
}
