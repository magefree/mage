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

package mage.sets.riseoftheeldrazi;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Loki
 */
public class PestilenceDemon extends CardImpl<PestilenceDemon> {

    public PestilenceDemon (UUID ownerId) {
        super(ownerId, 124, "Pestilence Demon", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{5}{B}{B}{B}");
        this.expansionSetCode = "ROE";
        this.subtype.add("Demon");
		this.color.setBlack(true);
        this.power = new MageInt(7);
        this.toughness = new MageInt(6);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new PestilenceDemonEffect(), new ManaCostsImpl("{B}")));
    }

    public PestilenceDemon (final PestilenceDemon card) {
        super(card);
    }

    @Override
    public PestilenceDemon copy() {
        return new PestilenceDemon(this);
    }

}

class PestilenceDemonEffect extends OneShotEffect<PestilenceDemonEffect> {
    PestilenceDemonEffect() {
        super(Constants.Outcome.Damage);
        staticText = "Pestilence Demon deals 1 damage to each creature and each player";
    }

    PestilenceDemonEffect(final PestilenceDemonEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID permanentId : game.getBattlefield().getAllPermanentIds()) {
            Permanent p = game.getPermanent(permanentId);
            if (p != null && p.getCardType().contains(CardType.CREATURE)) {
                p.damage(1, source.getSourceId(), game, true, false);
            }
        }
        for (UUID playerId : game.getPlayerList()) {
            Player p = game.getPlayer(playerId);
            if (p != null) {
                p.damage(1, source.getSourceId(), game, true, false);
            }
        }
        return true;
    }

    @Override
    public PestilenceDemonEffect copy() {
        return new PestilenceDemonEffect(this);
    }

}