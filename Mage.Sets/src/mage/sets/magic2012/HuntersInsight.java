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
package mage.sets.magic2012;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardControllerEffect;
import mage.abilities.effects.common.continious.GainAbilityTargetEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author North
 */
public class HuntersInsight extends CardImpl<HuntersInsight> {

    public HuntersInsight(UUID ownerId) {
        super(ownerId, 180, "Hunter's Insight", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{2}{G}");
        this.expansionSetCode = "M12";

        this.color.setGreen(true);

        // Choose target creature you control. Whenever that creature deals combat damage to a player or planeswalker this turn, draw that many cards.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(new HuntersInsightTriggeredAbility(), Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    public HuntersInsight(final HuntersInsight card) {
        super(card);
    }

    @Override
    public HuntersInsight copy() {
        return new HuntersInsight(this);
    }
}

class HuntersInsightTriggeredAbility extends TriggeredAbilityImpl<HuntersInsightTriggeredAbility> {

    public HuntersInsightTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, false);
    }

    public HuntersInsightTriggeredAbility(final HuntersInsightTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public HuntersInsightTriggeredAbility copy() {
        return new HuntersInsightTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if ((event.getType() == EventType.DAMAGED_PLAYER || event.getType() == EventType.DAMAGED_PLANESWALKER)
                && event.getSourceId().equals(this.sourceId)) {
            this.addEffect(new DrawCardControllerEffect(event.getAmount()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever this creature deals combat damage to a player or planeswalker, draw that many cards.";
    }
}
