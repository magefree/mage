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
package mage.sets.planechase2012;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.GainAbilityTargetEffect;
import mage.abilities.effects.common.continious.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class MassMutiny extends CardImpl<MassMutiny> {

    public MassMutiny(UUID ownerId) {
        super(ownerId, 48, "Mass Mutiny", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{3}{R}{R}");
        this.expansionSetCode = "PC2";

        this.color.setRed(true);
        this.getSpellAbility().addEffect(new MassMutinyEffect());
        // For each opponent, gain control of up to one target creature that player controls until end of turn. Untap those creatures. They gain haste until end of turn.
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            for(UUID opponentId : game.getOpponents(ability.getControllerId())) {
                Player opponent = game.getPlayer(opponentId);
                if (opponent != null) {
                    ability.getTargets().clear();
                    FilterCreaturePermanent filter = new FilterCreaturePermanent(new StringBuilder("creature from opponent ").append(opponent.getName()).toString());
                    filter.add(new ControllerIdPredicate(opponentId));
                    TargetCreaturePermanent target = new TargetCreaturePermanent(0,1, filter,false);
                    ability.addTarget(target);
                }
            }
        }
    }

    public MassMutiny(final MassMutiny card) {
        super(card);
    }

    @Override
    public MassMutiny copy() {
        return new MassMutiny(this);
    }
}


class MassMutinyEffect extends OneShotEffect<MassMutinyEffect> {

    public MassMutinyEffect() {
        super(Outcome.GainControl);
        this.staticText = "For each opponent, gain control of up to one target creature that player controls until end of turn. Untap those creatures. They gain haste until end of turn";
    }

    public MassMutinyEffect(final MassMutinyEffect effect) {
        super(effect);
    }

    @Override
    public MassMutinyEffect copy() {
        return new MassMutinyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = false;
        for (Target target: source.getTargets()) {
            if (target instanceof TargetCreaturePermanent) {
                Permanent targetCreature = game.getPermanent(target.getFirstTarget());
                if (targetCreature != null) {
                    ContinuousEffect effect1 = new GainControlTargetEffect(Duration.EndOfTurn);
                    effect1.setTargetPointer(new FixedTarget(targetCreature.getId()));
                    game.addEffect(effect1, source);

                    ContinuousEffect effect2 = new GainAbilityTargetEffect(HasteAbility.getInstance(), Constants.Duration.EndOfTurn);
                    effect2.setTargetPointer(new FixedTarget(targetCreature.getId()));
                    game.addEffect(effect2, source);

                    targetCreature.untap(game);
                    result = true;
                }
            }
        }
        return result;
    }
}
