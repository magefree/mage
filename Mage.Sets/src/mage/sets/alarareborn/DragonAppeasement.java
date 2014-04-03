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
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author jeffwadsworth
 */
public class DragonAppeasement extends CardImpl<DragonAppeasement> {

    public DragonAppeasement(UUID ownerId) {
        super(ownerId, 115, "Dragon Appeasement", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}{R}{G}");
        this.expansionSetCode = "ARB";

        this.color.setRed(true);
        this.color.setGreen(true);
        this.color.setBlack(true);

        // Skip your draw step.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SkipYourDrawStepEffect()));
        
        // Whenever you sacrifice a creature, you may draw a card.
        this.addAbility(new DragonAppeasementTriggeredAbility());
        
    }

    public DragonAppeasement(final DragonAppeasement card) {
        super(card);
    }

    @Override
    public DragonAppeasement copy() {
        return new DragonAppeasement(this);
    }
}

class SkipYourDrawStepEffect extends ReplacementEffectImpl<SkipYourDrawStepEffect> {

    public SkipYourDrawStepEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "Skip your draw step";
    }

    public SkipYourDrawStepEffect(final SkipYourDrawStepEffect effect) {
        super(effect);
    }

    @Override
    public SkipYourDrawStepEffect copy() {
        return new SkipYourDrawStepEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.DRAW_STEP
                && (event.getPlayerId().equals(source.getControllerId()))) {
            return true;
        }
        return false;
    }
}

class DragonAppeasementTriggeredAbility extends TriggeredAbilityImpl<DragonAppeasementTriggeredAbility> {

    public DragonAppeasementTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), true);
    }

    public DragonAppeasementTriggeredAbility(final DragonAppeasementTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DragonAppeasementTriggeredAbility copy() {
        return new DragonAppeasementTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SACRIFICED_PERMANENT 
                && event.getPlayerId().equals(this.getControllerId())
                && game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD).getCardType().contains(CardType.CREATURE)) {
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you sacrifice a creature, " + super.getRule();
    }
}

