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
package mage.cards.b;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author emerald000
 */
public class BlatantThievery extends CardImpl {

    public BlatantThievery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{U}{U}{U}");

        // For each opponent, gain control of target permanent that player controls.
        this.getSpellAbility().addEffect(new BlatantThieveryEffect());
    }

    public BlatantThievery(final BlatantThievery card) {
        super(card);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            ability.getTargets().clear();
            ability.addTarget(new BlatantThieveryTarget(game.getOpponents(ability.getControllerId()).size()));
        }
    }

    @Override
    public BlatantThievery copy() {
        return new BlatantThievery(this);
    }
}

class BlatantThieveryEffect extends OneShotEffect {

    BlatantThieveryEffect() {
        super(Outcome.GainControl);
        this.staticText = "For each opponent, gain control of target permanent that player controls";
    }

    BlatantThieveryEffect(final BlatantThieveryEffect effect) {
        super(effect);
    }

    @Override
    public BlatantThieveryEffect copy() {
        return new BlatantThieveryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID targetId : getTargetPointer().getTargets(game, source)) {
            ContinuousEffect effect = new GainControlTargetEffect(Duration.EndOfGame);
            effect.setTargetPointer(new FixedTarget(targetId));
            game.addEffect(effect, source);
        }
        return true;
    }
}

class BlatantThieveryTarget extends TargetPermanent {

    Map<UUID, UUID> targetOpponent = new HashMap<>();

    public BlatantThieveryTarget(int opponents) {
        super(opponents, opponents, new FilterPermanent("a permanent for each opponent"), false);
    }

    public BlatantThieveryTarget(final BlatantThieveryTarget target) {
        super(target);
        this.targetOpponent.putAll(target.targetOpponent);
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID objectId, Ability source, Game game) {
        Permanent targetObject = game.getPermanent(objectId);
        if (targetObject == null || !game.getOpponents(source.getControllerId()).contains(targetObject.getControllerId())) {
            return false;
        }
        // If a permanent changes controller after being targeted but before this spell resolves, you won't gain control of that permanent.
        if (targetOpponent.containsKey(objectId)) {
            if (!targetOpponent.get(objectId).equals(targetObject.getControllerId())) {
                return false;
            }
        } else {
            // if already a target from this opponent exists, another can't be target
            if (targetOpponent.values().contains(targetObject.getControllerId())) {
                return false;
            }
        }
        return super.canTarget(controllerId, objectId, source, game);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceId, UUID sourceControllerId, Game game) {
        Set<UUID> opponents = new HashSet<>();
        for (UUID targetId : getTargets()) {
            Permanent oldTargets = game.getPermanent(targetId);
            if (oldTargets != null) {
                opponents.add(oldTargets.getControllerId());
            }
        }
        Set<UUID> possibleTargets = new HashSet<>();
        MageObject mageObject = game.getObject(sourceId);
        if (mageObject == null) {
            return possibleTargets;
        }
        for (UUID opponentId : game.getOpponents(sourceControllerId)) {
            if (opponents.contains(opponentId)) {
                // Target for this opponent already selected
                continue;
            }
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(opponentId)) {
                if (permanent.canBeTargetedBy(mageObject, sourceControllerId, game)) {
                    possibleTargets.add(permanent.getId());
                }
            }
        }
        return possibleTargets;
    }

    @Override
    public boolean canChoose(UUID sourceId, UUID sourceControllerId, Game game) {
        for (UUID opponentId : game.getOpponents(sourceControllerId)) {
            boolean targetAvailable = false;
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(opponentId)) {
                if (!targets.containsKey(permanent.getId())) {
                    MageObject mageObject = game.getObject(sourceId);
                    if (mageObject != null && permanent.canBeTargetedBy(mageObject, sourceControllerId, game)) {
                        targetAvailable = true;
                        break;
                    }

                } else {
                    targetAvailable = true;
                    break;
                }
            }
            if (!targetAvailable) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void addTarget(UUID objectId, int amount, Ability source, Game game, boolean skipEvent) {
        Permanent targetObject = game.getPermanent(objectId);
        if (targetObject != null) {
            targetOpponent.put(objectId, targetObject.getControllerId());
        }
        super.addTarget(objectId, amount, source, game, skipEvent);
    }

    @Override
    public void remove(UUID id) {
        super.remove(id);
        targetOpponent.remove(id);
    }

    @Override
    public BlatantThieveryTarget copy() {
        return new BlatantThieveryTarget(this);
    }
}
