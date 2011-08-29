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
package mage.sets.newphyrexia;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author North
 */
public class SoulConduit extends CardImpl<SoulConduit> {

    public SoulConduit(UUID ownerId) {
        super(ownerId, 158, "Soul Conduit", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{6}");
        this.expansionSetCode = "NPH";

        // {6}, {tap}: Two target players exchange life totals.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SoulConduitEffect(), new GenericManaCost(6));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public SoulConduit(final SoulConduit card) {
        super(card);
    }

    @Override
    public SoulConduit copy() {
        return new SoulConduit(this);
    }
}

class SoulConduitEffect extends OneShotEffect<SoulConduitEffect> {

    public SoulConduitEffect() {
        super(Outcome.Neutral);
        this.staticText = "Two target players exchange life totals";
    }

    public SoulConduitEffect(final SoulConduitEffect effect) {
        super(effect);
    }

    @Override
    public SoulConduitEffect copy() {
        return new SoulConduitEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player1 = game.getPlayer(source.getFirstTarget());
        Player player2 = game.getPlayer(source.getTargets().get(1).getFirstTarget());
        if (player1 != null && player2 != null) {
            int lifePlayer1 = player1.getLife();
            int lifePlayer2 = player2.getLife();

            player1.setLife(lifePlayer2, game);
            player2.setLife(lifePlayer1, game);
        }
        return false;
    }
}
