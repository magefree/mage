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
package mage.sets.planarchaos;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public class RoilingHorror extends CardImpl {

    public RoilingHorror(UUID ownerId) {
        super(ownerId, 79, "Roiling Horror", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.expansionSetCode = "PLC";
        this.subtype.add("Horror");

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Roiling Horror's power and toughness are each equal to your life total minus the life total of an opponent with the most life.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetPowerToughnessSourceEffect(new RoilingHorrorDynamicValue(), Duration.EndOfGame)));

        // Suspend X-{X}{B}{B}{B}. X can't be 0.
        this.addAbility(new SuspendAbility(Integer.MAX_VALUE, new ManaCostsImpl("{B}{B}{B}"), this, true));
        
        // Whenever a time counter is removed from Roiling Horror while it's exiled, target player loses 1 life and you gain 1 life.
        this.addAbility(new RoilingHorrorTriggeredAbility());

    }

    public RoilingHorror(final RoilingHorror card) {
        super(card);
    }

    @Override
    public RoilingHorror copy() {
        return new RoilingHorror(this);
    }
}

class RoilingHorrorTriggeredAbility extends TriggeredAbilityImpl {

    public RoilingHorrorTriggeredAbility() {
        super(Zone.EXILED, new LoseLifeTargetEffect(1), false);
        this.addTarget(new TargetPlayer());
        Effect effect = new GainLifeEffect(1);
        effect.setText("and you gain 1 life");
        this.addEffect(effect);
    }

    public RoilingHorrorTriggeredAbility(final RoilingHorrorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RoilingHorrorTriggeredAbility copy() {
        return new RoilingHorrorTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return EventType.COUNTER_REMOVED.equals(event.getType()) && event.getData().equals(CounterType.TIME.getName()) && event.getTargetId().equals(this.getSourceId());
    }

    @Override
    public String getRule() {
        return "Whenever a time counter is removed from {this} while it's exiled, " + super.getRule();
    }

}

class RoilingHorrorDynamicValue implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int opponentsMostLife = Integer.MIN_VALUE;
        Player controller = game.getPlayer(sourceAbility.getControllerId());
        if (controller != null) {
            for (UUID playerUUID : controller.getInRange()) {
                if (controller.hasOpponent(playerUUID, game)) {
                    Player opponent = game.getPlayer(playerUUID);
                    if (opponent != null && opponent.getLife() > opponentsMostLife) {
                        opponentsMostLife = opponent.getLife();
                    }
                }
            }
            return controller.getLife() - opponentsMostLife;
        }
        return 0;
    }

    @Override
    public DynamicValue copy() {
        return this;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "your life total minus the life total of an opponent with the most life";
    }
}
