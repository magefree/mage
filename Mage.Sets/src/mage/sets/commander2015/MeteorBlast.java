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
package mage.sets.commander2015;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author fireshoes
 */
public class MeteorBlast extends CardImpl {

    public MeteorBlast(UUID ownerId) {
        super(ownerId, 28, "Meteor Blast", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{X}{R}{R}{R}");
        this.expansionSetCode = "C15";

        // Meteor Blast deals 4 damage to each of X target creatures and/or players.
        this.getSpellAbility().addEffect(new MeteorBlastEffect());
    }

    public MeteorBlast(final MeteorBlast card) {
        super(card);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        int xValue = ability.getManaCostsToPay().getX();
        if (xValue > 0) {
            Target target = new TargetCreatureOrPlayer(xValue);
            ability.addTarget(target);
        }
    }

    @Override
    public MeteorBlast copy() {
        return new MeteorBlast(this);
    }
}

class MeteorBlastEffect extends OneShotEffect {

    public MeteorBlastEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals 4 damage to each of X target creatures and/or players";
    }

    public MeteorBlastEffect(final MeteorBlastEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (source.getTargets().size() > 0) {
                for (UUID targetId : this.getTargetPointer().getTargets(game, source)) {
                    Permanent creature = game.getPermanent(targetId);
                    if (creature != null) {
                        creature.damage(4, source.getSourceId(), game, false, true);
                    } else {
                        Player player = game.getPlayer(targetId);
                        if (player != null) {
                            player.damage(4, source.getSourceId(), game, false, true);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public MeteorBlastEffect copy() {
        return new MeteorBlastEffect(this);
    }
}
