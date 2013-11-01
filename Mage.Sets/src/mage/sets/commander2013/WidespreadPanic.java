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
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class WidespreadPanic extends CardImpl<WidespreadPanic> {

    public WidespreadPanic(UUID ownerId) {
        super(ownerId, 131, "Widespread Panic", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");
        this.expansionSetCode = "C13";

        this.color.setRed(true);

        // Whenever a spell or ability causes its controller to shuffle his or her library, that player puts a card from his or her hand on top of his or her library.
        this.addAbility(new WidespreadPanicTriggeredAbility());
    }

    public WidespreadPanic(final WidespreadPanic card) {
        super(card);
    }

    @Override
    public WidespreadPanic copy() {
        return new WidespreadPanic(this);
    }
}

class WidespreadPanicTriggeredAbility extends TriggeredAbilityImpl<WidespreadPanicTriggeredAbility> {

    public WidespreadPanicTriggeredAbility() {
        super(Zone.BATTLEFIELD, new WidespreadPanicEffect(), false);
    }

    public WidespreadPanicTriggeredAbility(final WidespreadPanicTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public WidespreadPanicTriggeredAbility copy() {
        return new WidespreadPanicTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType().equals(GameEvent.EventType.LIBRARY_SHUFFLED)) {
            for(Effect effect :this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return new StringBuilder("Whenever a spell or ability causes its controller to shuffle his or her library, ").append(super.getRule()).toString();
    }
}

class WidespreadPanicEffect extends OneShotEffect<WidespreadPanicEffect> {

    public WidespreadPanicEffect() {
        super(Outcome.Detriment);
        this.staticText = "that player puts a card from his or her hand on top of his or her library";
    }

    public WidespreadPanicEffect(final WidespreadPanicEffect effect) {
        super(effect);
    }

    @Override
    public WidespreadPanicEffect copy() {
        return new WidespreadPanicEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player shuffler = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (shuffler != null) {
            if (shuffler.getHand().size() > 0) {
                TargetCardInHand target = new TargetCardInHand();
                target.setTargetName("a card from your hand to put on top of your library");
                shuffler.choose(Outcome.Detriment, target, source.getSourceId(), game);
                Card card = shuffler.getHand().get(target.getFirstTarget(), game);
                if (card != null) {
                    return card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
                }
            }
            return true;
        }
        return false;
    }
}
