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
package mage.cards.w;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ShuffleHandIntoLibraryDrawThatManySourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class WhirlpoolWarrior extends CardImpl {

    public WhirlpoolWarrior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Whirlpool Warrior enters the battlefield, shuffle the cards from your hand into your library, then draw that many cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ShuffleHandIntoLibraryDrawThatManySourceEffect()));

        // {R}, Sacrifice Whirlpool Warrior: Each player shuffles the cards from his or her hand into his or her library, then draws that many cards.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new WhirlpoolWarriorActivatedEffect(), new ManaCostsImpl("{R}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    public WhirlpoolWarrior(final WhirlpoolWarrior card) {
        super(card);
    }

    @Override
    public WhirlpoolWarrior copy() {
        return new WhirlpoolWarrior(this);
    }
}

class WhirlpoolWarriorActivatedEffect extends OneShotEffect {

    public WhirlpoolWarriorActivatedEffect() {
        super(Outcome.Benefit);
        this.staticText = "Each player shuffles the cards from his or her hand into his or her library, then draws that many cards";
    }

    public WhirlpoolWarriorActivatedEffect(final WhirlpoolWarriorActivatedEffect effect) {
        super(effect);
    }

    @Override
    public WhirlpoolWarriorActivatedEffect copy() {
        return new WhirlpoolWarriorActivatedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Map<UUID, Integer> playerCards = new LinkedHashMap<>();
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    int cardsHand = player.getHand().size();
                    if (cardsHand > 0) {
                        playerCards.put(playerId, cardsHand);
                        player.moveCards(player.getHand(), Zone.LIBRARY, source, game);
                        player.shuffleLibrary(source, game);
                    }
                }
            }
            for (Entry<UUID, Integer> entry : playerCards.entrySet()) {
                Player player = game.getPlayer(entry.getKey());
                if (player != null) {
                    player.drawCards(entry.getValue(), game);
                }
            }
            return true;
        }
        return false;
    }
}
