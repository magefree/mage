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
package mage.sets.eldritchmoon;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author fireshoes
 */
public class HarmlessOffering extends CardImpl {

    public HarmlessOffering(UUID ownerId) {
        super(ownerId, 131, "Harmless Offering", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{2}{R}");
        this.expansionSetCode = "EMN";

        // Target opponent gains control of target permanent you control.
        this.getSpellAbility().addEffect(new HarmlessOfferingEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addTarget(new TargetControlledPermanent());
    }

    public HarmlessOffering(final HarmlessOffering card) {
        super(card);
    }

    @Override
    public HarmlessOffering copy() {
        return new HarmlessOffering(this);
    }
}

class HarmlessOfferingEffect extends ContinuousEffectImpl {

    public HarmlessOfferingEffect() {
        super(Duration.EndOfGame, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.Benefit);
        this.staticText = "Target opponent gains control of target permanent you control";
    }

    public HarmlessOfferingEffect(final HarmlessOfferingEffect effect) {
        super(effect);
    }

    @Override
    public HarmlessOfferingEffect copy() {
        return new HarmlessOfferingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID controllerId = source.getTargets().get(0).getFirstTarget();
        Player controller = game.getPlayer(controllerId);
        Permanent permanent = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (controller != null && permanent != null) {
            permanent.changeControllerId(controllerId, game);
        } else {
            this.discard();
        }
        return true;
    }

}
