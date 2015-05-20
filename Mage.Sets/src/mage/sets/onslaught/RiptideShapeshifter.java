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
package mage.sets.onslaught;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
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
 * @author emerald000
 */
public class RiptideShapeshifter extends CardImpl {

    public RiptideShapeshifter(UUID ownerId) {
        super(ownerId, 109, "Riptide Shapeshifter", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.expansionSetCode = "ONS";
        this.subtype.add("Shapeshifter");

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {2}{U}{U}, Sacrifice Riptide Shapeshifter: Choose a creature type. Reveal cards from the top of your library until you reveal a creature card of that type. Put that card onto the battlefield and shuffle the rest into your library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RiptideShapeshifterEffect(), new ManaCostsImpl<>("{2}{U}{U}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    public RiptideShapeshifter(final RiptideShapeshifter card) {
        super(card);
    }

    @Override
    public RiptideShapeshifter copy() {
        return new RiptideShapeshifter(this);
    }
}

class RiptideShapeshifterEffect extends OneShotEffect {
    
    RiptideShapeshifterEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Choose a creature type. Reveal cards from the top of your library until you reveal a creature card of that type. Put that card onto the battlefield and shuffle the rest into your library";
    }
    
    RiptideShapeshifterEffect(final RiptideShapeshifterEffect effect) {
        super(effect);
    }
    
    @Override
    public RiptideShapeshifterEffect copy() {
        return new RiptideShapeshifterEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Choice choice = new ChoiceImpl(true);
            choice.setMessage("Choose a creature type:");
            choice.setChoices(CardRepository.instance.getCreatureTypes());
            while (!player.choose(Outcome.BoostCreature, choice, game)) {
                if (!player.isInGame()) {
                    return false;
                }
            }
            Cards revealedCards = new CardsImpl();
            while (player.getLibrary().size() > 0) {
                Card card = player.getLibrary().removeFromTop(game);
                if (card.getCardType().contains(CardType.CREATURE) && card.getSubtype().contains(choice.getChoice())) {
                    player.putOntoBattlefieldWithInfo(card, game, Zone.LIBRARY, source.getSourceId());
                    break;
                }
                revealedCards.add(card);
            }
            player.revealCards("Riptide Shapeshifter", revealedCards, game);
            for (Card card: revealedCards.getCards(game)) {
                card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
            }                           
            player.shuffleLibrary(game);
            return true;
        }
        return false;
    }
}
