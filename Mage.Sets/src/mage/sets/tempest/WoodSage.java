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
package mage.sets.tempest;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class WoodSage extends CardImpl {

    public WoodSage(UUID ownerId) {
        super(ownerId, 350, "Wood Sage", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{G}{U}");
        this.expansionSetCode = "TMP";
        this.subtype.add("Human");
        this.subtype.add("Druid");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Name a creature card. Reveal the top four cards of your library and put all of them with that name into your hand. Put the rest into your graveyard.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new WoodSageEffect(), new TapSourceCost()));

    }

    public WoodSage(final WoodSage card) {
        super(card);
    }

    @Override
    public WoodSage copy() {
        return new WoodSage(this);
    }
}


class WoodSageEffect extends OneShotEffect {

    public WoodSageEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Name a creature card. Reveal the top four cards of your library and put all of them with that name into your hand. Put the rest into your graveyard";
    }

    public WoodSageEffect(final WoodSageEffect effect) {
        super(effect);
    }

    @Override
    public WoodSageEffect copy() {
        return new WoodSageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            Choice cardChoice = new ChoiceImpl();
            cardChoice.setChoices(CardRepository.instance.getCreatureNames());
            cardChoice.setMessage("Name a creature card");
            while (!controller.choose(Outcome.Detriment, cardChoice, game)) {
                if (!controller.isInGame()) {
                    return false;
                }
            }
            String cardName = cardChoice.getChoice();
            if (!game.isSimulation()) {
                game.informPlayers(sourceObject.getLogName() + ", named card: [" + cardName + "]");
            }

            Cards cards = new CardsImpl();
            cards.addAll(controller.getLibrary().getTopCards(game, 4));

            if (!cards.isEmpty()) {
                controller.revealCards(sourceObject.getLogName(), cards, game);
                for (Card card: cards.getCards(game)) {
                    if (card.getName().equals(cardName)) {
                        controller.moveCardToHandWithInfo(card, source.getSourceId(), game, Zone.LIBRARY, true);
                        cards.remove(card);
                    }
                }
                controller.moveCardsToGraveyardWithInfo(cards, source, game, Zone.LIBRARY);
            }
            return true;
        }

        return false;
    }
}
