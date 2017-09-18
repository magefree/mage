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
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public class GamePreserve extends CardImpl {

    public GamePreserve(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // At the beginning of your upkeep, each player reveals the top card of his or her library. If all cards revealed this way are creature cards, put those cards onto the battlefield under their owners' control.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DuskmarEffect(), TargetController.YOU, false));
    }

    public GamePreserve(final GamePreserve card) {
        super(card);
    }

    @Override
    public GamePreserve copy() {
        return new GamePreserve(this);
    }
}

class DuskmarEffect extends OneShotEffect {

    public DuskmarEffect() {
        super(Outcome.Detriment);
        this.staticText = "each player reveals the top card of his or her library. If all cards revealed this way are creature cards, put those cards onto the battlefield under their owners' control";
    }

    public DuskmarEffect(final DuskmarEffect effect) {
        super(effect);
    }

    @Override
    public DuskmarEffect copy() {
        return new DuskmarEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourceCard = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (sourceCard == null) {
            return false;
        }
        boolean putToPlay = true;
        Cards cards = new CardsImpl();
        for (Player player : game.getPlayers().values()) {
            if (player.getLibrary().hasCards()) {
                Card card = player.getLibrary().removeFromTop(game);
                if (card != null) {
                    cards.add(card);
                    if (!card.isCreature()) {
                        putToPlay = false;
                    }
                    player.revealCards(sourceCard.getName() + ": Revealed by " + player.getName(), cards, game);
                }
            } else {
                putToPlay = false;
            }
        }
        if (putToPlay) {
            game.getPlayers().values().iterator().next().moveCards(cards.getCards(game), Zone.BATTLEFIELD, source, game, false, false, true, null);
        }
        return true;
    }
}
