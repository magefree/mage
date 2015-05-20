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
package mage.sets.betrayersofkamigawa;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class ShinkaGatekeeper extends CardImpl {

    public ShinkaGatekeeper(UUID ownerId) {
        super(ownerId, 117, "Shinka Gatekeeper", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.expansionSetCode = "BOK";
        this.subtype.add("Ogre");
        this.subtype.add("Warrior");

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever Shinka Gatekeeper is dealt damage, it deals that much damage to you.
        this.addAbility(new DealtDamageToSourceTriggeredAbility(Zone.BATTLEFIELD, new ShinkaGatekeeperDealDamageEffect(), false));
    }

    public ShinkaGatekeeper(final ShinkaGatekeeper card) {
        super(card);
    }

    @Override
    public ShinkaGatekeeper copy() {
        return new ShinkaGatekeeper(this);
    }
}


class ShinkaGatekeeperDealDamageEffect extends OneShotEffect {

    public ShinkaGatekeeperDealDamageEffect() {
        super(Outcome.Damage);
        this.staticText = "it deals that much damage to you";
    }

    public ShinkaGatekeeperDealDamageEffect(final ShinkaGatekeeperDealDamageEffect effect) {
        super(effect);
    }

    @Override
    public ShinkaGatekeeperDealDamageEffect copy() {
        return new ShinkaGatekeeperDealDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = (Integer) getValue("damage");
        if (amount > 0) {
            Player player = game.getPlayer(source.getControllerId());
            if (player != null) {
                player.damage(amount, source.getSourceId(), game, false, true);
                return true;
            }
        }
        return false;
    }
}
