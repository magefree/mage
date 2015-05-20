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
package mage.sets.commander2014;

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
 * @author LevelX2
 */
public class InciteRebellion extends CardImpl {

    public InciteRebellion(UUID ownerId) {
        super(ownerId, 37, "Incite Rebellion", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{4}{R}{R}");
        this.expansionSetCode = "C14";


        // For each player, Incite Rebellion deals damage to that player and each creature that player controls equal to the number of creatures he or she controls.
        this.getSpellAbility().addEffect(new InciteRebellionEffect());
    }

    public InciteRebellion(final InciteRebellion card) {
        super(card);
    }

    @Override
    public InciteRebellion copy() {
        return new InciteRebellion(this);
    }
}

class InciteRebellionEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    public InciteRebellionEffect() {
        super(Outcome.Detriment);
        this.staticText = "For each player, {this} deals damage to that player and each creature that player controls equal to the number of creatures he or she controls";
    }

    public InciteRebellionEffect(final InciteRebellionEffect effect) {
        super(effect);
    }

    @Override
    public InciteRebellionEffect copy() {
        return new InciteRebellionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId: controller.getInRange()) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    int count = game.getBattlefield().countAll(filter, playerId, game);
                    if (count > 0) {
                        player.damage(count, source.getSourceId(), game, false, true);
                        for (Permanent permanent: game.getBattlefield().getAllActivePermanents(filter, playerId, game)) {
                            permanent.damage(count, source.getSourceId(), game, false, true);
                        }
                   }
                }
            }
            return true;
        }
        return false;
    }
}
