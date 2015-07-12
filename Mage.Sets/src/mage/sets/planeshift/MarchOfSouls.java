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
package mage.sets.planeshift;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SpiritWhiteToken;

/**
 *
 * @author LoneFox

 */
public class MarchOfSouls extends CardImpl {

    public MarchOfSouls(UUID ownerId) {
        super(ownerId, 10, "March of Souls", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{4}{W}");
        this.expansionSetCode = "PLS";

        // Destroy all creatures. They can't be regenerated. For each creature destroyed this way, its controller puts a 1/1 white Spirit creature token with flying onto the battlefield.
        this.getSpellAbility().addEffect(new MarchOfSoulsEffect());
    }

    public MarchOfSouls(final MarchOfSouls card) {
        super(card);
    }

    @Override
    public MarchOfSouls copy() {
        return new MarchOfSouls(this);
    }
}

class MarchOfSoulsEffect extends OneShotEffect {

    public MarchOfSoulsEffect() {
        super(Outcome.Benefit);
        staticText = "Destroy all creatures. They can't be regenerated. For each creature destroyed this way, its controller puts a 1/1 white Spirit creature token with flying onto the battlefield.";
    }

    public MarchOfSoulsEffect(final MarchOfSoulsEffect effect) {
        super(effect);
    }

    @Override
    public MarchOfSoulsEffect copy() {
        return new MarchOfSoulsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> creatures = game.getBattlefield().getActivePermanents(new FilterCreaturePermanent(),
            source.getControllerId(), source.getSourceId(), game);
        HashMap<UUID, Integer> playersWithCreatures = new HashMap<UUID, Integer>();
        for(Permanent p : creatures) {
            UUID controllerId = p.getControllerId();
            if(p.destroy(source.getSourceId(), game, true)) {
                if(playersWithCreatures.containsKey(controllerId)) {
                    playersWithCreatures.put(controllerId, playersWithCreatures.get(controllerId) + 1);
                }
                else {
                    playersWithCreatures.put(controllerId, 1);
                }
            }
        }
        SpiritWhiteToken token = new SpiritWhiteToken();
        for(UUID playerId : playersWithCreatures.keySet()) {
            token.putOntoBattlefield(playersWithCreatures.get(playerId), game, source.getSourceId(), playerId);
        }
        return true;
    }
}
