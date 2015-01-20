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
package mage.sets.shadowmoor;

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
 * @author jeffwadsworth
 */
public class PoisonTheWell extends CardImpl {

    public PoisonTheWell(UUID ownerId) {
        super(ownerId, 193, "Poison the Well", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{2}{B/R}{B/R}");
        this.expansionSetCode = "SHM";

        // Destroy target land. Poison the Well deals 2 damage to that land's controller.
        this.getSpellAbility().addEffect(new PoisonTheWellEffect());
        this.getSpellAbility().addTarget(new TargetLandPermanent());
    }

    public PoisonTheWell(final PoisonTheWell card) {
        super(card);
    }

    @Override
    public PoisonTheWell copy() {
        return new PoisonTheWell(this);
    }
}

class PoisonTheWellEffect extends OneShotEffect {

    public PoisonTheWellEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy target land. {this} deals 2 damage to that land's controller";
    }

    public PoisonTheWellEffect(final PoisonTheWellEffect effect) {
        super(effect);
    }

    @Override
    public PoisonTheWellEffect copy() {
        return new PoisonTheWellEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetedLand = game.getPermanent(source.getFirstTarget());
        if (targetedLand != null) {
            targetedLand.destroy(source.getSourceId(), game, true);
            Player controller = game.getPlayer(targetedLand.getControllerId());
            if (controller != null) {            
                controller.damage(2, source.getSourceId(), game, false, true);
            }
            return true;
        }
        return false;
    }
}
