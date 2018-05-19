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
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ReturnSourceFromGraveyardToBattlefieldEffect extends OneShotEffect {

    private boolean tapped;
    private boolean ownerControl;
    private boolean haste;

    public ReturnSourceFromGraveyardToBattlefieldEffect() {
        this(false);
    }

    public ReturnSourceFromGraveyardToBattlefieldEffect(boolean tapped) {
        this(tapped, true);
    }

    public ReturnSourceFromGraveyardToBattlefieldEffect(boolean tapped, boolean ownerControl) {
        this(tapped, ownerControl, false);
    }

    public ReturnSourceFromGraveyardToBattlefieldEffect(boolean tapped, boolean ownerControl, boolean haste) {
        super(Outcome.PutCreatureInPlay);
        this.tapped = tapped;
        this.ownerControl = ownerControl;
        this.haste = haste;
        setText();
    }

    public ReturnSourceFromGraveyardToBattlefieldEffect(final ReturnSourceFromGraveyardToBattlefieldEffect effect) {
        super(effect);
        this.tapped = effect.tapped;
        this.ownerControl = effect.ownerControl;
        this.haste = effect.haste;
    }

    @Override
    public ReturnSourceFromGraveyardToBattlefieldEffect copy() {
        return new ReturnSourceFromGraveyardToBattlefieldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getSourceId());
        if (card == null) {
            return false;
        }
        Player player;
        if (ownerControl) {
            player = game.getPlayer(card.getOwnerId());
        } else {
            player = game.getPlayer(source.getControllerId());
        }
        if (player == null) {
            return false;
        }
        if (game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD) {
            player.moveCards(card, Zone.BATTLEFIELD, source, game, tapped, false, true, null);
            if (haste) {
                Permanent permanent = game.getPermanent(card.getId());
                if (permanent != null) {
                    ContinuousEffect effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.Custom);
                    effect.setTargetPointer(new FixedTarget(permanent, game));
                    game.addEffect(effect, source);
                }
            }
        }
        return true;
    }

    private void setText() {
        StringBuilder sb = new StringBuilder("return {this} from your graveyard to the battlefield");
        if (tapped) {
            sb.append(" tapped");
        }
        if (ownerControl) {
            sb.append(" under its owner's control");
        }
        staticText = sb.toString();
    }

}
