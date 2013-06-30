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
package mage.sets.eventide;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.IslandwalkAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class ColdEyedSelkie extends CardImpl<ColdEyedSelkie> {

    public ColdEyedSelkie(UUID ownerId) {
        super(ownerId, 149, "Cold-Eyed Selkie", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{G/U}{G/U}");
        this.expansionSetCode = "EVE";
        this.subtype.add("Merfolk");
        this.subtype.add("Rogue");

        this.color.setBlue(true);
        this.color.setGreen(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Islandwalk
        this.addAbility(new IslandwalkAbility());
        // Whenever Cold-Eyed Selkie deals combat damage to a player, you may draw that many cards.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new ColdEyeSelkieEffect(), true, true));

    }

    public ColdEyedSelkie(final ColdEyedSelkie card) {
        super(card);
    }

    @Override
    public ColdEyedSelkie copy() {
        return new ColdEyedSelkie(this);
    }
}

class ColdEyeSelkieEffect extends OneShotEffect<ColdEyeSelkieEffect> {

    public ColdEyeSelkieEffect() {
        super(Outcome.DrawCard);
        this.staticText = "draw that many cards";
    }

    public ColdEyeSelkieEffect(final ColdEyeSelkieEffect effect) {
        super(effect);
    }

    @Override
    public ColdEyeSelkieEffect copy() {
        return new ColdEyeSelkieEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = (Integer) getValue("damage");
        if (amount > 0) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                controller.drawCards(amount, game);
                return true;
            }
        }
        return false;
    }
}
