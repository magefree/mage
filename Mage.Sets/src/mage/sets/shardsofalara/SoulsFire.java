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
package mage.sets.shardsofalara;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author North
 */
public class SoulsFire extends CardImpl<SoulsFire> {

    public SoulsFire(UUID ownerId) {
        super(ownerId, 115, "Soul's Fire", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{2}{R}");
        this.expansionSetCode = "ALA";

        this.color.setRed(true);

        // Target creature you control on the battlefield deals damage equal to its power to target creature or player.
        this.getSpellAbility().addEffect(new SoulsFireEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlayer());
    }

    public SoulsFire(final SoulsFire card) {
        super(card);
    }

    @Override
    public SoulsFire copy() {
        return new SoulsFire(this);
    }
}

class SoulsFireEffect extends OneShotEffect<SoulsFireEffect> {

    public SoulsFireEffect() {
        super(Outcome.Damage);
        this.staticText = "Target creature you control on the battlefield deals damage equal to its power to target creature or player";
    }

    public SoulsFireEffect(final SoulsFireEffect effect) {
        super(effect);
    }

    @Override
    public SoulsFireEffect copy() {
        return new SoulsFireEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getFirstTarget());
        if (sourcePermanent == null) {
            sourcePermanent = (Permanent) game.getLastKnownInformation(source.getSourceId(), Constants.Zone.BATTLEFIELD);
        }
        if (sourcePermanent == null) {
            return false;
        }

        UUID targetId = source.getTargets().get(1).getFirstTarget();
        int damage = sourcePermanent.getPower().getValue();

        Permanent permanent = game.getPermanent(targetId);
        if (permanent != null) {
            permanent.damage(damage, sourcePermanent.getId(), game, true, false);
            return true;
        }

        Player player = game.getPlayer(targetId);
        if (player != null) {
            player.damage(damage, sourcePermanent.getId(), game, false, true);
            return true;
        }

        return false;
    }
}
