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
package mage.sets.shadowmoor;

import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public class ThoughtweftGambit extends CardImpl {

    public ThoughtweftGambit(UUID ownerId) {
        super(ownerId, 154, "Thoughtweft Gambit", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{4}{W/U}{W/U}");
        this.expansionSetCode = "SHM";


        // Tap all creatures your opponents control and untap all creatures you control.
        this.getSpellAbility().addEffect(new ThoughtweftGambitEffect());

    }

    public ThoughtweftGambit(final ThoughtweftGambit card) {
        super(card);
    }

    @Override
    public ThoughtweftGambit copy() {
        return new ThoughtweftGambit(this);
    }
}

class ThoughtweftGambitEffect extends OneShotEffect {

    private static FilterCreaturePermanent filter = new FilterCreaturePermanent();

    public ThoughtweftGambitEffect() {
        super(Outcome.Benefit);
        staticText = "Tap all creatures your opponents control and untap all creatures you control";
    }

    public ThoughtweftGambitEffect(final ThoughtweftGambitEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Boolean passed = false;
        Set<UUID> opponents = game.getOpponents(source.getControllerId());
        Player controller = game.getPlayer(source.getControllerId());
        if (opponents != null) {
            for (Permanent creature : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
                if (opponents.contains(creature.getControllerId())) {
                    creature.tap(game);
                }
            }
            passed = true;
        }
        if (controller != null) {
            for (Permanent creature : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
                if (controller.getId() == creature.getControllerId()) {
                    creature.untap(game);
                }
            }
            passed = true;
        }
        return passed;
    }

    @Override
    public ThoughtweftGambitEffect copy() {
        return new ThoughtweftGambitEffect(this);
    }
}
