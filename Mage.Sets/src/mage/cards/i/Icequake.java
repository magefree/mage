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
package mage.sets.iceage;

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
public class Icequake extends CardImpl {

    public Icequake(UUID ownerId) {
        super(ownerId, 22, "Icequake", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{1}{B}{B}");
        this.expansionSetCode = "ICE";

        // Destroy target land.
        // If that land was a snow land, Icequake deals 1 damage to that land's controller.
        this.getSpellAbility().addEffect(new IcequakeEffect());
        this.getSpellAbility().addTarget(new TargetLandPermanent());
        
        
    }

    public Icequake(final Icequake card) {
        super(card);
    }

    @Override
    public Icequake copy() {
        return new Icequake(this);
    }
}

class IcequakeEffect extends OneShotEffect {

    public IcequakeEffect() {
        super(Outcome.Damage);
        this.staticText = "Destroy target land.<br>If that land was a snow land, {this} deals 1 damage to that land's controller.";
    }

    public IcequakeEffect(final IcequakeEffect effect) {
        super(effect);
    }

    @Override
    public IcequakeEffect copy() {
        return new IcequakeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent != null && controller != null) {
            permanent.destroy(source.getSourceId(), game, false);
            if (permanent.getSupertype().contains("Snow")) {
                controller.damage(1, source.getSourceId(), game, false, true);
            }
            return true;
        }
        return false;
    }
}
