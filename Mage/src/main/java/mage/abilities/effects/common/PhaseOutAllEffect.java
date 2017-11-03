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
package mage.abilities.effects.common;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * This class should only be used within the application of another effect
 *
 * @author TheElk801
 */
public class PhaseOutAllEffect extends OneShotEffect {

    private final List<UUID> idList;

    public PhaseOutAllEffect(List<UUID> idList) {
        super(Outcome.Neutral);
        this.idList = idList;
    }

    public PhaseOutAllEffect(final PhaseOutAllEffect effect) {
        super(effect);
        this.idList = effect.idList;
    }

    @Override
    public PhaseOutAllEffect copy() {
        return new PhaseOutAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // First we phase out everything that isn't attached to anything
        // Anything attached to these permanents will phase out indirectly
        for (UUID permanentId : idList) {
            Permanent permanent = game.getPermanent(permanentId);
            if (permanent != null) {
                Permanent attachedTo = game.getPermanent(permanent.getAttachedTo());
                if (attachedTo == null) {
                    permanent.phaseOut(game);
                }
            }
        }
        // Once this is done, we'll have permanents which are attached to something but haven't phased out
        // These will be phased out directly
        for (UUID permanentId : idList) {
            Permanent permanent = game.getPermanent(permanentId);
            if (permanent != null && permanent.isPhasedIn()) {
                permanent.phaseOut(game);
            }
        }
        return true;
    }
}
