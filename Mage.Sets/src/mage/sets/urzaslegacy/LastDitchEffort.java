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
package mage.sets.urzaslegacy;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author BursegSardaukar
 */
public class LastDitchEffort extends CardImpl {

    public LastDitchEffort(UUID ownerId) {
        super(ownerId, 83, "Last-Ditch Effort", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{R}");
        this.expansionSetCode = "ULG";

        // Sacrifice any number of creatures. Last-Ditch Effort deals that much damage to target creature or player.
        this.getSpellAbility().addEffect(new LastDitchEffortEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlayer());
    }

    public LastDitchEffort(final LastDitchEffort card) {
        super(card);
    }

    @Override
    public LastDitchEffort copy() {
        return new LastDitchEffort(this);
    }
}

class LastDitchEffortEffect extends OneShotEffect {

    LastDitchEffortEffect() {
        super(Outcome.Damage);
        this.staticText = "Sacrifice any number of creatures. {this} deals that much damage to target creature or player";
    }

    LastDitchEffortEffect(final LastDitchEffortEffect effect) {
        super(effect);
    }

    @Override
    public LastDitchEffortEffect copy() {
        return new LastDitchEffortEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Target target = new TargetControlledCreaturePermanent(0, Integer.MAX_VALUE, new FilterControlledCreaturePermanent(), true);
            player.chooseTarget(Outcome.Sacrifice, target, source, game);
            int numSacrificed = 0;
            for (UUID permanentId : target.getTargets()) {
                Permanent permanent = game.getPermanent(permanentId);
                if (permanent != null) {
                    if (permanent.sacrifice(source.getSourceId(), game)) {
                        numSacrificed++;
                    }
                }
            }
            if (numSacrificed > 0) {
                int damage = numSacrificed;
                UUID uuid = this.getTargetPointer().getFirst(game, source);
                Permanent permanent = game.getPermanent(uuid);
                Player opponent = game.getPlayer(uuid);
                if (permanent != null) {
                    permanent.damage(damage, source.getSourceId(), game, false, true);
                }
                if (opponent != null) {
                    opponent.damage(damage, source.getSourceId(), game, false, true);
                }
            }
            return true;
        }
        return false;
    }
}
