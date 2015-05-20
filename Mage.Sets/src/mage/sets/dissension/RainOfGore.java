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
package mage.sets.dissension;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import static mage.game.events.GameEvent.EventType.GAIN_LIFE;
import mage.game.stack.StackObject;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class RainOfGore extends CardImpl {

    public RainOfGore(UUID ownerId) {
        super(ownerId, 126, "Rain of Gore", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{B}{R}");
        this.expansionSetCode = "DIS";


        // If a spell or ability would cause its controller to gain life, that player loses that much life instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new RainOfGoreEffect()));

    }

    public RainOfGore(final RainOfGore card) {
        super(card);
    }

    @Override
    public RainOfGore copy() {
        return new RainOfGore(this);
    }
}


class RainOfGoreEffect extends ReplacementEffectImpl {

    public RainOfGoreEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If a spell or ability would cause its controller to gain life, that player loses that much life instead";
    }

    public RainOfGoreEffect(final RainOfGoreEffect effect) {
        super(effect);
    }

    @Override
    public RainOfGoreEffect copy() {
        return new RainOfGoreEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        if (player != null) {
            player.loseLife(event.getAmount(), game);
        }
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        switch (event.getType()) {
            case GAIN_LIFE:
                if (!game.getStack().isEmpty()) {
                    StackObject stackObject = game.getStack().getFirst();
                    if (stackObject != null) {
                        return stackObject.getControllerId().equals(event.getPlayerId());
                    }
                }
        }
        return false;
    }
}