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

package mage.sets.scarsofmirrodin;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterArtifactCard;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Loki
 */
public class Lifesmith extends CardImpl<Lifesmith> {

    private static final FilterArtifactCard filter = new FilterArtifactCard("an artifact spell");

    public Lifesmith (UUID ownerId) {
        super(ownerId, 124, "Lifesmith", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.expansionSetCode = "SOM";
        this.subtype.add("Human");
        this.subtype.add("Artificer");

		this.color.setGreen(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        this.addAbility(new SpellCastTriggeredAbility(new LifesmithEffect(), filter, false));
    }

    public Lifesmith (final Lifesmith card) {
        super(card);
    }

    @Override
    public Lifesmith copy() {
        return new Lifesmith(this);
    }
}

class LifesmithEffect extends OneShotEffect<LifesmithEffect> {
    LifesmithEffect() {
        super(Constants.Outcome.GainLife);
        staticText = "you may pay {1}. If you do, you gain 3 life";
    }

    LifesmithEffect(final LifesmithEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Cost cost = new GenericManaCost(1);
        cost.clearPaid();
        if (cost.pay(source, game, source.getId(), source.getControllerId(), false)) {
            Player player = game.getPlayer(source.getControllerId());
            if (player != null) {
                player.gainLife(3, game);
                return true;
            }
        }
        return false;
    }

    @Override
    public LifesmithEffect copy() {
        return new LifesmithEffect(this);
    }

}