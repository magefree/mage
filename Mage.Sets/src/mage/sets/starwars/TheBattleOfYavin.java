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
package mage.sets.starwars;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterNonlandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Styxo
 */
public class TheBattleOfYavin extends CardImpl {

    public TheBattleOfYavin(UUID ownerId) {
        super(ownerId, 66, "The Battle of Yavin", Rarity.MYTHIC, new CardType[]{CardType.SORCERY}, "{X}{B}{B}");
        this.expansionSetCode = "SWS";

        // For each nonland permanent target opponent controls, that player sacrificies it unless he or she pays X life.
        this.getSpellAbility().addEffect(new TheBattleOfYavinEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());

    }

    public TheBattleOfYavin(final TheBattleOfYavin card) {
        super(card);
    }

    @Override
    public TheBattleOfYavin copy() {
        return new TheBattleOfYavin(this);
    }
}

class TheBattleOfYavinEffect extends OneShotEffect {

    public TheBattleOfYavinEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "For each nonland permanent target opponent controls, that player sacrificies it unless he or she pays X life";
    }

    public TheBattleOfYavinEffect(final TheBattleOfYavinEffect effect) {
        super(effect);
    }

    @Override
    public TheBattleOfYavinEffect copy() {
        return new TheBattleOfYavinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(source.getTargets().getFirstTarget());
        if (opponent == null) {
            return false;
        }

        int amount = (new ManacostVariableValue()).calculate(game, source, this);
        if (amount > 0) {
            LinkedList<Permanent> sacrifices = new LinkedList<Permanent>();

            FilterNonlandPermanent filter = new FilterNonlandPermanent();
            List<Permanent> permanents = game.getBattlefield().getAllActivePermanents(filter, opponent.getId(), game);

            int lifePaid = 0;
            int playerLife = opponent.getLife();
            for (Permanent permanent : permanents) {
                String message = "Pay " + amount + " life? If you don't, " + permanent.getName() + " will be sacrificed.";
                if (playerLife - amount - lifePaid >= 0 && opponent != null && opponent.chooseUse(Outcome.Neutral, message, source, game)) {
                    game.informPlayers(opponent.getLogName() + " pays " + amount + " life. He will not sacrifice " + permanent.getName());
                    lifePaid += amount;
                } else {
                    game.informPlayers(opponent.getLogName() + " will sacrifice " + permanent.getName());
                    sacrifices.add(permanent);
                }
            }

            if (lifePaid > 0) {
                Player player = game.getPlayer(opponent.getId());
                if (player != null) {
                    player.loseLife(lifePaid, game);
                }
            }

            for (Permanent permanent : sacrifices) {
                permanent.sacrifice(source.getSourceId(), game);
            }
        }
        return true;
    }
}
