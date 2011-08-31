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
package mage.sets.newphyrexia;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.PhyrexianManaCost;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCreatureOrPlayer;
import mage.target.targetpointer.FixedTarget;

/**
 * @author Loki
 */
public class RageExtractor extends CardImpl<RageExtractor> {

    public RageExtractor(UUID ownerId) {
        super(ownerId, 91, "Rage Extractor", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{4}{RP}");
        this.expansionSetCode = "NPH";

        this.color.setRed(true);

        this.addAbility(new RageExtractorTriggeredAbility());
    }

    public RageExtractor(final RageExtractor card) {
        super(card);
    }

    @Override
    public RageExtractor copy() {
        return new RageExtractor(this);
    }
}

class RageExtractorTriggeredAbility extends TriggeredAbilityImpl<RageExtractorTriggeredAbility> {
    RageExtractorTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new DamageTargetEffect(0));
        this.addTarget(new TargetCreatureOrPlayer());
    }

    RageExtractorTriggeredAbility(final RageExtractorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RageExtractorTriggeredAbility copy() {
        return new RageExtractorTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST && event.getPlayerId().equals(this.controllerId)) {
            Card card = game.getCard(event.getSourceId());
            if (card != null) {
                for (ManaCost cost : card.getManaCost()) {
                    if (cost instanceof PhyrexianManaCost) {
                        ((DamageTargetEffect)getEffects().get(0)).setAmount(new StaticValue(card.getManaCost().convertedManaCost()));
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you cast a spell with p in its mana cost, {this} deals damage equal to that spell's converted mana cost to target creature or player.";
    }
}
