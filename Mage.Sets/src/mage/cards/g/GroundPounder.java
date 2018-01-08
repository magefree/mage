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
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author spjspj
 */
public class GroundPounder extends CardImpl {

    public GroundPounder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // 3G: Roll a six-sided die. Ground Pounder gets +X/+X until end of turn, where X is the result.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GroundPounderEffect(), new ManaCostsImpl("{3}{G}")));

        // Whenever you roll a 5 or higher on a die, Ground Pounder gains trample until end of turn.
        this.addAbility(new GroundPounderTriggeredAbility());
    }

    public GroundPounder(final GroundPounder card) {
        super(card);
    }

    @Override
    public GroundPounder copy() {
        return new GroundPounder(this);
    }
}

class GroundPounderEffect extends OneShotEffect {

    public GroundPounderEffect() {
        super(Outcome.Benefit);
        this.staticText = "Roll a six-sided die. {this} gets +X/+X until end of turn, where X is the result";
    }

    public GroundPounderEffect(final GroundPounderEffect effect) {
        super(effect);
    }

    @Override
    public GroundPounderEffect copy() {
        return new GroundPounderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (controller != null && permanent != null) {
            int amount = controller.rollDice(game, 6);
            game.addEffect(new BoostSourceEffect(amount, amount, Duration.EndOfTurn), source);
            return true;
        }
        return false;
    }
}

class GroundPounderTriggeredAbility extends TriggeredAbilityImpl {

    public GroundPounderTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.EndOfTurn), false);
    }

    public GroundPounderTriggeredAbility(final GroundPounderTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GroundPounderTriggeredAbility copy() {
        return new GroundPounderTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DICE_ROLLED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (this.getControllerId().equals(event.getPlayerId()) && event.getFlag()) {
            if (event.getAmount() >= 5) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you roll a 5 or higher on a die, {this} gains trample until end of turn";
    }
}
