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
package mage.cards.r;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.UntapAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author emerald000
 */
public class ReinsOfPower extends CardImpl {

    public ReinsOfPower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}{U}");

        // Untap all creatures you control and all creatures target opponent controls. You and that opponent each gain control of all creatures the other controls until end of turn. Those creatures gain haste until end of turn.
        this.getSpellAbility().addEffect(new ReinsOfPowerEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    public ReinsOfPower(final ReinsOfPower card) {
        super(card);
    }

    @Override
    public ReinsOfPower copy() {
        return new ReinsOfPower(this);
    }
}

class ReinsOfPowerEffect extends OneShotEffect {
    
    ReinsOfPowerEffect() {
        super(Outcome.Benefit);
        this.staticText = "Untap all creatures you control and all creatures target opponent controls. You and that opponent each gain control of all creatures the other controls until end of turn. Those creatures gain haste until end of turn";
    }
    
    ReinsOfPowerEffect(final ReinsOfPowerEffect effect) {
        super(effect);
    }
    
    @Override
    public ReinsOfPowerEffect copy() {
        return new ReinsOfPowerEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        UUID opponentId = this.getTargetPointer().getFirst(game, source);
        if (opponentId != null) {
            // Untap all creatures you control and all creatures target opponent controls.
            FilterCreaturePermanent filter = new FilterCreaturePermanent();
            filter.add(Predicates.or(new ControllerIdPredicate(source.getControllerId()), new ControllerIdPredicate(opponentId)));
            new UntapAllEffect(filter).apply(game, source);
            
            // You and that opponent each gain control of all creatures the other controls until end of turn.
            Set<UUID> yourCreatures = new HashSet<>();
            Set<UUID> opponentCreatures = new HashSet<>();
            for (Permanent permanent : game.getBattlefield().getActivePermanents(new FilterControlledCreaturePermanent(), source.getControllerId(), source.getSourceId(), game)) {
                yourCreatures.add(permanent.getId());
            }
            FilterCreaturePermanent filterOpponent = new FilterCreaturePermanent();
            filterOpponent.add(new ControllerIdPredicate(opponentId));
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filterOpponent, source.getControllerId(), source.getSourceId(), game)) {
                opponentCreatures.add(permanent.getId());
            }
            for (UUID creatureId : yourCreatures) {
                ContinuousEffect effect = new GainControlTargetEffect(Duration.EndOfTurn, opponentId);
                effect.setTargetPointer(new FixedTarget(creatureId));
                game.addEffect(effect, source);
            }
            for (UUID creatureId : opponentCreatures) {
                ContinuousEffect effect = new GainControlTargetEffect(Duration.EndOfTurn);
                effect.setTargetPointer(new FixedTarget(creatureId));
                game.addEffect(effect, source);
            }
            
            // Those creatures gain haste until end of turn.
            game.addEffect(new GainAbilityAllEffect(HasteAbility.getInstance(), Duration.EndOfTurn, filter), source);
            
            return true;
        }
        return false;
    }
}
