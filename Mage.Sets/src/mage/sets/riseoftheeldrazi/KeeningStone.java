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
package mage.sets.riseoftheeldrazi;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.players.Library;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author North
 */
public class KeeningStone extends CardImpl<KeeningStone> {

    public KeeningStone(UUID ownerId) {
        super(ownerId, 219, "Keening Stone", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{6}");
        this.expansionSetCode = "ROE";

        // {5}, {tap}: Target player puts the top X cards of his or her library into his or her graveyard, where X is the number of cards in that player's graveyard.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new KeeningStoneEffect(), new GenericManaCost(5));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public KeeningStone(final KeeningStone card) {
        super(card);
    }

    @Override
    public KeeningStone copy() {
        return new KeeningStone(this);
    }
}

class KeeningStoneEffect extends OneShotEffect<KeeningStoneEffect> {

    public KeeningStoneEffect() {
        super(Outcome.Neutral);
        this.staticText = "Target player puts the top X cards of his or her library into his or her graveyard, where X is the number of cards in that player's graveyard";
    }

    public KeeningStoneEffect(final KeeningStoneEffect effect) {
        super(effect);
    }

    @Override
    public KeeningStoneEffect copy() {
        return new KeeningStoneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            Library library = player.getLibrary();

            int amount = Math.min(player.getGraveyard().size(), library.size());
            for (int i = 0; i < amount; i++) {
                Card card = library.removeFromTop(game);
                if (card != null) {
                    card.moveToZone(Zone.GRAVEYARD, source.getSourceId(), game, true);
                }
            }

            if (amount > 0) {
                return true;
            }
        }
        return false;
    }
}
