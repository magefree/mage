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
package mage.cards.w;

import java.util.UUID;
import mage.MageObject;
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
import mage.players.Player;
import mage.util.GameLog;

/**
 *
 * @author BetaSteward_at_googlemail.com and jeff
 */
public class WildEvocation extends CardImpl {

    public WildEvocation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{5}{R}");

        //At the beginning of each player's upkeep, that player reveals a card at random from his or her hand. If it's a land card, the player puts it onto the battlefield. Otherwise, the player casts it without paying its mana cost if able.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new WildEvocationEffect(), TargetController.ANY, false));
    }

    public WildEvocation(final WildEvocation card) {
        super(card);
    }

    @Override
    public WildEvocation copy() {
        return new WildEvocation(this);
    }

}

class WildEvocationEffect extends OneShotEffect {

    public WildEvocationEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "that player reveals a card at random from his or her hand. "
                + "If it's a land card, that player puts it onto the battlefield. "
                + "Otherwise, the player casts it without paying its mana cost if able";
    }

    public WildEvocationEffect(final WildEvocationEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        MageObject sourceObject = source.getSourceObject(game);
        if (player != null && sourceObject != null) {
            Card card = player.getHand().getRandom(game);
            if (card != null) {
                Cards cards = new CardsImpl();
                cards.add(card);
                player.revealCards(sourceObject.getIdName() + " Turn: " + game.getTurnNum(), cards, game);
                if (card.isLand()) {
                    player.moveCards(card, Zone.BATTLEFIELD, source, game);
                } else if (card.getSpellAbility() != null
                        && card.getSpellAbility().getTargets().canChoose(player.getId(), game)) {
                    player.cast(card.getSpellAbility(), game, true);
                } else {
                    game.informPlayers(GameLog.getColoredObjectName(card) + " can't be cast now by " + player.getLogName());
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public WildEvocationEffect copy() {
        return new WildEvocationEffect(this);
    }
}
