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
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LoseGameSourceControllerEffect;
import mage.abilities.effects.common.continuous.DontLoseByZeroOrLessLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public class Transcendence extends CardImpl {

    public Transcendence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{W}{W}{W}");

        // You don't lose the game for having 0 or less life.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DontLoseByZeroOrLessLifeEffect(Duration.WhileOnBattlefield)));

        // When you have 20 or more life, you lose the game.
        this.addAbility(new TranscendenceStateTriggeredAbility());

        // Whenever you lose life, you gain 2 life for each 1 life you lost.
        this.addAbility(new TranscendenceLoseLifeTriggeredAbility());
    }

    public Transcendence(final Transcendence card) {
        super(card);
    }

    @Override
    public Transcendence copy() {
        return new Transcendence(this);
    }
}

class TranscendenceStateTriggeredAbility extends StateTriggeredAbility {

    TranscendenceStateTriggeredAbility() {
        super(Zone.BATTLEFIELD, new LoseGameSourceControllerEffect());
    }

    TranscendenceStateTriggeredAbility(final TranscendenceStateTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TranscendenceStateTriggeredAbility copy() {
        return new TranscendenceStateTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player controller = game.getPlayer(this.getControllerId());
        if (controller != null) {
            return controller.getLife() >= 20;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When you have 20 or more life, you lose the game.";
    }
}

class TranscendenceLoseLifeTriggeredAbility extends TriggeredAbilityImpl {

    TranscendenceLoseLifeTriggeredAbility() {
        super(Zone.BATTLEFIELD, new TranscendenceLoseLifeEffect(), false);
    }

    TranscendenceLoseLifeTriggeredAbility(final TranscendenceLoseLifeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TranscendenceLoseLifeTriggeredAbility copy() {
        return new TranscendenceLoseLifeTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.LOST_LIFE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.getControllerId())) {
            for (Effect effect : this.getEffects()) {
                if (effect instanceof TranscendenceLoseLifeEffect) {
                    ((TranscendenceLoseLifeEffect) effect).setAmount(event.getAmount());
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you lose life, you gain 2 life for each 1 life you lost.";
    }
}

class TranscendenceLoseLifeEffect extends OneShotEffect {

    private int amount = 0;

    TranscendenceLoseLifeEffect() {
        super(Outcome.GainLife);
        this.staticText = "you gain 2 life for each 1 life you lost";
    }

    TranscendenceLoseLifeEffect(final TranscendenceLoseLifeEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public TranscendenceLoseLifeEffect copy() {
        return new TranscendenceLoseLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.gainLife(2 * amount, game);
            return true;
        }
        return false;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
