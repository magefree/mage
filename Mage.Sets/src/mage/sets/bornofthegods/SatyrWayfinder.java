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
package mage.sets.bornofthegods;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author LevelX2
 */
public class SatyrWayfinder extends CardImpl {

    public SatyrWayfinder(UUID ownerId) {
        super(ownerId, 136, "Satyr Wayfinder", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.expansionSetCode = "BNG";
        this.subtype.add("Satyr");

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Satyr Wayfinder enters the battlefield, reveal the top four cards of your library. You may put a land card from among them into your hand. Put the rest into your graveyard.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SatyrWayfinderEffect()));
    }

    public SatyrWayfinder(final SatyrWayfinder card) {
        super(card);
    }

    @Override
    public SatyrWayfinder copy() {
        return new SatyrWayfinder(this);
    }
}

class SatyrWayfinderEffect extends OneShotEffect {

    private static final FilterLandCard filterPutInHand = new FilterLandCard("land card to put in hand");

    public SatyrWayfinderEffect() {
        super(Outcome.DrawCard);
        this.staticText = "reveal the top four cards of your library. You may put a land card from among them into your hand. Put the rest into your graveyard";
    }

    public SatyrWayfinderEffect(final SatyrWayfinderEffect effect) {
        super(effect);
    }

    @Override
    public SatyrWayfinderEffect copy() {
        return new SatyrWayfinderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            Cards cards = new CardsImpl();
            cards.addAll(controller.getLibrary().getTopCards(game, 4));
            boolean properCardFound = cards.count(filterPutInHand, source.getControllerId(), source.getSourceId(), game) > 0;
            if (!cards.isEmpty()) {
                controller.revealCards(sourceObject.getName(), cards, game);
                TargetCard target = new TargetCard(Zone.LIBRARY, filterPutInHand);
                if (properCardFound
                        && controller.chooseUse(outcome, "Put a land card into your hand?", source, game)
                        && controller.choose(Outcome.DrawCard, cards, target, game)) {
                    Card card = game.getCard(target.getFirstTarget());
                    if (card != null) {
                        cards.remove(card);
                        controller.moveCards(card, Zone.HAND, source, game);
                    }

                }
                controller.moveCards(cards, Zone.GRAVEYARD, source, game);
            }
            return true;
        }
        return false;
    }
}
