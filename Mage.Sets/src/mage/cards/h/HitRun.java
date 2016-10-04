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
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class HitRun extends SplitCard {

    public HitRun(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{B}{R}","{3}{R}{G}",false);

        // Hit
        // Target player sacrifices an artifact or creature. Hit deals damage to that player equal to that permanent's converted mana cost.
        getLeftHalfCard().getSpellAbility().addEffect(new HitEffect());
        getLeftHalfCard().getSpellAbility().addTarget(new TargetPlayer());

        // Run
        // Attacking creatures you control get +1/+0 until end of turn for each other attacking creature.
        getRightHalfCard().getSpellAbility().addEffect(new RunEffect());

    }

    public HitRun(final HitRun card) {
        super(card);
    }

    @Override
    public HitRun copy() {
        return new HitRun(this);
    }
}

class HitEffect extends OneShotEffect {

    public HitEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Target player sacrifices an artifact or creature. Hit deals damage to that player equal to that permanent's converted mana cost";
    }

    public HitEffect(final HitEffect effect) {
        super(effect);
    }

    @Override
    public HitEffect copy() {
        return new HitEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getTargets().getFirstTarget());
        if (targetPlayer != null) {
            FilterControlledPermanent filter = new FilterControlledPermanent("artifact or creature");
            filter.add(Predicates.or(
                    new CardTypePredicate(CardType.ARTIFACT),
                    new CardTypePredicate(CardType.CREATURE)));
            TargetControlledPermanent target = new TargetControlledPermanent(1, 1, filter, true);

            if (target.canChoose(targetPlayer.getId(), game)) {
                targetPlayer.choose(Outcome.Sacrifice, target, source.getSourceId(), game);
                Permanent permanent = game.getPermanent(target.getFirstTarget());
                if (permanent != null) {
                    permanent.sacrifice(source.getSourceId(), game);
                    int damage = permanent.getConvertedManaCost();
                    if (damage > 0) {
                        targetPlayer.damage(damage, source.getSourceId(), game, false, true);
                    }
                }
            }
        }
        return true;
    }
}

class RunEffect extends OneShotEffect {

    public RunEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "Attacking creatures you control get +1/+0 until end of turn for each other attacking creature";
    }

    public RunEffect(final RunEffect effect) {
        super(effect);
    }

    @Override
    public RunEffect copy() {
        return new RunEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int attackingCreatures = game.getBattlefield().count(new FilterAttackingCreature(), controller.getId(), controller.getId(), game);
            if (attackingCreatures > 1) {
                for (Permanent permanent : game.getBattlefield().getAllActivePermanents(new FilterAttackingCreature(), controller.getId(), game)) {
                    ContinuousEffect effect = new BoostTargetEffect(attackingCreatures - 1, 0, Duration.EndOfTurn);
                    effect.setTargetPointer(new FixedTarget(permanent, game));
                    game.addEffect(effect, source);
                }
            }
            return true;
        }
        return false;
    }
}
