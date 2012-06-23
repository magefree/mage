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
package mage.sets.innistrad;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.SetPowerToughnessSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author BetaSteward
 */
public class TreeOfRedemption extends CardImpl<TreeOfRedemption> {

    public TreeOfRedemption(UUID ownerId) {
        super(ownerId, 207, "Tree of Redemption", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.expansionSetCode = "ISD";
        this.subtype.add("Plant");

        this.color.setGreen(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(13);

        this.addAbility(DefenderAbility.getInstance());

        // {tap}: Exchange your life total with Tree of Redemption's toughness.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new TreeOfRedemptionEffect(), new TapSourceCost()));
    }

    public TreeOfRedemption(final TreeOfRedemption card) {
        super(card);
    }

    @Override
    public TreeOfRedemption copy() {
        return new TreeOfRedemption(this);
    }
}

class TreeOfRedemptionEffect extends OneShotEffect<TreeOfRedemptionEffect> {

    public TreeOfRedemptionEffect() {
        super(Outcome.GainLife);
        staticText = "Exchange your life total with Tree of Redemption's toughness";
    }

    public TreeOfRedemptionEffect(final TreeOfRedemptionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null && player.isLifeTotalCanChange()) {
            Permanent perm = game.getPermanent(source.getSourceId());
            if (perm != null) {
                int amount = perm.getToughness().getValue();
                int life = player.getLife();
                if (life == amount)
                    return false;
                if (life < amount && !player.isCanGainLife())
                    return false;
                if (life > amount && !player.isCanLoseLife())
                    return false;
                player.setLife(amount, game);
                game.addEffect(new SetPowerToughnessSourceEffect(Integer.MIN_VALUE, life, Duration.WhileOnBattlefield), source);
                return true;
            }
        }
        return false;
    }

    @Override
    public TreeOfRedemptionEffect copy() {
        return new TreeOfRedemptionEffect(this);
    }

}