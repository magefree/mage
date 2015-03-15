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
package mage.sets.dragonsoftarkir;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.ExploitCreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ExploitAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author fireshoes
 */
public class GurmagDrowner extends CardImpl {

    public GurmagDrowner(UUID ownerId) {
        super(ownerId, 57, "Gurmag Drowner", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.expansionSetCode = "DTK";
        this.subtype.add("Naga");
        this.subtype.add("Wizard");
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Exploit
        this.addAbility(new ExploitAbility());
        
        // When Gurmag Drowner exploits a creature, look at the top four cards of your library. Put one of them into your hand and the rest into your graveyard.
        this.addAbility(new ExploitCreatureTriggeredAbility(new GurmagDrownerEffect(), false));
    }

    public GurmagDrowner(final GurmagDrowner card) {
        super(card);
    }

    @Override
    public GurmagDrowner copy() {
        return new GurmagDrowner(this);
    }
}

class GurmagDrownerEffect extends OneShotEffect {

    public GurmagDrownerEffect() {
        super(Outcome.DrawCard);
        this.staticText = "look at the top four cards of your library. Put one of them into your hand and the rest into your graveyard";
    }

    public GurmagDrownerEffect(final GurmagDrownerEffect effect) {
        super(effect);
    }

    @Override
    public GurmagDrownerEffect copy() {
        return new GurmagDrownerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            Cards cards = new CardsImpl();
            cards.addAll(controller.getLibrary().getTopCards(game, 4));

            if (cards.size() > 0) {
                controller.lookAtCards(sourceObject.getLogName(), cards, game);

                TargetCard target = new TargetCard(Zone.LIBRARY, new FilterCard("card to put in your hand"));
                if (controller.choose(Outcome.Benefit, cards, target, game)) {
                    Card card = cards.get(target.getFirstTarget(), game);
                    if (card != null) {
                        card.moveToZone(Zone.HAND, source.getSourceId(), game, false);
                        cards.remove(card);
                    }
                }

                for (Card card : cards.getCards(game)) {
                    controller.moveCardToGraveyardWithInfo(card, source.getSourceId(), game, Zone.LIBRARY);
                }
            }
            return true;
        }
        return false;
    }
}
