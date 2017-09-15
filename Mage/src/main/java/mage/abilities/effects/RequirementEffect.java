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
package mage.abilities.effects;

import java.util.UUID;
import mage.abilities.Ability;
import mage.constants.Duration;
import mage.constants.EffectType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class RequirementEffect extends ContinuousEffectImpl {

    boolean playerRelated; // defines a requirement that is more related to a player than a single creature

    public RequirementEffect(Duration duration) {
        this(duration, false);
    }

    /**
     *
     * @param duration
     * @param playerRelated defines a requirement that is more related to a
     * player than a single creature
     */
    public RequirementEffect(Duration duration, boolean playerRelated) {
        super(duration, Outcome.Detriment);
        this.effectType = EffectType.REQUIREMENT;
        this.playerRelated = playerRelated;
    }

    public RequirementEffect(final RequirementEffect effect) {
        super(effect);
        this.playerRelated = effect.playerRelated;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        throw new UnsupportedOperationException("Not supported.");
    }

    public abstract boolean applies(Permanent permanent, Ability source, Game game);

    public abstract boolean mustAttack(Game game);

    public abstract boolean mustBlock(Game game);

    public boolean mustBlockAny(Game game) {
        return false;
    }

    /**
     * Defines the defender a attacker has to attack
     *
     * @param source
     * @param game
     * @return
     */
    public UUID mustAttackDefender(Ability source, Game game) {
        return null;
    }

    public UUID mustBlockAttacker(Ability source, Game game) {
        return null;
    }

    public UUID mustBlockAttackerIfElseUnblocked(Ability source, Game game) {
        return null;
    }

    /**
     * Player related check The player returned or controlled planeswalker must
     * be attacked with at least one attacker
     *
     * @param source
     * @param game
     * @return
     */
    public UUID playerMustBeAttackedIfAble(Ability source, Game game) {
        return null;
    }

    public boolean isPlayerRelated() {
        return playerRelated;
    }

}
