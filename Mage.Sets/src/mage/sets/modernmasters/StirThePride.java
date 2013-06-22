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
package mage.sets.modernmasters;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.BoostControlledEffect;
import mage.abilities.effects.common.continious.GainAbilityControlledEffect;
import mage.abilities.keyword.EntwineAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class StirThePride extends CardImpl<StirThePride> {

    public StirThePride(UUID ownerId) {
        super(ownerId, 30, "Stir the Pride", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{4}{W}");
        this.expansionSetCode = "MMA";

        this.color.setWhite(true);

        // Choose one - 
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(1);
        // Creatures you control get +2/+2 until end of turn;
        this.getSpellAbility().addEffect(new BoostControlledEffect(2,2, Duration.EndOfTurn));
        // or until end of turn, creatures you control gain "Whenever this creature deals damage, you gain that much life."
        Mode mode = new Mode();
        Effect effect = new GainAbilityControlledEffect(new StirThePrideTriggeredAbility(), Duration.EndOfTurn);
        effect.setText("until end of turn, creatures you control gain \"Whenever this creature deals damage, you gain that much life.\"");
        mode.getEffects().add(effect);
        this.getSpellAbility().getModes().addMode(mode);

        // Entwine {1}{W}
        this.addAbility(new EntwineAbility("{1}{W}"));

    }

    public StirThePride(final StirThePride card) {
        super(card);
    }

    @Override
    public StirThePride copy() {
        return new StirThePride(this);
    }
}

class StirThePrideTriggeredAbility extends TriggeredAbilityImpl<StirThePrideTriggeredAbility> {

    public StirThePrideTriggeredAbility() {
            super(Zone.BATTLEFIELD, new StirThePrideGainLifeEffect(), false);
    }

    public StirThePrideTriggeredAbility(final StirThePrideTriggeredAbility ability) {
            super(ability);
    }

    @Override
    public StirThePrideTriggeredAbility copy() {
            return new StirThePrideTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
            if (event.getType().equals(EventType.DAMAGED_CREATURE)
                    || event.getType().equals(GameEvent.EventType.DAMAGED_PLAYER)
                    || event.getType().equals(GameEvent.EventType.DAMAGED_PLANESWALKER)) {
                if (event.getSourceId().equals(this.getSourceId())) {
                    for (Effect effect : this.getEffects()) {
                        effect.setValue("damage", event.getAmount());
                    }
                    return true;
                }

            }
            return false;
    }

    @Override
    public String getRule() {
            return "Whenever {this} deals damage, " + super.getRule();
    }

}

class StirThePrideGainLifeEffect extends OneShotEffect<StirThePrideGainLifeEffect> {

    public StirThePrideGainLifeEffect() {
        super(Outcome.GainLife);
        this.staticText = "you gain that much life";
    }

    public StirThePrideGainLifeEffect(final StirThePrideGainLifeEffect effect) {
        super(effect);
    }

    @Override
    public StirThePrideGainLifeEffect copy() {
        return new StirThePrideGainLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = (Integer) getValue("damage");
        if (amount > 0) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                controller.gainLife(amount, game);
                return true;
            }
        }
        return false;
    }
}
