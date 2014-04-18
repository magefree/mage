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
package mage.sets.journeyintonyx;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.abilities.effects.common.continious.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author LevelX2
 */
public class KnowledgeAndPower extends CardImpl<KnowledgeAndPower> {

    public KnowledgeAndPower(UUID ownerId) {
        super(ownerId, 101, "Knowledge and Power", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{4}{R}");
        this.expansionSetCode = "JOU";

        this.color.setRed(true);

        // Whenever you scry, you may pay 2. If you do. Knowledge and Power deals 2 damage to target creature or player.
        this.addAbility(new ScryTriggeredAbility() );

    }

    public KnowledgeAndPower(final KnowledgeAndPower card) {
        super(card);
    }

    @Override
    public KnowledgeAndPower copy() {
        return new KnowledgeAndPower(this);
    }
}

class ScryTriggeredAbility extends TriggeredAbilityImpl<ScryTriggeredAbility> {

    public ScryTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DoIfCostPaid(new DamageTargetEffect(2), new GenericManaCost(2)), false);
        this.addTarget(new TargetCreatureOrPlayer(true));
    }

    public ScryTriggeredAbility(final ScryTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ScryTriggeredAbility copy() {
        return new ScryTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType().equals(EventType.SCRY) && event.getPlayerId().equals(this.getControllerId())) {
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you scry, " + super.getRule();
    }
}
