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
package mage.sets.magic2012;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CreatureEntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public class WarstormSurge extends CardImpl<WarstormSurge> {

    public WarstormSurge(UUID ownerId) {
        super(ownerId, 160, "Warstorm Surge", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{5}{R}");
        this.expansionSetCode = "M12";

        this.color.setRed(true);

        // Whenever a creature enters the battlefield under your control, it deals damage equal to its power to target creature or player.
        Ability ability = new CreatureEntersBattlefieldTriggeredAbility(new WarstormSurgeEffect());
        ability.addTarget(new TargetCreaturePermanent());
        ability.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(ability);
    }

    public WarstormSurge(final WarstormSurge card) {
        super(card);
    }

    @Override
    public WarstormSurge copy() {
        return new WarstormSurge(this);
    }
}

class WarstormSurgeEffect extends OneShotEffect<WarstormSurgeEffect> {

    public WarstormSurgeEffect() {
        super(Outcome.Damage);
        staticText = "it deals damage equal to its power to target creature or player";
    }

    public WarstormSurgeEffect(final WarstormSurgeEffect effect) {
        super(effect);
    }

    @Override
    public WarstormSurgeEffect copy() {
        return new WarstormSurgeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(source.getFirstTarget());
        if (creature != null) {
            int amount = creature.getPower().getValue();
            UUID target = source.getTargets().get(1).getFirstTarget();
            Permanent permanent = game.getPermanent(target);
            if (permanent != null) {
                permanent.damage(amount, source.getSourceId(), game, true, false);
                return true;
            }
            Player player = game.getPlayer(target);
            if (player != null) {
                player.damage(amount, source.getSourceId(), game, false, true);
                return true;
            }
        }
        return false;
    }
}
