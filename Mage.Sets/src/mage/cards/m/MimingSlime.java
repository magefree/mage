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

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.OozeToken;
import mage.players.Player;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class MimingSlime extends CardImpl {

    public MimingSlime(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{G}");

        // Put an X/X green Ooze creature token onto the battlefield, where X is the greatest power among creatures you control.
        this.getSpellAbility().addEffect(new MimingSlimeEffect());
    }

    public MimingSlime(final MimingSlime card) {
        super(card);
    }

    @Override
    public MimingSlime copy() {
        return new MimingSlime(this);
    }
}

class MimingSlimeEffect extends OneShotEffect {

    public MimingSlimeEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Put an X/X green Ooze creature token onto the battlefield, where X is the greatest power among creatures you control";
    }

    public MimingSlimeEffect(final MimingSlimeEffect effect) {
        super(effect);
    }

    @Override
    public MimingSlimeEffect copy() {
        return new MimingSlimeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            List<Permanent> creatures = game.getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), player.getId(), game);
            int amount = 0;
            for (Permanent creature : creatures) {
                int power = creature.getPower().getValue();
                if (amount < power) {
                    amount = power;
                }
            }
            OozeToken oozeToken = new OozeToken();
            oozeToken.getPower().modifyBaseValue(amount);
            oozeToken.getToughness().modifyBaseValue(amount);
            oozeToken.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());
            return true;
        }
        return false;
    }
}
