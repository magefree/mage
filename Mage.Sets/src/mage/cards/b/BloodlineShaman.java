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
package mage.cards.b;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public class BloodlineShaman extends CardImpl {

    public BloodlineShaman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add("Elf");
        this.subtype.add("Wizard");
        this.subtype.add("Shaman");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Choose a creature type. Reveal the top card of your library. If that card is a creature card of the chosen type, put it into your hand.
        // Otherwise, put it into your graveyard.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BloodlineShamanEffect(), new TapSourceCost()));
    }

    public BloodlineShaman(final BloodlineShaman card) {
        super(card);
    }

    @Override
    public BloodlineShaman copy() {
        return new BloodlineShaman(this);
    }
}

class BloodlineShamanEffect extends OneShotEffect {

    public BloodlineShamanEffect() {
        super(Outcome.Benefit);
        this.staticText = "Choose a creature type. Reveal the top card of your library. If that card is a creature card of the chosen type, put it into your hand. "
                + "Otherwise, put it into your graveyard";
    }

    public BloodlineShamanEffect(final BloodlineShamanEffect effect) {
        super(effect);
    }

    @Override
    public BloodlineShamanEffect copy() {
        return new BloodlineShamanEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null) {
            // Choose a creature type.
            Choice typeChoice = new ChoiceImpl(true);
            typeChoice.setMessage("Choose a creature type:");
            typeChoice.setChoices(CardRepository.instance.getCreatureTypes());
            while (!controller.choose(outcome, typeChoice, game)) {
                if (!controller.canRespond()) {
                    return false;
                }
            }
            if (typeChoice.getChoice() != null) {
                game.informPlayers(sourceObject.getLogName() + " chosen type: " + typeChoice.getChoice());
            }

            FilterCard filterSubtype = new FilterCard();
            filterSubtype.add(new SubtypePredicate(typeChoice.getChoice()));

            // Reveal the top card of your library.
            if (controller.getLibrary().hasCards()) {
            Card card = controller.getLibrary().getFromTop(game);
            Cards cards = new CardsImpl(card);
            controller.revealCards(sourceObject.getIdName(), cards, game);

            if (card != null) {
            // If that card is a creature card of the chosen type, put it into your hand.
                if (filterSubtype.match(card, game)) {
                    controller.moveCards(card, Zone.HAND, source, game);
            // Otherwise, put it into your graveyard.
                } else {
                    controller.moveCards(card, Zone.GRAVEYARD, source, game);
                }
            }
        }
            return true;
        }
        return false;
    }
}
