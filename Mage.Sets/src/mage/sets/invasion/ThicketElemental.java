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
package mage.sets.invasion;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.KickerAbility;
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
public class ThicketElemental extends CardImpl {

    public ThicketElemental(UUID ownerId) {
        super(ownerId, 214, "Thicket Elemental", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");
        this.expansionSetCode = "INV";
        this.subtype.add("Elemental");
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Kicker {1}{G}
        this.addAbility(new KickerAbility("{1}{G}"));

        // When Thicket Elemental enters the battlefield, if it was kicked, you may reveal cards from the top of your library until you reveal a creature card. If you do, put that card onto the battlefield and shuffle all other cards revealed this way into your library.
        TriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new ThicketElementalEffect());
        this.addAbility(new ConditionalTriggeredAbility(ability, KickedCondition.getInstance(),
            "When {this} enters the battlefield, if it was kicked, you may reveal cards from the top of your library until you reveal a creature card. If you do, put that card onto the battlefield and shuffle all other cards revealed this way into your library."));
    }

    public ThicketElemental(final ThicketElemental card) {
        super(card);
    }

    @Override
    public ThicketElemental copy() {
        return new ThicketElemental(this);
    }
}

class ThicketElementalEffect extends OneShotEffect {



    public ThicketElementalEffect() {
        super(Outcome.Benefit);
        staticText = "if {this} was kicked, reveal cards from the top of your library until you reveal a creature card. Put that card onto the battlefield and shuffle all other cards revealed this way into your library";
    }

    public ThicketElementalEffect(final ThicketElementalEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            Cards revealedCards = new CardsImpl();
            while (controller.getLibrary().size() > 0) {
                Card card = controller.getLibrary().removeFromTop(game);
                if (card.getCardType().contains(CardType.CREATURE)) {
                    controller.putOntoBattlefieldWithInfo(card, game, Zone.LIBRARY, source.getSourceId());
                    break;
                }
                revealedCards.add(card);
            }
            controller.revealCards("ThicketElemental", revealedCards, game);
            for (Card card: revealedCards.getCards(game)) {
                card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
            }
            controller.shuffleLibrary(game);
            return true;
        }
        return false;
    }

    @Override
    public ThicketElementalEffect copy() {
        return new ThicketElementalEffect(this);
    }
}