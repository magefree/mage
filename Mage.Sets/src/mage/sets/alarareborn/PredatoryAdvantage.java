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
package mage.sets.alarareborn;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.TargetController;
import mage.Constants.WatcherScope;
import mage.Constants.Zone;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.game.stack.Spell;
import mage.watchers.Watcher;
import mage.watchers.WatcherImpl;

/**
 *
 * @author jeffwadsworth
 */
public class PredatoryAdvantage extends CardImpl<PredatoryAdvantage> {

    public PredatoryAdvantage(UUID ownerId) {
        super(ownerId, 58, "Predatory Advantage", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}{G}");
        this.expansionSetCode = "ARB";

        this.color.setRed(true);
        this.color.setGreen(true);

        // At the beginning of each opponent's end step, if that player didn't cast a creature spell this turn, put a 2/2 green Lizard creature token onto the battlefield.
        this.addWatcher(new CastCreatureWatcher());
        this.addAbility(new BeginningOfEndStepTriggeredAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new LizardToken()), TargetController.OPPONENT, new DidNotCastCreatureCondition(), false));
    }

    public PredatoryAdvantage(final PredatoryAdvantage card) {
        super(card);
    }

    @Override
    public PredatoryAdvantage copy() {
        return new PredatoryAdvantage(this);
    }
}

class DidNotCastCreatureCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent p = game.getPermanent(source.getSourceId());
        if (p != null) {
            Watcher watcher = game.getState().getWatchers().get("CastCreature", source.getSourceId());
            if (watcher != null && !watcher.conditionMet()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("if that player didn't cast a creature spell this turn");
        return sb.toString();
    }
}

class CastCreatureWatcher extends WatcherImpl<CastCreatureWatcher> {

    public CastCreatureWatcher() {
        super("CastCreature", WatcherScope.CARD);
    }

    public CastCreatureWatcher(final CastCreatureWatcher watcher) {
        super(watcher);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST
                && game.getActivePlayerId().equals(event.getPlayerId())
                && game.getOpponents(controllerId).contains(event.getPlayerId())) {
            Spell spell = (Spell) game.getObject(event.getTargetId());
            if (spell.getCardType().contains(CardType.CREATURE)) {
                condition = true;
            }
        }
    }

    @Override
    public CastCreatureWatcher copy() {
        return new CastCreatureWatcher(this);
    }
}

class LizardToken extends Token {

    public LizardToken() {
        super("Lizard", "2/2 green Lizard creature token onto the battlefield");
        cardType.add(CardType.CREATURE);
        color = ObjectColor.GREEN;
        subtype.add("Lizard");
        power = new MageInt(2);
        toughness = new MageInt(2);
    }
}
