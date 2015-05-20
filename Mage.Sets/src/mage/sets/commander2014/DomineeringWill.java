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
package mage.sets.commander2014;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RequirementEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class DomineeringWill extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonattacking creatures");

    static {
        filter.add(Predicates.not(new AttackingPredicate()));
    }

    public DomineeringWill(UUID ownerId) {
        super(ownerId, 13, "Domineering Will", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{3}{U}");
        this.expansionSetCode = "C14";


        // Target player gains control of up to three target nonattacking creatures until end of turn. Untap those creatures. They block this turn if able.
        this.getSpellAbility().addEffect(new DomineeringWillEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 3, filter, false));

    }

    public DomineeringWill(final DomineeringWill card) {
        super(card);
    }

    @Override
    public DomineeringWill copy() {
        return new DomineeringWill(this);
    }
}

class DomineeringWillEffect extends OneShotEffect {

    public DomineeringWillEffect() {
        super(Outcome.Benefit);
        staticText = "Target player gains control of up to three target nonattacking creatures until end of turn. Untap those creatures. They block this turn if able";
    }

    public DomineeringWillEffect(final DomineeringWillEffect effect) {
        super(effect);
    }

    @Override
    public DomineeringWillEffect copy() {
        return new DomineeringWillEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        if (targetPlayer != null) {
            ContinuousEffect effect = new DomineeringWillControlEffect();
            game.addEffect(effect, source);
            Effect effect2 = new DomineeringWillUntapTargetEffect();
            effect2.setText("Untap those creatures");
            effect2.apply(game, source);
            RequirementEffect effect3 = new DomineeringWillBlocksIfAbleTargetEffect(Duration.EndOfTurn);
            effect3.setText("They block this turn if able");
            game.addEffect(effect3, source);
            return true;
        }
        return false;
    }
}

class DomineeringWillControlEffect extends ContinuousEffectImpl {

    public DomineeringWillControlEffect() {
        super(Duration.EndOfTurn, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
    }

    public DomineeringWillControlEffect(final DomineeringWillControlEffect effect) {
        super(effect);
    }

    @Override
    public DomineeringWillControlEffect copy() {
        return new DomineeringWillControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        if (targetPlayer != null) {
            boolean targetStillExists = false;
            for (UUID permanentId : source.getTargets().get(1).getTargets()) {
                Permanent permanent = game.getPermanent(permanentId);
                if (permanent != null) {
                    targetStillExists = true;
                    if (targetPlayer != null) {
                        permanent.changeControllerId(targetPlayer.getId(), game);
                        permanent.getAbilities().setControllerId(targetPlayer.getId());
                    } else {
                        permanent.changeControllerId(source.getControllerId(), game);
                        permanent.getAbilities().setControllerId(source.getControllerId());
                    }
                }
            }
            if (!targetStillExists) {
                // no valid target exists, effect can be discarded
                discard();
            }
            return true;
        }
        return false;
    }
}

class DomineeringWillUntapTargetEffect extends OneShotEffect {

    public DomineeringWillUntapTargetEffect() {
        super(Outcome.Untap);
    }

    public DomineeringWillUntapTargetEffect(final DomineeringWillUntapTargetEffect effect) {
        super(effect);
    }

    @Override
    public DomineeringWillUntapTargetEffect copy() {
        return new DomineeringWillUntapTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID target : source.getTargets().get(1).getTargets()) {
            Permanent permanent = game.getPermanent(target);
            if (permanent != null) {
                permanent.untap(game);
            }
        }
        return true;
    }
}

class DomineeringWillBlocksIfAbleTargetEffect extends RequirementEffect {

    public DomineeringWillBlocksIfAbleTargetEffect(Duration duration) {
        super(duration);
    }

    public DomineeringWillBlocksIfAbleTargetEffect(final DomineeringWillBlocksIfAbleTargetEffect effect) {
        super(effect);
    }

    @Override
    public DomineeringWillBlocksIfAbleTargetEffect copy() {
        return new DomineeringWillBlocksIfAbleTargetEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return source.getTargets().get(1).getTargets().contains(permanent.getId());
    }

    @Override
    public boolean mustAttack(Game game) {
        return false;
    }

    @Override
    public boolean mustBlock(Game game) {
        return false;
    }

    @Override
    public boolean mustBlockAny(Game game) {
        return true;
    }
}
