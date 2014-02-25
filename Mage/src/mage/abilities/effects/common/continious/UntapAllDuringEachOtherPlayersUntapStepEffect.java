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

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.SubLayer;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class UntapAllDuringEachOtherPlayersUntapStepEffect extends ContinuousEffectImpl<UntapAllDuringEachOtherPlayersUntapStepEffect> {

    private final FilterPermanent filter;

    public UntapAllDuringEachOtherPlayersUntapStepEffect(FilterPermanent filter) {
        super(Duration.WhileOnBattlefield, Outcome.Untap);
        this.filter = filter;
        staticText = new StringBuilder("Untap all ").append(filter.getMessage()).append(" during each other player's untap step").toString();
    }

    public UntapAllDuringEachOtherPlayersUntapStepEffect(final UntapAllDuringEachOtherPlayersUntapStepEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public UntapAllDuringEachOtherPlayersUntapStepEffect copy() {
        return new UntapAllDuringEachOtherPlayersUntapStepEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Boolean applied = (Boolean) game.getState().getValue(source.getSourceId() + "applied");
        if (applied == null) {
            applied = Boolean.FALSE;
        }
        if (!applied && layer.equals(Layer.RulesEffects)) {
            if (!game.getActivePlayerId().equals(source.getControllerId()) && game.getStep().getType() == PhaseStep.UNTAP) {
                game.getState().setValue(source.getSourceId() + "applied", true);
                for (Permanent artifact: game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game)) {
                    boolean untap = true;
                    for (RestrictionEffect effect: game.getContinuousEffects().getApplicableRestrictionEffects(artifact, game).keySet()) {
                        untap &= effect.canBeUntapped(artifact, game);
                    }
                    if (untap) {
                        artifact.untap(game);
                    }
                }
            }
        } else if (applied && layer.equals(Layer.RulesEffects)) {
            if (game.getStep().getType() == PhaseStep.END_TURN) {
                game.getState().setValue(source.getSourceId() + "applied", false);
            }
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.RulesEffects;
    }
}
