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
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author fireshoes
 */
public class TreeOfPerdition extends CardImpl {

    public TreeOfPerdition(UUID ownerId) {
        super(ownerId, 109, "Tree of Perdition", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.expansionSetCode = "EMN";
        this.subtype.add("Plant");
        this.power = new MageInt(0);
        this.toughness = new MageInt(13);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // {tap}: Exchange target opponent's life total with Tree of Perdition's toughness.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TreeOfPerditionEffect(), new TapSourceCost());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    public TreeOfPerdition(final TreeOfPerdition card) {
        super(card);
    }

    @Override
    public TreeOfPerdition copy() {
        return new TreeOfPerdition(this);
    }
}

class TreeOfPerditionEffect extends OneShotEffect {

    public TreeOfPerditionEffect() {
        super(Outcome.Neutral);
        staticText = "Exchange target opponent's life total with Tree of Perdition's toughness";
    }

    public TreeOfPerditionEffect(final TreeOfPerditionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (opponent != null && opponent.isLifeTotalCanChange()) {
            Permanent perm = game.getPermanent(source.getSourceId());
            if (perm != null) {
                int amount = perm.getToughness().getValue();
                int life = opponent.getLife();
                if (life == amount) {
                    return false;
                }
                if (life < amount && !opponent.isCanGainLife()) {
                    return false;
                }
                if (life > amount && !opponent.isCanLoseLife()) {
                    return false;
                }
                opponent.setLife(amount, game);
                perm.getToughness().modifyBaseValue(life);
                // game.addEffect(new SetPowerToughnessSourceEffect(Integer.MIN_VALUE, life, Duration.Custom, SubLayer.SetPT_7b), source);
                return true;
            }
        }
        return false;
    }

    @Override
    public TreeOfPerditionEffect copy() {
        return new TreeOfPerditionEffect(this);
    }

}
