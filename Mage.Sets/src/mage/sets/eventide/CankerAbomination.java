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
package mage.sets.eventide;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetOpponent;

/**
 *
 * @author jeffwadsworth
 *
 */
public class CankerAbomination extends CardImpl<CankerAbomination> {

    public CankerAbomination(UUID ownerId) {
        super(ownerId, 115, "Canker Abomination", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{B/G}{B/G}");
        this.expansionSetCode = "EVE";
        this.subtype.add("Treefolk");
        this.subtype.add("Horror");

        this.color.setGreen(true);
        this.color.setBlack(true);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // As Canker Abomination enters the battlefield, choose an opponent. Canker Abomination enters the battlefield with a -1/-1 counter on it for each creature that player controls.
        Ability ability = new AsEntersBattlefieldAbility(new CankerAbominationEffect());
        Target target = new TargetOpponent(true);
        target.setNotTarget(true);
        ability.addTarget(target);
        this.addAbility(ability);

    }

    public CankerAbomination(final CankerAbomination card) {
        super(card);
    }

    @Override
    public CankerAbomination copy() {
        return new CankerAbomination(this);
    }
}

class CankerAbominationEffect extends OneShotEffect<CankerAbominationEffect> {

    public CankerAbominationEffect() {
        super(Outcome.Neutral);
        this.staticText = "choose an opponent. {this} enters the battlefield with a -1/-1 counter on it for each creature that player controls";
    }

    public CankerAbominationEffect(final CankerAbominationEffect effect) {
        super(effect);
    }

    @Override
    public CankerAbominationEffect copy() {
        return new CankerAbominationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent CankerAbomination = game.getPermanent(source.getSourceId());
        if (player != null && CankerAbomination != null) {
            Player chosenPlayer = game.getPlayer(source.getFirstTarget());
            if (chosenPlayer != null) {
                game.informPlayers(CankerAbomination.getName() + ": " + player.getName() + " has chosen " + chosenPlayer.getName());
                int amount = game.getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), chosenPlayer.getId(), game).size();
                CankerAbomination.addCounters(CounterType.M1M1.createInstance(amount), game);
                return true;
            }
        }
        return false;
    }
}
