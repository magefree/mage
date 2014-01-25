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
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */

public class PutOnLibrarySourceEffect extends OneShotEffect<PutOnLibrarySourceEffect> {

    boolean onTop;

    public PutOnLibrarySourceEffect(boolean onTop) {
        super(Outcome.ReturnToHand);
        this.onTop = onTop;
    }

    public PutOnLibrarySourceEffect(boolean onTop, String rule) {
        super(Outcome.ReturnToHand);
        this.onTop = onTop;
        this.staticText = rule;
    }

    public PutOnLibrarySourceEffect(final PutOnLibrarySourceEffect effect) {
        super(effect);
        this.onTop = effect.onTop;
    }

    @Override
    public PutOnLibrarySourceEffect copy() {
        return new PutOnLibrarySourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = false;
        switch (game.getState().getZone(source.getSourceId())) {
            case BATTLEFIELD:
                Permanent permanent = game.getPermanent(source.getSourceId());
                if (permanent != null) {
                    result |= permanent.moveToZone(Zone.LIBRARY, source.getSourceId(), game, onTop);
                }
            case GRAVEYARD:
                Card card = game.getCard(source.getSourceId());
                if (card != null) {
                    for (Player player : game.getPlayers().values()) {
                        if (player.getGraveyard().contains(card.getId())) {
                            player.getGraveyard().remove(card);
                            result |= card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, onTop);
                        }
                    }
                }
        }
        return result;
    }

    @Override
    public String getText(Mode mode) {
        StringBuilder sb = new StringBuilder();
        if (this.staticText != null && !this.staticText.isEmpty()) {
            sb.append(staticText);
        } else {
            // Put Champion of Stray Souls on top of your library from your graveyard
            sb.append("Put {this} on");
            sb.append(onTop ? "top" : "the bottom").append(" of it's owner's library");
        }
        return sb.toString();

    }
}
