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
package mage.abilities.keyword;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Styxo
 */
public class MeditateAbility extends ActivatedAbilityImpl {

    public MeditateAbility(Cost cost) {
        super(Zone.BATTLEFIELD, new ReturnToHandEffect(), cost);
        this.timing = TimingRule.SORCERY;
    }

    public MeditateAbility(final MeditateAbility ability) {
        super(ability);
    }

    @Override
    public MeditateAbility copy() {
        return new MeditateAbility(this);
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder("Meditate ").append(manaCosts.getText());
        sb.append(" <i>(Return this creature to its owner's hand. Meditate only as a sorcery.)</i>");
        return sb.toString();
    }

}

class ReturnToHandEffect extends OneShotEffect {

    public ReturnToHandEffect() {
        super(Outcome.ReturnToHand);
    }

    public ReturnToHandEffect(final ReturnToHandEffect effect) {
        super(effect);
    }

    @Override
    public ReturnToHandEffect copy() {
        return new ReturnToHandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            MageObject mageObject = source.getSourceObjectIfItStillExists(game);
            if (mageObject != null) {
                Permanent permanent = game.getPermanent(source.getSourceId());
                if (permanent != null) {
                    boolean ret = controller.moveCards(permanent, Zone.HAND, source, game);
                    if (ret) {
                        game.fireEvent(new GameEvent(EventType.MEDITATED, source.getSourceId(), source.getSourceId(), controller.getId()));
                    }
                    return ret;
                }
            }
        }
        return false;
    }
}
