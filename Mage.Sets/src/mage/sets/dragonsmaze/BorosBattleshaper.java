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

package mage.sets.dragonsmaze;

import java.util.UUID;

import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttacksIfAbleTargetEffect;
import mage.abilities.effects.common.BlocksIfAbleTargetEffect;
import mage.abilities.effects.common.CantAttackTargetEffect;
import mage.abilities.effects.common.CantBlockTargetEffect;
import mage.abilities.effects.common.continious.GainAbilityTargetEffect;
import mage.abilities.keyword.AttacksThisTurnMarkerAbility;
import mage.abilities.keyword.BlocksThisTurnMarkerAbility;
import mage.cards.CardImpl;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */


public class BorosBattleshaper extends CardImpl<BorosBattleshaper> {

    public BorosBattleshaper (UUID ownerId) {
        super(ownerId, 58, "Boros Battleshaper", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{5}{R}{W}");
        this.expansionSetCode = "DGM";
        this.subtype.add("Minotaur");
        this.subtype.add("Soldier");
        this.color.setRed(true);
        this.color.setWhite(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // At the beginning of each combat, up to one target creature attacks or blocks this combat if able and up to one target creature can't attack or block this combat.
        Ability ability = new BeginningOfCombatTriggeredAbility(Zone.BATTLEFIELD, new BorosBattleshaperEffect(), TargetController.ANY, false, false);
        ability.addTarget(new TargetCreaturePermanent(0,1,new FilterCreaturePermanent("creature that attacks or blocks if able"),false));
        ability.addTarget(new TargetCreaturePermanent(0,1,new FilterCreaturePermanent("creature that can't attack or block"),false));
        this.addAbility(ability);


    }

    public BorosBattleshaper (final BorosBattleshaper card) {
        super(card);
    }

    @Override
    public BorosBattleshaper copy() {
        return new BorosBattleshaper(this);
    }

}

class BorosBattleshaperEffect extends OneShotEffect<BorosBattleshaperEffect> {

    public BorosBattleshaperEffect() {
        super(Outcome.Benefit);
        this.staticText = "up to one target creature attacks or blocks this combat if able and up to one target creature can't attack or block this combat";
    }

    public BorosBattleshaperEffect(final BorosBattleshaperEffect effect) {
        super(effect);
    }

    @Override
    public BorosBattleshaperEffect copy() {
        return new BorosBattleshaperEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature1 = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (creature1 != null) {
            if (game.getOpponents(creature1.getControllerId()).contains(game.getActivePlayerId())) {
                // Blocks
                ContinuousEffectImpl effect = new BlocksIfAbleTargetEffect(Duration.EndOfTurn);
                effect.setTargetPointer(new FixedTarget(creature1.getId()));
                game.addEffect(effect, source);
                effect = new GainAbilityTargetEffect(BlocksThisTurnMarkerAbility.getInstance(), Duration.EndOfTurn, "");
                effect.setTargetPointer(new FixedTarget(creature1.getId()));
                game.addEffect(effect, source);
            } else {
                // Attacks
                ContinuousEffectImpl effect = new AttacksIfAbleTargetEffect(Duration.EndOfTurn);
                effect.setTargetPointer(new FixedTarget(creature1.getId()));
                game.addEffect(effect, source);
                effect = new GainAbilityTargetEffect(AttacksThisTurnMarkerAbility.getInstance(), Duration.EndOfTurn, "");
                effect.setTargetPointer(new FixedTarget(creature1.getId()));
                game.addEffect(effect, source);

            }
        }
        Permanent creature2 = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (creature2 != null) {
            if (game.getOpponents(creature2.getControllerId()).contains(game.getActivePlayerId())) {
                // Blocks
                ContinuousEffectImpl effect = new CantBlockTargetEffect(Duration.EndOfTurn);
                effect.setTargetPointer(new FixedTarget(creature2.getId()));
                game.addEffect(effect, source);
            } else {
                // Attacks
                ContinuousEffectImpl effect = new CantAttackTargetEffect(Duration.EndOfTurn);
                effect.setTargetPointer(new FixedTarget(creature2.getId()));
                game.addEffect(effect, source);
            }
        }
        return true;
    }
}
