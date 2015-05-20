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
package mage.sets.futuresight;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.HellbentCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousRuleModifyingEffect;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author emerald000
 */
public class GibberingDescent extends CardImpl {

    public GibberingDescent(UUID ownerId) {
        super(ownerId, 66, "Gibbering Descent", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{4}{B}{B}");
        this.expansionSetCode = "FUT";


        // At the beginning of each player's upkeep, that player loses 1 life and discards a card.
        Effect effect = new LoseLifeTargetEffect(1);
        effect.setText("that player loses 1 life");
        Ability ability = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, effect, TargetController.ANY, false, true);
        effect = new DiscardTargetEffect(1);
        effect.setText("and discards a card");
        ability.addEffect(effect);
        this.addAbility(ability);
        
        // Hellbent - Skip your upkeep step if you have no cards in hand.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousRuleModifyingEffect(
                new GibberingDescentSkipUpkeepEffect(),
                HellbentCondition.getInstance())));
        
        // Madness {2}{B}{B}
        this.addAbility(new MadnessAbility(this, new ManaCostsImpl<>("{2}{B}{B}")));
    }

    public GibberingDescent(final GibberingDescent card) {
        super(card);
    }

    @Override
    public GibberingDescent copy() {
        return new GibberingDescent(this);
    }
}

class GibberingDescentSkipUpkeepEffect extends ContinuousRuleModifyingEffectImpl {

    GibberingDescentSkipUpkeepEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        this.staticText = "Hellbent - Skip your upkeep step if you have no cards in hand";
    }

    GibberingDescentSkipUpkeepEffect(final GibberingDescentSkipUpkeepEffect effect) {
        super(effect);
    }

    @Override
    public GibberingDescentSkipUpkeepEffect copy() {
        return new GibberingDescentSkipUpkeepEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.UPKEEP_STEP;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getPlayerId().equals(source.getControllerId());
    }
}