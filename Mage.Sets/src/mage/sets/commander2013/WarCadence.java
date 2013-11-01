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
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class WarCadence extends CardImpl<WarCadence> {

    public WarCadence(UUID ownerId) {
        super(ownerId, 128, "War Cadence", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");
        this.expansionSetCode = "C13";

        this.color.setRed(true);

        // {X}{R}: This turn, creatures can't block unless their controller pays {X} for each blocking creature he or she controls.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new WarCadenceReplacementEffect(), new ManaCostsImpl("{X}{R}") ));

    }

    public WarCadence(final WarCadence card) {
        super(card);
    }

    @Override
    public WarCadence copy() {
        return new WarCadence(this);
    }
}

class WarCadenceReplacementEffect extends ReplacementEffectImpl<WarCadenceReplacementEffect> {

    DynamicValue xCosts = new ManacostVariableValue();

    WarCadenceReplacementEffect ( ) {
        super(Duration.EndOfTurn, Outcome.Neutral);
        staticText = "This turn, creatures can't block unless their controller pays {X} for each blocking creature he or she controls";
    }

    WarCadenceReplacementEffect ( WarCadenceReplacementEffect effect ) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        if (player != null) {
            int amount = xCosts.calculate(game, source);
            if (amount > 0) {
                String mana = new StringBuilder("{").append(amount).append("}").toString();
                ManaCostsImpl cost = new ManaCostsImpl(mana);
                if ( cost.canPay(source.getSourceId(), event.getPlayerId(), game) &&
                    player.chooseUse(Outcome.Benefit, new StringBuilder("Pay ").append(mana).append(" to declare blocker?").toString(), game) ) {
                    if (cost.payOrRollback(source, game, source.getSourceId(), event.getPlayerId())) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType().equals(GameEvent.EventType.DECLARE_BLOCKER)) {
            return true;
        }
        return false;
    }

    @Override
    public WarCadenceReplacementEffect copy() {
        return new WarCadenceReplacementEffect(this);
    }

}
