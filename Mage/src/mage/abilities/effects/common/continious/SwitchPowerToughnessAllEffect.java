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

package mage.abilities.effects.common.continious;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */

public class SwitchPowerToughnessAllEffect extends ContinuousEffectImpl<SwitchPowerToughnessAllEffect> {

    static FilterCreaturePermanent filter = new FilterCreaturePermanent();

    public SwitchPowerToughnessAllEffect(Duration duration) {
        super(duration, Layer.PTChangingEffects_7, SubLayer.SwitchPT_e, Outcome.BoostCreature);
    }

    public SwitchPowerToughnessAllEffect(final SwitchPowerToughnessAllEffect effect) {
        super(effect);
    }

    @Override
    public SwitchPowerToughnessAllEffect copy() {
        return new SwitchPowerToughnessAllEffect(this);
    }
    
    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (this.affectedObjectsSet) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                for (Permanent creature :game.getState().getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
                    this.objects.add(creature.getId());
                }
            }            
        }
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (this.affectedObjectsSet) {
                Set<UUID> toDelete = new HashSet<>();
                for (UUID creatureId: objects) {
                    Permanent creature = game.getPermanent(creatureId);
                    if (creature != null) {
                        int power = creature.getPower().getValue();
                        creature.getPower().setValue(creature.getToughness().getValue());
                        creature.getToughness().setValue(power);                                            
                    } else {
                        toDelete.add(creatureId);                        
                    }                    
                }
                this.objects.removeAll(toDelete);
            } else {
                for (Permanent creature :game.getState().getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
                    int power = creature.getPower().getValue();
                    creature.getPower().setValue(creature.getToughness().getValue());
                    creature.getToughness().setValue(power);                    
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        StringBuilder sb = new StringBuilder();
        sb.append("Switch each creature's power and toughness");
        if (!duration.toString().isEmpty()) {
            sb.append(" ");
            sb.append(duration.toString());
        }
        return sb.toString();
    }

}
