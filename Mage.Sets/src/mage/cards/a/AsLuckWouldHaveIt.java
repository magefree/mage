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
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.Counter;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author spjspj
 */
public class AsLuckWouldHaveIt extends CardImpl {

    static final String rule = "put a number of luck counters on {this} equal to the result. Then if there are 100 or more luck counters on {this}, you win the game.";

    public AsLuckWouldHaveIt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");

        // Hexproof
        this.addAbility(HexproofAbility.getInstance());

        // Whenever you roll a die, put a number of luck counters on As Luck Would Have It equal to the result. Then if there are 100 or more luck counters on As Luck Would Have It, you win the game.
        this.addAbility(new AsLuckWouldHaveItTriggeredAbility());
    }

    public AsLuckWouldHaveIt(final AsLuckWouldHaveIt card) {
        super(card);
    }

    @Override
    public AsLuckWouldHaveIt copy() {
        return new AsLuckWouldHaveIt(this);
    }
}

class AsLuckWouldHaveItTriggeredAbility extends TriggeredAbilityImpl {

    public AsLuckWouldHaveItTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AsLuckWouldHaveItEffect(), false);
    }

    public AsLuckWouldHaveItTriggeredAbility(final AsLuckWouldHaveItTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AsLuckWouldHaveItTriggeredAbility copy() {
        return new AsLuckWouldHaveItTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DICE_ROLLED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (this.getControllerId().equals(event.getPlayerId()) && event.getFlag()) {
            for (Effect effect : this.getEffects()) {
                effect.setValue("rolled", event.getAmount());
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you roll a die, " + super.getRule();
    }
}

class AsLuckWouldHaveItEffect extends OneShotEffect {

    public AsLuckWouldHaveItEffect() {
        super(Outcome.Benefit);
        this.staticText = "put a number of luck counters on {this} equal to the result. Then if there are 100 or more luck counters on {this}, you win the game.";
    }

    public AsLuckWouldHaveItEffect(final AsLuckWouldHaveItEffect effect) {
        super(effect);
    }

    @Override
    public AsLuckWouldHaveItEffect copy() {
        return new AsLuckWouldHaveItEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (controller != null && permanent != null) {
            if (getValue("rolled") != null) {
                int amount = (Integer) getValue("rolled");
                permanent.addCounters(new Counter("luck", amount), source, game);

                if (permanent.getCounters(game).getCount("luck") >= 100) {
                    Player player = game.getPlayer(permanent.getControllerId());
                    if (player != null) {
                        player.won(game);
                    }
                }

                return true;
            }
        }
        return false;

    }
}
