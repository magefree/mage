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
package mage.sets.masterseditionii;

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
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author fireshoes
 */
public class Thermokarst extends CardImpl {

    public Thermokarst(UUID ownerId) {
        super(ownerId, 183, "Thermokarst", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{1}{G}{G}");
        this.expansionSetCode = "ME2";

        // Destroy target land. If that land was a snow land, you gain 1 life.
        this.getSpellAbility().addEffect(new ThermokarstEffect());
        this.getSpellAbility().addTarget(new TargetLandPermanent());
    }

    public Thermokarst(final Thermokarst card) {
        super(card);
    }

    @Override
    public Thermokarst copy() {
        return new Thermokarst(this);
    }
}

class ThermokarstEffect extends OneShotEffect {

    public ThermokarstEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy target land. If that land was a snow land, you gain 1 life.";
    }

    public ThermokarstEffect(final ThermokarstEffect effect) {
        super(effect);
    }

    @Override
    public ThermokarstEffect copy() {
        return new ThermokarstEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent != null && controller != null) {
            permanent.destroy(source.getSourceId(), game, false);
            if (permanent.getSupertype().contains("Snow")) {
                Player player = game.getPlayer(source.getControllerId());
                if (player != null) {
                    player.gainLife(1, game);
                }
            }
            return true;
        }
        return false;
    }
}
