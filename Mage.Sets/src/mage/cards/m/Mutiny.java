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
package mage.cards.m;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class Mutiny extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature an opponent controls");

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public Mutiny(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");

        // Target creature an opponent controls deals damage equal to its power to another target creature that player controls.
        this.getSpellAbility().addEffect(new MutinyEffect());
        this.getSpellAbility().addTarget(new MutinyFirstTarget(filter));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(new FilterCreaturePermanent("another target creature that player controls")));

    }

    public Mutiny(final Mutiny card) {
        super(card);
    }

    @Override
    public Mutiny copy() {
        return new Mutiny(this);
    }
}

class MutinyEffect extends OneShotEffect {

    public MutinyEffect() {
        super(Outcome.Damage);
        this.staticText = "Target creature an opponent controls deals damage equal to its power to another target creature that player controls";
    }

    public MutinyEffect(final MutinyEffect effect) {
        super(effect);
    }

    @Override
    public MutinyEffect copy() {
        return new MutinyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent firstTarget = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (firstTarget != null) {
            int damage = firstTarget.getPower().getValue();
            Permanent secondTarget = game.getPermanent(source.getTargets().get(1).getFirstTarget());
            if (damage > 0 && secondTarget != null) {
                secondTarget.damage(damage, firstTarget.getId(), game, false, true);
            }
        }
        return true;
    }

}

class MutinyFirstTarget extends TargetCreaturePermanent {

    public MutinyFirstTarget(FilterCreaturePermanent filter) {
        super(1, 1, filter, false);
    }

    public MutinyFirstTarget(final MutinyFirstTarget target) {
        super(target);
    }

    @Override
    public void addTarget(UUID id, Ability source, Game game, boolean skipEvent) {
        super.addTarget(id, source, game, skipEvent);
        // Update the second target
        UUID firstController = game.getControllerId(id);
        if (firstController != null && source.getTargets().size() > 1) {
            Player controllingPlayer = game.getPlayer(firstController);
            TargetCreaturePermanent targetCreaturePermanent = (TargetCreaturePermanent) source.getTargets().get(1);
            // Set a new filter to the second target with the needed restrictions
            FilterCreaturePermanent filter = new FilterCreaturePermanent("another creature that player " + controllingPlayer.getName() + " controls");
            filter.add(new ControllerIdPredicate(firstController));
            filter.add(Predicates.not(new PermanentIdPredicate(id)));
            targetCreaturePermanent.replaceFilter(filter);
        }
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        if (super.canTarget(controllerId, id, source, game)) {
            // can only target, if the controller has at least two targetable creatures
            UUID controllingPlayerId = game.getControllerId(id);
            int possibleTargets = 0;
            MageObject sourceObject = game.getObject(source.getId());
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, controllingPlayerId, game)) {
                if (permanent.canBeTargetedBy(sourceObject, controllerId, game)) {
                    possibleTargets++;
                }
            }
            return possibleTargets > 1;
        }
        return false;
    }

    @Override
    public boolean canChoose(UUID sourceId, UUID sourceControllerId, Game game) {
        if (super.canChoose(sourceId, sourceControllerId, game)) {
            UUID controllingPlayerId = game.getControllerId(sourceId);
            for (UUID playerId : game.getOpponents(controllingPlayerId)) {
                int possibleTargets = 0;
                MageObject sourceObject = game.getObject(sourceId);
                for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, playerId, game)) {
                    if (permanent.canBeTargetedBy(sourceObject, controllingPlayerId, game)) {
                        possibleTargets++;
                    }
                }
                if (possibleTargets > 1) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public MutinyFirstTarget copy() {
        return new MutinyFirstTarget(this);
    }
}
