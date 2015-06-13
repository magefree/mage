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
package mage.sets.mercadianmasques;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.token.InsectToken;
import mage.players.Player;

/**
 *
 * @author LoneFox

 */
public class SaberAnts extends CardImpl {

    public SaberAnts(UUID ownerId) {
        super(ownerId, 267, "Saber Ants", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.expansionSetCode = "MMQ";
        this.subtype.add("Insect");
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever Saber Ants is dealt damage, you may put that many 1/1 green Insect creature tokens onto the battlefield.
        this.addAbility(new DealtDamageToSourceTriggeredAbility(Zone.BATTLEFIELD, new SaberAntsEffect(), true));
    }

    public SaberAnts(final SaberAnts card) {
        super(card);
    }

    @Override
    public SaberAnts copy() {
        return new SaberAnts(this);
    }
}

class SaberAntsEffect extends OneShotEffect {

    public SaberAntsEffect() {
        super(Outcome.Benefit);
        this.staticText = "Whenever Saber Ants is dealt damage, you may put that many 1/1 green Insect creature tokens onto the battlefield.";
    }

    public SaberAntsEffect(final SaberAntsEffect effect) {
        super(effect);
    }

    @Override
    public SaberAntsEffect copy() {
        return new SaberAntsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int damage = (Integer) this.getValue("damage");
            return new CreateTokenEffect(new InsectToken(), damage).apply(game, source);
        }
        return false;
    }
}
