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

package mage.abilities.effects.common.continious;

import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;


/**
 *
 * @author LevelX2
 */
public class LoseAllAbilitiesAllEffect extends ContinuousEffectImpl {

    private FilterPermanent filter;

    public LoseAllAbilitiesAllEffect(FilterPermanent filter, Duration duration) {
        super(duration, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.filter = filter;
    }

    public LoseAllAbilitiesAllEffect(final LoseAllAbilitiesAllEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
    }

    @Override
    public LoseAllAbilitiesAllEffect copy() {
        return new LoseAllAbilitiesAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent: game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
            if (permanent != null) {
                permanent.removeAllAbilities(source.getSourceId(), game);
            }
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        StringBuilder sb = new StringBuilder();
        if (duration.equals(Duration.EndOfTurn)) {
            sb.append(duration.toString()).append(", ");
        }
        sb.append(filter.getMessage()).append(" lose all abilities.");
        return sb.toString();
    }

}
