/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.abilities.effects.common;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import mage.constants.Outcome;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.Duration;
import mage.constants.PhaseStep;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.turn.Step;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */

public class DetainAllEffect extends OneShotEffect<DetainAllEffect> {

    private FilterPermanent filter = new FilterPermanent();

    public DetainAllEffect(FilterPermanent filter) {
        super(Outcome.Benefit);
        this.filter = filter;
        this.staticText = new StringBuilder("detain ").append(filter.getMessage()).toString();
    }

    public DetainAllEffect(final DetainAllEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public DetainAllEffect copy() {
        return new DetainAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<FixedTarget> detainedObjects = new ArrayList<>();
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
            game.informPlayers("Detained permanent: " + permanent.getName());
            FixedTarget fixedTarget = new FixedTarget(permanent.getId());
            fixedTarget.init(game, source);
            detainedObjects.add(fixedTarget);
        }

        game.addEffect(new DetainAllRestrictionEffect(detainedObjects), source);
        return false;
    }
}

class DetainAllRestrictionEffect extends RestrictionEffect<DetainAllRestrictionEffect> {

    private final List<FixedTarget> detainedObjects;

    public DetainAllRestrictionEffect(List<FixedTarget> detainedObjects) {
        super(Duration.Custom);
        this.detainedObjects = detainedObjects;
        staticText = "";
    }

    public DetainAllRestrictionEffect(final DetainAllRestrictionEffect effect) {
        super(effect);
        this.detainedObjects = effect.detainedObjects;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        for(FixedTarget fixedTarget :this.detainedObjects) {
            Permanent permanent = game.getPermanent(fixedTarget.getFirst(game, source));
            if (permanent != null) {
                permanent.addInfo(new StringBuilder("detain").append(getId()).toString(),"[Detained]");
            }
        }
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        if (game.getPhase().getStep().getType() == PhaseStep.UNTAP && game.getStep().getStepPart() == Step.StepPart.PRE)
        {
            if (game.getActivePlayerId().equals(source.getControllerId()) || game.getPlayer(source.getControllerId()).hasReachedNextTurnAfterLeaving()) {
                for(FixedTarget fixedTarget :this.detainedObjects) {
                    Permanent permanent = game.getPermanent(fixedTarget.getFirst(game, source));
                    if (permanent != null) {
                        permanent.addInfo(new StringBuilder("detain").append(getId()).toString(),"");
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        for(FixedTarget fixedTarget :this.detainedObjects) {
            UUID targetId = fixedTarget.getFirst(game, source);
            if (targetId != null && targetId.equals(permanent.getId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canAttack(Game game) {
        return false;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game) {
        return false;
    }
    
    @Override
    public boolean canUseActivatedAbilities(Permanent permanent, Ability source, Game game) {
        return false;
    }

    @Override
    public DetainAllRestrictionEffect copy() {
        return new DetainAllRestrictionEffect(this);
    }

}
