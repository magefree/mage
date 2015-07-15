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
package mage.sets.conspiracy;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public class StrongholdDiscipline extends CardImpl {

    public StrongholdDiscipline(UUID ownerId) {
        super(ownerId, 126, "Stronghold Discipline", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{2}{B}{B}");
        this.expansionSetCode = "CNS";

        // Each player loses 1 life for each creature he or she controls.
        this.getSpellAbility().addEffect(new StrongholdDisciplineEffect());
    }

    public StrongholdDiscipline(final StrongholdDiscipline card) {
        super(card);
    }

    @Override
    public StrongholdDiscipline copy() {
        return new StrongholdDiscipline(this);
    }
}

class StrongholdDisciplineEffect extends OneShotEffect {

    StrongholdDisciplineEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "Each player loses 1 life for each creature he or she controls";
    }

    StrongholdDisciplineEffect(final StrongholdDisciplineEffect effect) {
        super(effect);
    }

    @Override
    public StrongholdDisciplineEffect copy() {
        return new StrongholdDisciplineEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : controller.getInRange()) {
                final int count = game.getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), playerId, game).size();
                if (count > 0) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        player.loseLife(count, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}