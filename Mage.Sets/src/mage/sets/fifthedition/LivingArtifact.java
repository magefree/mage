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
package mage.sets.fifthedition;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent.EventType;
import mage.game.events.GameEvent;
import mage.target.TargetPermanent;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author LoneFox
 */
public class LivingArtifact extends CardImpl {

    public LivingArtifact(UUID ownerId) {
        super(ownerId, 173, "Living Artifact", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{G}");
        this.expansionSetCode = "5ED";
        this.subtype.add("Aura");

        // Enchant artifact
        TargetPermanent auraTarget = new TargetArtifactPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        // Whenever you're dealt damage, put that many vitality counters on Living Artifact.
        this.addAbility(new LivingArtifactTriggeredAbility());
        // At the beginning of your upkeep, you may remove a vitality counter from Living Artifact. If you do, you gain 1 life.
        this.addAbility(new ConditionalTriggeredAbility(new BeginningOfUpkeepTriggeredAbility(new DoIfCostPaid(new GainLifeEffect(1),
            new RemoveCountersSourceCost(CounterType.VITALITY.createInstance(1))), TargetController.YOU, false),
            new SourceHasCounterCondition(CounterType.VITALITY, 1), "At the beginning of your upkeep, you may remove a vitality counter from {this}. If you do, you gain 1 life"));
    }

    public LivingArtifact(final LivingArtifact card) {
        super(card);
    }

    @Override
    public LivingArtifact copy() {
        return new LivingArtifact(this);
    }
}

class LivingArtifactTriggeredAbility extends TriggeredAbilityImpl {

    public LivingArtifactTriggeredAbility() {
        super(Zone.BATTLEFIELD, new LivingArtifactEffect(), false);
    }

    public LivingArtifactTriggeredAbility(final LivingArtifactTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public LivingArtifactTriggeredAbility copy() {
        return new LivingArtifactTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.getControllerId())) {
            this.getEffects().get(0).setValue("damageAmount", event.getAmount());
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you're dealt damage, put that many charge counters on {this}.";
    }
}

class LivingArtifactEffect extends OneShotEffect {

    public LivingArtifactEffect() {
        super(Outcome.Benefit);
    }

    public LivingArtifactEffect(final LivingArtifactEffect effect) {
        super(effect);
    }

    @Override
    public LivingArtifactEffect copy() {
        return new LivingArtifactEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return new AddCountersSourceEffect(CounterType.VITALITY.createInstance((Integer) this.getValue("damageAmount"))).apply(game, source);
    }
}
