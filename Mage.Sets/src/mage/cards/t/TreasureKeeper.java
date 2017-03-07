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
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public class TreasureKeeper extends CardImpl {

    public TreasureKeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.subtype.add("Construct");
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Treasure Keeper dies, reveal cards from the top of your library until you reveal a nonland card with converted mana cost 3 or less.
        // You may cast that card without paying its mana cost. Put all revealed cards not cast this way on the bottom of your library in a random order.
        this.addAbility(new DiesTriggeredAbility(new TreasureKeeperEffect()));
    }

    public TreasureKeeper(final TreasureKeeper card) {
        super(card);
    }

    @Override
    public TreasureKeeper copy() {
        return new TreasureKeeper(this);
    }
}

class TreasureKeeperEffect extends OneShotEffect {

    public TreasureKeeperEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "reveal cards from the top of your library until you reveal a nonland card with converted mana cost 3 or less. "
                + "You may cast that card without paying its mana cost. Put all revealed cards not cast this way on the bottom of your library in a random order";
    }

    public TreasureKeeperEffect(TreasureKeeperEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CardsImpl toReveal = new CardsImpl();
        Card nonLandCard = null;
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            while (nonLandCard == null && controller.getLibrary().hasCards()) {
                Card card = controller.getLibrary().removeFromTop(game);
                toReveal.add(card);
                if (!card.isLand() && card.getConvertedManaCost() < 4) {
                    nonLandCard = card;
                }
            }
            // reveal cards
            if (!toReveal.isEmpty()) {
                controller.revealCards(sourceObject.getIdName(), toReveal, game);
            }
            if (nonLandCard != null && controller.chooseUse(outcome, "Cast " + nonLandCard.getLogName() + "without paying its mana cost?", source, game)) {
                controller.cast(nonLandCard.getSpellAbility(), game, true);
                toReveal.remove(nonLandCard);
            }
            controller.putCardsOnBottomOfLibrary(toReveal, game, source, false);
            return true;
        }
        return false;
    }

    @Override
    public TreasureKeeperEffect copy() {
        return new TreasureKeeperEffect(this);
    }
}
