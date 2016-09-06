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
package mage.sets.darksteel;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.AffinityForArtifactsAbility;
import mage.abilities.keyword.FlyingAbility;
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
public class ChromescaleDrake extends CardImpl {

    public ChromescaleDrake(UUID ownerId) {
        super(ownerId, 20, "Chromescale Drake", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{6}{U}{U}{U}");
        this.expansionSetCode = "DST";
        this.subtype.add("Drake");
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Affinity for artifacts
        this.addAbility(new AffinityForArtifactsAbility());
        
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // When Chromescale Drake enters the battlefield, reveal the top three cards of your library. Put all artifact cards revealed this way into your hand and the rest into your graveyard.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ChromescaleDrakeEffect()));
    }

    public ChromescaleDrake(final ChromescaleDrake card) {
        super(card);
    }

    @Override
    public ChromescaleDrake copy() {
        return new ChromescaleDrake(this);
    }
}

class ChromescaleDrakeEffect extends OneShotEffect {

    public ChromescaleDrakeEffect() {
        super(Outcome.Benefit);
        staticText = "Reveal the top three cards of your library. Put all artifacts cards revealed this way into your hand and the rest into your graveyard";
    }

    public ChromescaleDrakeEffect(final ChromescaleDrakeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller == null || sourceObject ==  null) {
            return false;
        }

        Cards cards = new CardsImpl();
        Cards cardsToHand = new CardsImpl();
        cards.addAll(controller.getLibrary().getTopCards(game, 3));
        if (!cards.isEmpty()) {
            controller.revealCards(sourceObject.getName(), cards, game);
            for (Card card: cards.getCards(game)) {
                if (card.getCardType().contains(CardType.ARTIFACT)) {
                    cardsToHand.add(card);
                    cards.remove(card);
                }
            }
            controller.moveCards(cardsToHand, Zone.HAND, source, game);
            controller.moveCards(cards, Zone.GRAVEYARD, source, game);
        }
        return true;
    }

    @Override
    public ChromescaleDrakeEffect copy() {
        return new ChromescaleDrakeEffect(this);
    }
}
