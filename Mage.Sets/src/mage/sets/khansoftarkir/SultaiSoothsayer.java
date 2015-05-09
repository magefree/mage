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
package mage.sets.khansoftarkir;

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
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author LevelX2
 */
public class SultaiSoothsayer extends CardImpl {

    public SultaiSoothsayer(UUID ownerId) {
        super(ownerId, 205, "Sultai Soothsayer", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{B}{G}{U}");
        this.expansionSetCode = "KTK";
        this.subtype.add("Naga");
        this.subtype.add("Shaman");

        this.color.setBlue(true);
        this.color.setGreen(true);
        this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // When Sultai Soothsayer enters the battlefield, look at the top four cards of your library. Put one of them into your hand and the rest into your graveyard.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SultaiSoothsayerEffect(), false));
    }

    public SultaiSoothsayer(final SultaiSoothsayer card) {
        super(card);
    }

    @Override
    public SultaiSoothsayer copy() {
        return new SultaiSoothsayer(this);
    }
}

class SultaiSoothsayerEffect extends OneShotEffect {

    public SultaiSoothsayerEffect() {
        super(Outcome.DrawCard);
        this.staticText = "look at the top four cards of your library. Put one of them into your hand and the rest into your graveyard";
    }

    public SultaiSoothsayerEffect(final SultaiSoothsayerEffect effect) {
        super(effect);
    }

    @Override
    public SultaiSoothsayerEffect copy() {
        return new SultaiSoothsayerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            Cards cards = new CardsImpl();
            cards.addAll(controller.getLibrary().getTopCards(game, 4));

            if (cards.size() > 0) {
                controller.lookAtCards(sourceObject.getName(), cards, game);

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
