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
package mage.sets.alarareborn;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.TargetController;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public class BlitzHellion extends CardImpl<BlitzHellion> {

    public BlitzHellion(UUID ownerId) {
        super(ownerId, 49, "Blitz Hellion", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{R}{G}");
        this.expansionSetCode = "ARB";
        this.subtype.add("Hellion");

        this.color.setRed(true);
        this.color.setGreen(true);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // At the beginning of the end step, Blitz Hellion's owner shuffles it into his or her library.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(Constants.Zone.BATTLEFIELD, new ShuffleSourceEffect(), TargetController.ANY, null, false));
    }

    public BlitzHellion(final BlitzHellion card) {
        super(card);
    }

    @Override
    public BlitzHellion copy() {
        return new BlitzHellion(this);
    }
}

class ShuffleSourceEffect extends OneShotEffect<ShuffleSourceEffect> {

    ShuffleSourceEffect() {
        super(Constants.Outcome.Neutral);
        staticText = "{this}'s owner shuffles it into his or her library";
    }

    ShuffleSourceEffect(final ShuffleSourceEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            permanent.moveToZone(Constants.Zone.LIBRARY, id, game, false);
            player.shuffleLibrary(game);
            return true;
        }
        return false;
    }

    @Override
    public ShuffleSourceEffect copy() {
        return new ShuffleSourceEffect(this);
    }
}