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

import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.LoseAbilityTargetEffect;
import mage.abilities.effects.common.replacement.DiesReplacementEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlayer;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author fireshoes
 */
public class BurnFromWithin extends CardImpl {

    public BurnFromWithin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}");

        // Burn from Within deals X damage to target creature or player. If a creature is dealt damage this way, it loses indestructible until end of turn.
        // If that creature would die this turn, exile it instead.
        this.getSpellAbility().addEffect(new BurnFromWithinEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlayer());

    }

    public BurnFromWithin(final BurnFromWithin card) {
        super(card);
    }

    @Override
    public BurnFromWithin copy() {
        return new BurnFromWithin(this);
    }
}

class BurnFromWithinEffect extends OneShotEffect {

    public BurnFromWithinEffect() {
        super(Outcome.Benefit);
        this.staticText = "{this} deals X damage to target creature or player. If a creature is dealt damage this way, it loses indestructible until end of turn. If that creature would die this turn, exile it instead";
    }

    public BurnFromWithinEffect(final BurnFromWithinEffect effect) {
        super(effect);
    }

    @Override
    public BurnFromWithinEffect copy() {
        return new BurnFromWithinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent creature = game.getPermanent(getTargetPointer().getFirst(game, source));
            int amount = source.getManaCostsToPay().getX();
            if (creature != null) {
                game.addEffect(new DiesReplacementEffect(new MageObjectReference(creature, game), Duration.EndOfTurn), source);
                int damageDealt = creature.damage(amount, source.getSourceId(), game, false, true);
                if (damageDealt > 0) {
                    ContinuousEffect effect = new LoseAbilityTargetEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn);
                    effect.setTargetPointer(new FixedTarget(creature.getId()));
                    game.addEffect(effect, source);
                }
                return true;
            }
            Player targetPlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
            if (targetPlayer != null) {
                targetPlayer.damage(amount, source.getSourceId(), game, false, true);
                return true;
            }
        }
        return false;
    }
}
