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
package mage.cards.s;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Galatolol
 */
public class SoulBarrier extends CardImpl {

    public SoulBarrier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // Whenever an opponent casts a creature spell, Soul Barrier deals 2 damage to that player unless he or she pays {2}.
        this.addAbility(new SpellCastOpponentTriggeredAbility(Zone.BATTLEFIELD, new SoulBarrierEffect(),
                StaticFilters.FILTER_SPELL_A_CREATURE, false, SetTargetPointer.PLAYER));
    }

    public SoulBarrier(final SoulBarrier card) {
        super(card);
    }

    @Override
    public SoulBarrier copy() {
        return new SoulBarrier(this);
    }
}

class SoulBarrierEffect extends OneShotEffect {

    SoulBarrierEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals 2 damage to that player unless he or she pays {2}";
    }

    SoulBarrierEffect(final SoulBarrierEffect effect) {
        super(effect);
    }

    @Override
    public SoulBarrierEffect copy() {
        return new SoulBarrierEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        Permanent permanent = game.getPermanent(source.getSourceId());

        if (player != null && permanent != null) {
            GenericManaCost cost = new GenericManaCost(2);
            String message = "Would you like to pay {2} to prevent taking 2 damage from " + permanent.getLogName() + "?";
            if (!(player.chooseUse(Outcome.Benefit, message, source, game)
                    && cost.pay(source, game, source.getSourceId(), player.getId(), false, null))) {
                player.damage(2, source.getSourceId(), game, false, true);
            }
            return true;
        }
        return false;
    }
}
