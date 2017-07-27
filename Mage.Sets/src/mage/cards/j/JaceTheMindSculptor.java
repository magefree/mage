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
package mage.cards.j;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.BrainstormEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class JaceTheMindSculptor extends CardImpl {

    public JaceTheMindSculptor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.PLANESWALKER},"{2}{U}{U}");
        this.subtype.add("Jace");

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(3));

        // +2: Look at the top card of target player's library. You may put that card on the bottom of that player's library.
        LoyaltyAbility ability1 = new LoyaltyAbility(new JaceTheMindSculptorEffect1(), 2);
        ability1.addTarget(new TargetPlayer());
        this.addAbility(ability1);

        // 0: Draw three cards, then put two cards from your hand on top of your library in any order.
        LoyaltyAbility ability2 = new LoyaltyAbility(new BrainstormEffect(), 0);
        this.addAbility(ability2);

        // −1: Return target creature to its owner's hand.
        LoyaltyAbility ability3 = new LoyaltyAbility(new ReturnToHandTargetEffect(), -1);
        ability3.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability3);

        // −12: Exile all cards from target player's library, then that player shuffles his or her hand into his or her library.
        LoyaltyAbility ability4 = new LoyaltyAbility(new JaceTheMindSculptorEffect2(), -12);
        ability4.addTarget(new TargetPlayer());
        this.addAbility(ability4);

    }

    public JaceTheMindSculptor(final JaceTheMindSculptor card) {
        super(card);
    }

    @Override
    public JaceTheMindSculptor copy() {
        return new JaceTheMindSculptor(this);
    }

}

class JaceTheMindSculptorEffect1 extends OneShotEffect {

    public JaceTheMindSculptorEffect1() {
        super(Outcome.Detriment);
        staticText = "Look at the top card of target player's library. You may put that card on the bottom of that player's library";
    }

    public JaceTheMindSculptorEffect1(final JaceTheMindSculptorEffect1 effect) {
        super(effect);
    }

    @Override
    public JaceTheMindSculptorEffect1 copy() {
        return new JaceTheMindSculptorEffect1(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(source.getFirstTarget());
        if (controller != null && player != null) {
            Card card = player.getLibrary().getFromTop(game);
            if (card != null) {
                Cards cards = new CardsImpl();
                cards.add(card);
                controller.lookAtCards("Jace, the Mind Sculptor", cards, game);
                if (controller.chooseUse(outcome, "Do you wish to put card on the bottom of player's library?", source, game)) {
                    controller.moveCardToLibraryWithInfo(card, source.getSourceId(), game, Zone.LIBRARY, false, false);
                } else {
                    game.informPlayers(controller.getLogName() + " puts the card back on top of the library.");
                }
                return true;
            }
        }
        return false;
    }

}

class JaceTheMindSculptorEffect2 extends OneShotEffect {

    public JaceTheMindSculptorEffect2() {
        super(Outcome.DrawCard);
        staticText = "Exile all cards from target player's library, then that player shuffles his or her hand into his or her library";
    }

    public JaceTheMindSculptorEffect2(final JaceTheMindSculptorEffect2 effect) {
        super(effect);
    }

    @Override
    public JaceTheMindSculptorEffect2 copy() {
        return new JaceTheMindSculptorEffect2(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            while (true) {
                if (player.getLibrary().getFromTop(game) == null) {
                    break;
                }
                Card card = player.getLibrary().removeFromTop(game);
                if (card != null) {
                    card.moveToExile(null, "", source.getSourceId(), game);
                }
            }
            for (Card card : player.getHand().getCards(game)) {
                card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, false);
            }
            player.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }

}
