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
package mage.cards.c;

import mage.ObjectColor;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public class CurseOfWizardry extends CardImpl {

    public CurseOfWizardry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{B}{B}");

        // As Curse of Wizardry enters the battlefield, choose a color.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseColorEffect(Outcome.Neutral)));

        // Whenever a player casts a spell of the chosen color, that player loses 1 life.
        this.addAbility(new CurseOfWizardryPlayerCastsSpellChosenColorTriggeredAbility());

    }

    public CurseOfWizardry(final CurseOfWizardry card) {
        super(card);
    }

    @Override
    public CurseOfWizardry copy() {
        return new CurseOfWizardry(this);
    }
}

class CurseOfWizardryPlayerCastsSpellChosenColorTriggeredAbility extends TriggeredAbilityImpl {

    public CurseOfWizardryPlayerCastsSpellChosenColorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new LoseLifeTargetEffect(1), false);
    }

    public CurseOfWizardryPlayerCastsSpellChosenColorTriggeredAbility(final CurseOfWizardryPlayerCastsSpellChosenColorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CurseOfWizardryPlayerCastsSpellChosenColorTriggeredAbility copy() {
        return new CurseOfWizardryPlayerCastsSpellChosenColorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent curseOfWizardry = game.getPermanent(getSourceId());
        if (curseOfWizardry != null) {
            ObjectColor chosenColor = (ObjectColor) game.getState().getValue(curseOfWizardry.getId() + "_color");
            if (chosenColor != null) {
                Spell spell = game.getStack().getSpell(event.getTargetId());
                if (spell != null && spell.getColor(game).shares(chosenColor)) {
                    this.getEffects().get(0).setTargetPointer(new FixedTarget(event.getPlayerId()));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a player casts a spell of the chosen color, that player loses 1 life.";
    }
}
