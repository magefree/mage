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
package mage.sets.lorwyn;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author anonymous
 */
public class ArbiterOfKnollridge extends CardImpl<ArbiterOfKnollridge> {

    public ArbiterOfKnollridge(UUID ownerId) {
        super(ownerId, 2, "Arbiter of Knollridge", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{6}{W}");
        this.expansionSetCode = "LRW";
        this.subtype.add("Giant");
        this.subtype.add("Wizard");

        this.color.setWhite(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        // When Arbiter of Knollridge enters the battlefield, each player's life total becomes the highest life total among all players.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ArbiterOfKnollridgeEffect()));
    }

    public ArbiterOfKnollridge(final ArbiterOfKnollridge card) {
        super(card);
    }

    @Override
    public ArbiterOfKnollridge copy() {
        return new ArbiterOfKnollridge(this);
    }
}

class ArbiterOfKnollridgeEffect extends OneShotEffect<ArbiterOfKnollridgeEffect> {
    ArbiterOfKnollridgeEffect() {
        super(Constants.Outcome.GainLife);
        staticText = "each player's life total becomes the highest life total among all players";
    }

    ArbiterOfKnollridgeEffect(final ArbiterOfKnollridgeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int maxLife = 0;
        for (UUID pid : game.getPlayerList()) {
            Player p = game.getPlayer(pid);
            if (p != null) {
                if (maxLife < p.getLife()) {
                    maxLife = p.getLife();
                }
            }
        }
        for (UUID pid : game.getPlayerList()) {
            Player p = game.getPlayer(pid);
            if (p != null) {
                if (maxLife > p.getLife()) {
                    p.gainLife(maxLife - p.getLife(), game);
                }
            }
        }
        return true;
    }

    @Override
    public ArbiterOfKnollridgeEffect copy() {
        return new ArbiterOfKnollridgeEffect(this);
    }
}
