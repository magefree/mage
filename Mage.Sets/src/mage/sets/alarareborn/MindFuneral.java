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
package mage.sets.alarareborn;

import java.util.UUID;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public class MindFuneral extends CardImpl<MindFuneral> {

    public MindFuneral(UUID ownerId) {
        super(ownerId, 26, "Mind Funeral", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{1}{U}{B}");
        this.expansionSetCode = "ARB";

        this.color.setBlue(true);
        this.color.setBlack(true);

        // Target opponent reveals cards from the top of his or her library until four land cards are revealed. That player puts all cards revealed this way into his or her graveyard.
        this.getSpellAbility().addEffect(new MindFuneralEffect());
        this.getSpellAbility().addTarget(new TargetOpponent(true));
        
    }

    public MindFuneral(final MindFuneral card) {
        super(card);
    }

    @Override
    public MindFuneral copy() {
        return new MindFuneral(this);
    }
}

class MindFuneralEffect extends OneShotEffect<MindFuneralEffect> {

    public MindFuneralEffect() {
        super(Outcome.Detriment);
        this.staticText = "Target opponent reveals cards from the top of his or her library until four land cards are revealed. That player puts all cards revealed this way into his or her graveyard";
    }

    public MindFuneralEffect(final MindFuneralEffect effect) {
        super(effect);
    }

    @Override
    public MindFuneralEffect copy() {
        return new MindFuneralEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (opponent != null) {
            Cards cards = new CardsImpl();
            int landsFound = 0;
            while (landsFound < 4 && opponent.getLibrary().size() > 0) {
                Card card = opponent.getLibrary().removeFromTop(game);
                if (card == null) {
                    break;
                }
                if (card.getCardType().contains(CardType.LAND)) {
                    landsFound++;
                }
                cards.add(card);
            }
            opponent.revealCards("Mind Funeral", cards, game);
            for (Card card: cards.getCards(game)) {
                card.moveToZone(Zone.GRAVEYARD, source.getId(), game, false);
            }
            return true;
        }

        return false;
    }
}
