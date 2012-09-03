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
package mage.sets.shardsofalara;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardsImpl;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author North
 */
public class CovenantOfMinds extends CardImpl<CovenantOfMinds> {

    public CovenantOfMinds(UUID ownerId) {
        super(ownerId, 38, "Covenant of Minds", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{4}{U}");
        this.expansionSetCode = "ALA";

        this.color.setBlue(true);

        // Reveal the top three cards of your library. Target opponent may choose to put those cards into your hand.
        // If he or she doesn't, put those cards into your graveyard and draw five cards.
        this.getSpellAbility().addEffect(new CovenantOfMindsEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    public CovenantOfMinds(final CovenantOfMinds card) {
        super(card);
    }

    @Override
    public CovenantOfMinds copy() {
        return new CovenantOfMinds(this);
    }
}

class CovenantOfMindsEffect extends OneShotEffect<CovenantOfMindsEffect> {

    public CovenantOfMindsEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Reveal the top three cards of your library. Target opponent may choose to put those cards into your hand. If he or she doesn't, put those cards into your graveyard and draw five cards";
    }

    public CovenantOfMindsEffect(final CovenantOfMindsEffect effect) {
        super(effect);
    }

    @Override
    public CovenantOfMindsEffect copy() {
        return new CovenantOfMindsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (player == null || opponent == null) {
            return false;
        }

        CardsImpl cards = new CardsImpl();
        int count = Math.min(player.getLibrary().size(), 3);
        for (int i = 0; i < count; i++) {
            cards.add(player.getLibrary().removeFromTop(game));
        }

        if (!cards.isEmpty()) {
            player.revealCards("Covenant of Minds", cards, game);

            StringBuilder sb = new StringBuilder();
            sb.append("Put the revealed cards into ").append(player.getName()).append("'s hand?");
            sb.append(" If you don't, those cards are put into his graveyard and he will draw five cards.");

            Zone zone = Zone.GRAVEYARD;
            if (opponent.chooseUse(Outcome.Neutral, sb.toString(), game)) {
                zone = Zone.HAND;
            } else {
                player.drawCards(5, game);
            }

            for (Card card : cards.getCards(game)) {
                card.moveToZone(zone, source.getSourceId(), game, true);
            }
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(player.getName()).append("'s library is empty? Do you want him to draw five cards?");
            if (!opponent.chooseUse(Outcome.Benefit, sb.toString(), game)) {
                player.drawCards(5, game);
            }
        }

        return true;
    }
}
