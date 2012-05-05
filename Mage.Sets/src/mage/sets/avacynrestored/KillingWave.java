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
package mage.sets.avacynrestored;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author North
 */
public class KillingWave extends CardImpl<KillingWave> {

    public KillingWave(UUID ownerId) {
        super(ownerId, 111, "Killing Wave", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{X}{B}");
        this.expansionSetCode = "AVR";

        this.color.setBlack(true);

        // For each creature, its controller sacrifices it unless he or she pays X life.
        this.getSpellAbility().addEffect(new KillingWaveEffect());
    }

    public KillingWave(final KillingWave card) {
        super(card);
    }

    @Override
    public KillingWave copy() {
        return new KillingWave(this);
    }
}

class KillingWaveEffect extends OneShotEffect<KillingWaveEffect> {

    public KillingWaveEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "For each creature, its controller sacrifices it unless he or she pays X life";
    }

    public KillingWaveEffect(final KillingWaveEffect effect) {
        super(effect);
    }

    @Override
    public KillingWaveEffect copy() {
        return new KillingWaveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        int amount = (new ManacostVariableValue()).calculate(game, source);
        if (amount > 0) {
            LinkedList<Permanent> sacrifices = new LinkedList<Permanent>();
            HashMap<UUID, Integer> lifePaidAmounts = new HashMap<UUID, Integer>();

            FilterCreaturePermanent filter = new FilterCreaturePermanent();
            for (UUID playerId : controller.getInRange()) {
                Player player = game.getPlayer(playerId);
                List<Permanent> creatures = game.getBattlefield().getAllActivePermanents(filter, playerId);

                int lifePaid = 0;
                int playerLife = player.getLife();
                for (Permanent creature : creatures) {
                    String message = "Pay " + amount + " life? If you don't, " + creature.getName() + " will be sacrificed.";
                    if (playerLife - amount - lifePaid >= 0 && player != null && player.chooseUse(Outcome.Neutral, message, game)) {
                        game.informPlayers(player.getName() + " pays " + amount + " life. He will not sacrifice " + creature.getName());
                        lifePaid += amount;
                    } else {
                        game.informPlayers(player.getName() + " will sacrifice " + creature.getName());
                        sacrifices.add(creature);
                    }
                }
                lifePaidAmounts.put(playerId, lifePaid);
            }

            for (UUID playerId : controller.getInRange()) {
                int lifePaid = lifePaidAmounts.get(playerId);
                if (lifePaid > 0) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        player.loseLife(lifePaid, game);
                    }
                }
            }

            for (Permanent creature : sacrifices) {
                creature.sacrifice(source.getId(), game);
            }
        }
        return true;
    }
}
