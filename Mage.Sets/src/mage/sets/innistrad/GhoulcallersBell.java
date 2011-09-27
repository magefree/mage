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
package mage.sets.innistrad;

import java.util.Collection;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author North
 */
public class GhoulcallersBell extends CardImpl<GhoulcallersBell> {

    public GhoulcallersBell(UUID ownerId) {
        super(ownerId, 224, "Ghoulcaller's Bell", Rarity.COMMON, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.expansionSetCode = "ISD";

        // {tap}: Each player puts the top card of his or her library into his or her graveyard.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GhoulcallersBellEffect(), new TapSourceCost()));
    }

    public GhoulcallersBell(final GhoulcallersBell card) {
        super(card);
    }

    @Override
    public GhoulcallersBell copy() {
        return new GhoulcallersBell(this);
    }
}

class GhoulcallersBellEffect extends OneShotEffect<GhoulcallersBellEffect> {

    public GhoulcallersBellEffect() {
        super(Outcome.Discard);
        this.staticText = "Each player puts the top card of his or her library into his or her graveyard";
    }

    public GhoulcallersBellEffect(final GhoulcallersBellEffect effect) {
        super(effect);
    }

    @Override
    public GhoulcallersBellEffect copy() {
        return new GhoulcallersBellEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Collection<Player> players = game.getPlayers().values();
        for (Player player : players) {
            if (player.getLibrary().size() > 0) {
                Card card = player.getLibrary().removeFromTop(game);
                if (card != null) {
                    card.moveToZone(Zone.GRAVEYARD, source.getId(), game, true);
                }
            }
        }
        return true;
    }
}
