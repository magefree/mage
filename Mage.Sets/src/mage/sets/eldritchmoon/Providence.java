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
package mage.sets.eldritchmoon;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.ChancellorAbility;
import mage.abilities.effects.common.SetPlayerLifeSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author LevelX2
 */
public class Providence extends CardImpl {

    private static String abilityText = "at the beginning of your first upkeep, your life total becomes 26";

    public Providence(UUID ownerId) {
        super(ownerId, 37, "Providence", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{5}{W}{W}");
        this.expansionSetCode = "EMN";

        // You may reveal this card from your opening hand. If you do, at the beginning of your first upkeep, your life total becomes 26.
        Ability ability = new ChancellorAbility(new ProvidenceDelayedTriggeredAbility(), abilityText);
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);
        // Your life total becomes 26.
        this.getSpellAbility().addEffect(new SetPlayerLifeSourceEffect(26));

    }

    public Providence(final Providence card) {
        super(card);
    }

    @Override
    public Providence copy() {
        return new Providence(this);
    }
}

class ProvidenceDelayedTriggeredAbility extends DelayedTriggeredAbility {

    ProvidenceDelayedTriggeredAbility() {
        super(new SetPlayerLifeSourceEffect(26));
    }

    ProvidenceDelayedTriggeredAbility(ProvidenceDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getActivePlayerId().equals(controllerId);
    }

    @Override
    public ProvidenceDelayedTriggeredAbility copy() {
        return new ProvidenceDelayedTriggeredAbility(this);
    }
}
