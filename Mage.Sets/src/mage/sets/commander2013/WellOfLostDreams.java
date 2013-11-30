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
package mage.sets.commander2013;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
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
public class WellOfLostDreams extends CardImpl<WellOfLostDreams> {

    public WellOfLostDreams(UUID ownerId) {
        super(ownerId, 271, "Well of Lost Dreams", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.expansionSetCode = "C13";

        // Whenever you gain life, you may pay {X}, where X is less than or equal to the amount of life you gained. If you do, draw X cards.
        this.addAbility(new GainLifeControllerTriggeredAbility(new WellOfLostDreamsEffect(), true, true));
    }

    public WellOfLostDreams(final WellOfLostDreams card) {
        super(card);
    }

    @Override
    public WellOfLostDreams copy() {
        return new WellOfLostDreams(this);
    }
}

class WellOfLostDreamsEffect extends OneShotEffect<WellOfLostDreamsEffect> {

    public WellOfLostDreamsEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may pay {X}, where X is less than or equal to the amount of life you gained. If you do, draw X cards";
    }

    public WellOfLostDreamsEffect(final WellOfLostDreamsEffect effect) {
        super(effect);
    }

    @Override
    public WellOfLostDreamsEffect copy() {
        return new WellOfLostDreamsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int amount = (Integer) getValue("gainedLife");
            if (amount > 0) {
                int xValue = controller.announceXMana(0, amount, "Announce X Value", game, source);
                if (xValue > 0) {
                    if (new GenericManaCost(xValue).pay(source, game, source.getSourceId(), controller.getId(), false)) {
                        game.informPlayers(new StringBuilder(controller.getName()).append(" payed {").append(xValue).append("}").toString());
                        controller.drawCards(xValue, game);
                    } else {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }
}
