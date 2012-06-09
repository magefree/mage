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

package mage.sets.worldwake;

import java.util.UUID;

import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.counters.CounterType;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class JaceTheMindSculptor extends CardImpl<JaceTheMindSculptor> {


    public JaceTheMindSculptor(UUID ownerId) {
        super(ownerId, 31, "Jace, the Mind Sculptor", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{2}{U}{U}");
        this.expansionSetCode = "WWK";
        this.subtype.add("Jace");
        this.color.setBlue(true);
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(3)), ""));


        LoyaltyAbility ability1 = new LoyaltyAbility(new JaceTheMindSculptorEffect1(), 2);
        ability1.addTarget(new TargetPlayer());
        this.addAbility(ability1);

        LoyaltyAbility ability2 = new LoyaltyAbility(new JaceTheMindSculptorEffect2(), 0);
        this.addAbility(ability2);

        LoyaltyAbility ability3 = new LoyaltyAbility(new ReturnToHandTargetEffect(), -1);
        ability3.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability3);

        LoyaltyAbility ability4 = new LoyaltyAbility(new JaceTheMindSculptorEffect3(), -12);
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

class JaceTheMindSculptorEffect1 extends OneShotEffect<JaceTheMindSculptorEffect1> {

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
            Card c = player.getLibrary().getFromTop(game);
            if (c != null) {
                Cards cards = new CardsImpl();
                cards.add(c);
                controller.lookAtCards("Jace, the Mind Sculptor", cards, game);
                if (controller.chooseUse(outcome, "Do you wish to put card on the bottom of player's library?", game)) {
                    Card card = player.getLibrary().removeFromTop(game);
                    if (card != null) {
                        card.moveToZone(Zone.LIBRARY, source.getId(), game, false);
                    }
                }
                return true;
            }
        }
        return false;
    }

}

class JaceTheMindSculptorEffect2 extends OneShotEffect<JaceTheMindSculptorEffect2> {

    public JaceTheMindSculptorEffect2() {
        super(Outcome.DrawCard);
        staticText = "Draw three cards, then put two cards from your hand on top of your library in any order";
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
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.drawCards(3, game);
            putOnLibrary(player, source, game);
            putOnLibrary(player, source, game);
            return true;
        }
        return false;
    }

    private boolean putOnLibrary(Player player, Ability source, Game game) {
        TargetCardInHand target = new TargetCardInHand();
        target.setRequired(true);
        player.chooseTarget(Outcome.ReturnToHand, target, source, game);
        Card card = player.getHand().get(target.getFirstTarget(), game);
        if (card != null) {
            player.getHand().remove(card);
            card.moveToZone(Zone.LIBRARY, source.getId(), game, true);
        }
        return true;
    }

}

class JaceTheMindSculptorEffect3 extends OneShotEffect<JaceTheMindSculptorEffect3> {

    public JaceTheMindSculptorEffect3() {
        super(Outcome.DrawCard);
        staticText = "Exile all cards from target player's library, then that player shuffles his or her hand into his or her library";
    }

    public JaceTheMindSculptorEffect3(final JaceTheMindSculptorEffect3 effect) {
        super(effect);
    }

    @Override
    public JaceTheMindSculptorEffect3 copy() {
        return new JaceTheMindSculptorEffect3(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        ExileZone exile = game.getExile().getPermanentExile();
        if (player != null) {
            while (true) {
                if (player.getLibrary().getFromTop(game) == null)
                    break;
                Card card = player.getLibrary().removeFromTop(game);
                exile.add(card);
                game.setZone(card.getId(), Zone.EXILED);
            }
            for (Card card : player.getHand().getCards(game)) {
                card.moveToZone(Zone.LIBRARY, source.getId(), game, false);
            }
            return true;
        }
        return false;
    }

}