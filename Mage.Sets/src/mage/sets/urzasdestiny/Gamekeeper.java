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
package mage.sets.urzasdestiny;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public class Gamekeeper extends CardImpl {

    public Gamekeeper(UUID ownerId) {
        super(ownerId, 106, "Gamekeeper", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.expansionSetCode = "UDS";
        this.subtype.add("Elf");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Gamekeeper dies, you may exile it. If you do, reveal cards from the top of your library until you reveal a creature card. Put that card onto the battlefield and put all other cards revealed this way into your graveyard.
        Ability ability = new DiesTriggeredAbility(new DoIfCostPaid(new GamekeeperEffect(), new ExileSourceFromGraveCost(), "Exile to reveal cards from the top of your library until you reveal a creature card?"), false);
        this.addAbility(ability);
    }

    public Gamekeeper(final Gamekeeper card) {
        super(card);
    }

    @Override
    public Gamekeeper copy() {
        return new Gamekeeper(this);
    }
}

class GamekeeperEffect extends OneShotEffect {

    public GamekeeperEffect() {
        super(Outcome.Benefit);
        staticText = "reveal cards from the top of your library until you reveal a creature card. Put that card onto the battlefield and put all other cards revealed this way into your graveyard";
    }

    public GamekeeperEffect(final GamekeeperEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            new ExileSourceEffect().apply(game, source);
            Cards revealedCards = new CardsImpl();
            while (controller.getLibrary().size() > 0) {
                Card card = controller.getLibrary().removeFromTop(game);
                if (card.getCardType().contains(CardType.CREATURE)) {
                    controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                    break;
                }
                revealedCards.add(card);
            }
            controller.revealCards(sourceObject.getIdName(), revealedCards, game);
            controller.moveCards(revealedCards, Zone.GRAVEYARD, source, game);
            return true;
        }
        return false;
    }

    @Override
    public GamekeeperEffect copy() {
        return new GamekeeperEffect(this);
    }
}
