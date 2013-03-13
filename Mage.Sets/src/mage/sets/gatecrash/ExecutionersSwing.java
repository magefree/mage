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
package mage.sets.gatecrash;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.common.continious.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.SourceDidDamageWatcher;

/**
 *
 * @author LevelX2
 */
public class ExecutionersSwing extends CardImpl<ExecutionersSwing> {

    public ExecutionersSwing(UUID ownerId) {
        super(ownerId, 161, "Executioner's Swing", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{W}{B}");
        this.expansionSetCode = "GTC";

        this.color.setBlack(true);
        this.color.setWhite(true);

        // Target creature that dealt damage this turn gets -5/-5 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(-5,-5, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanentThatDealtDamageThisTurn());

        this.addWatcher(new SourceDidDamageWatcher());
    }

    public ExecutionersSwing(final ExecutionersSwing card) {
        super(card);
    }

    @Override
    public ExecutionersSwing copy() {
        return new ExecutionersSwing(this);
    }
}

class TargetCreaturePermanentThatDealtDamageThisTurn<T extends TargetCreaturePermanent<T>> extends TargetPermanent<TargetCreaturePermanent<T>> {

    public TargetCreaturePermanentThatDealtDamageThisTurn() {
        super(1, 1, new FilterCreaturePermanent(), false);
        setRequired(true);
        targetName = "creature that dealt damage this turn";
    }

    public TargetCreaturePermanentThatDealtDamageThisTurn(final TargetCreaturePermanentThatDealtDamageThisTurn target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        SourceDidDamageWatcher watcher = (SourceDidDamageWatcher) game.getState().getWatchers().get("SourceDidDamageWatcher");
        if (watcher != null) {
            if (watcher.damageSources.contains(id)) {
                return super.canTarget(id, source, game);
            }
        }
        return false;
    }

    @Override
    public boolean canChoose(UUID sourceId, UUID sourceControllerId, Game game) {
        int remainingTargets = this.minNumberOfTargets - targets.size();
        if (remainingTargets <= 0) {
            return true;
        }
        int count = 0;
        MageObject targetSource = game.getObject(sourceId);
        SourceDidDamageWatcher watcher = (SourceDidDamageWatcher) game.getState().getWatchers().get("SourceDidDamageWatcher");
        if (watcher != null) {
            for (Permanent permanent: game.getBattlefield().getActivePermanents(filter, sourceControllerId, sourceId, game)) {
                if (!targets.containsKey(permanent.getId()) && watcher.damageSources.contains(permanent.getId())) {
                    if (!notTarget || permanent.canBeTargetedBy(targetSource, sourceControllerId, game)) {
                        count++;
                        if (count >= remainingTargets) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceId, UUID sourceControllerId, Game game) {
        Set<UUID> availablePossibleTargets = super.possibleTargets(sourceId, sourceControllerId, game);
        Set<UUID> possibleTargets = new HashSet<UUID>();
        SourceDidDamageWatcher watcher = (SourceDidDamageWatcher) game.getState().getWatchers().get("SourceDidDamageWatcher");
        if (watcher != null) {
            for (UUID targetId : availablePossibleTargets) {
                Permanent permanent = game.getPermanent(targetId);
                if (permanent != null && !targets.containsKey(permanent.getId()) && watcher.damageSources.contains(targetId)) {
                    possibleTargets.add(targetId);
                }
            }
        }
        return possibleTargets;
    }

    @Override
    public TargetCreaturePermanentThatDealtDamageThisTurn copy() {
        return new TargetCreaturePermanentThatDealtDamageThisTurn(this);
    }
}
