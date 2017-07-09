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

import java.util.ArrayList;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControllerEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author BetaSteward
 */
public class WitchbaneOrb extends CardImpl {

    public WitchbaneOrb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // When Witchbane Orb enters the battlefield, destroy all Curses attached to you.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new WitchbaneOrbEffect()));

        // You have hexproof.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControllerEffect(HexproofAbility.getInstance())));

    }

    public WitchbaneOrb(final WitchbaneOrb card) {
        super(card);
    }

    @Override
    public WitchbaneOrb copy() {
        return new WitchbaneOrb(this);
    }
}

class WitchbaneOrbEffect extends OneShotEffect {

    public WitchbaneOrbEffect() {
        super(Outcome.Protect);
        staticText = "destroy all Curses attached to you";
    }

    public WitchbaneOrbEffect(final WitchbaneOrbEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            ArrayList<Permanent> toDestroy = new ArrayList<>();
            for (UUID attachmentId : controller.getAttachments()) {
                Permanent attachment = game.getPermanent(attachmentId);
                if (attachment != null && attachment.getSubtype(game).contains("Curse")) {
                    toDestroy.add(attachment);
                }
            }
            for (Permanent curse : toDestroy) {
                curse.destroy(source.getSourceId(), game, false);
            }
            return true;
        }
        return false;
    }

    @Override
    public WitchbaneOrbEffect copy() {
        return new WitchbaneOrbEffect(this);
    }

}
