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
package mage.sets.knightsvsdragons;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author Quercitron
 */
public class ConeOfFlame extends CardImpl {

    public ConeOfFlame(UUID ownerId) {
        super(ownerId, 75, "Cone of Flame", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{3}{R}{R}");
        this.expansionSetCode = "DDG";


        // Cone of Flame deals 1 damage to target creature or player, 2 damage to another target creature or player, and 3 damage to a third target creature or player.
        this.getSpellAbility().addEffect(new ConeOfFlameEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlayer(3));
    }

    public ConeOfFlame(final ConeOfFlame card) {
        super(card);
    }

    @Override
    public ConeOfFlame copy() {
        return new ConeOfFlame(this);
    }
}

class ConeOfFlameEffect extends OneShotEffect {

    public ConeOfFlameEffect() {
        super(Outcome.Damage);
        this.staticText = "{source} deals 1 damage to target creature or player, 2 damage to another target creature or player, and 3 damage to a third target creature or player";
    }
    
    public ConeOfFlameEffect(final ConeOfFlameEffect effect) {
        super(effect);
    }

    @Override
    public ConeOfFlameEffect copy() {
        return new ConeOfFlameEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        boolean applied = false;
        int damage = 1;
        for (UUID targetId : targetPointer.getTargets(game, source)) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null) {
                applied |= (permanent.damage(damage, source.getSourceId(), game, false, true) > 0);
            }
            Player player = game.getPlayer(targetId);
            if (player != null) {
                applied |= (player.damage(damage, source.getSourceId(), game, false, true) > 0);
            }
            damage++;
        }
        return applied;
    }
    
}
