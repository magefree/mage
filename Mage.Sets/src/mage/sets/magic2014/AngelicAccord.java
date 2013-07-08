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
package mage.sets.magic2014;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.IntCompareCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.token.AngelToken;
import mage.watchers.common.PlayerGainedLifeWatcher;

/**
 *
 * @author jeffwadsworth
 */
public class AngelicAccord extends CardImpl<AngelicAccord> {

    public AngelicAccord(UUID ownerId) {
        super(ownerId, 3, "Angelic Accord", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");
        this.expansionSetCode = "M14";

        this.color.setWhite(true);

        // At the beginning of each end step, if you gained 4 or more life this turn, put a 4/4 white Angel creature token with flying onto the battlefield.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new AngelToken()), TargetController.ANY, new YouGainedLifeCondition(Condition.ComparisonType.GreaterThan, 3), false));
        this.addWatcher(new PlayerGainedLifeWatcher());
    }

    public AngelicAccord(final AngelicAccord card) {
        super(card);
    }

    @Override
    public AngelicAccord copy() {
        return new AngelicAccord(this);
    }
}

class YouGainedLifeCondition extends IntCompareCondition {

    public YouGainedLifeCondition(Condition.ComparisonType type, int value) {
        super(type, value);
    }

    @Override
    protected int getInputValue(Game game, Ability source) {
        int gainedLife = 0;
        PlayerGainedLifeWatcher watcher = (PlayerGainedLifeWatcher) game.getState().getWatchers().get("PlayerGainedLifeWatcher");
        if (watcher != null) {
            gainedLife = watcher.getLiveGained(source.getControllerId());
        }
        return gainedLife;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("if you gained 4 or more life this turn ");
        return sb.toString();
    }
}
