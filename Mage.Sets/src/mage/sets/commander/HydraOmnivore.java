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
package mage.sets.commander;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DealsDamageToOpponentTriggeredAbility;
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
public class HydraOmnivore extends CardImpl<HydraOmnivore> {

    public HydraOmnivore(UUID ownerId) {
        super(ownerId, 161, "Hydra Omnivore", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");
        this.expansionSetCode = "CMD";
        this.subtype.add("Hydra");

        this.color.setGreen(true);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Whenever Hydra Omnivore deals combat damage to an opponent, it deals that much damage to each other opponent.
        this.addAbility(new DealsDamageToOpponentTriggeredAbility(new HydraOmnivoreEffect(), false, true));
    }

    public HydraOmnivore(final HydraOmnivore card) {
        super(card);
    }

    @Override
    public HydraOmnivore copy() {
        return new HydraOmnivore(this);
    }
}

class HydraOmnivoreEffect extends OneShotEffect<HydraOmnivoreEffect> {

    public HydraOmnivoreEffect() {
        super(Outcome.Benefit);
        this.staticText = "it deals that much damage to each other opponent";
    }

    public HydraOmnivoreEffect(final HydraOmnivoreEffect effect) {
        super(effect);
    }

    @Override
    public HydraOmnivoreEffect copy() {
        return new HydraOmnivoreEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID damagedOpponent = this.getTargetPointer().getFirst(game, source);
        int amount = (Integer) getValue("damage");
        MageObject object = game.getObject(source.getSourceId());
        if (object != null && amount > 0 && damagedOpponent != null) {
            for (UUID playerId :game.getOpponents(source.getControllerId())) {
                if (playerId != damagedOpponent) {
                    Player opponent = game.getPlayer(playerId);
                    if (opponent != null) {
                        int dealtDamage = opponent.damage(amount, source.getSourceId(), game, false, true);
                        game.informPlayers(new StringBuilder(object.getName()).append(" deals ").append(dealtDamage).append(" damage to ").append(opponent.getName()).toString());
                    }
                }
            }
            return true;
        }
        return false;
    }
}
