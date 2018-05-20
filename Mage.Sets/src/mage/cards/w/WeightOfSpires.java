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
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public class WeightOfSpires extends CardImpl {

    public WeightOfSpires(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Weight of Spires deals damage to target creature equal to the number of nonbasic lands that creature's controller controls.
        this.getSpellAbility().addEffect(new WeightOfSpiresEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public WeightOfSpires(final WeightOfSpires card) {
        super(card);
    }

    @Override
    public WeightOfSpires copy() {
        return new WeightOfSpires(this);
    }
}

class WeightOfSpiresEffect extends OneShotEffect {

    WeightOfSpiresEffect() {
        super(Outcome.Benefit);
        this.staticText = "{this} deals damage to target creature equal to the number of nonbasic lands that creature's controller controls";
    }

    WeightOfSpiresEffect(final WeightOfSpiresEffect effect) {
        super(effect);
    }

    @Override
    public WeightOfSpiresEffect copy() {
        return new WeightOfSpiresEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(source.getFirstTarget());
        if (creature == null) {
            return false;
        }
        Player player = game.getPlayer(creature.getControllerId());
        if (player == null) {
            return false;
        }
        int damage = game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_LANDS_NONBASIC, player.getId(), game).size();
        return new DamageTargetEffect(damage).apply(game, source);
    }
}
