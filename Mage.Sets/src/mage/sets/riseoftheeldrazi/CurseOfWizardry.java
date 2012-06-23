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
package mage.sets.riseoftheeldrazi;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.cards.CardImpl;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.choices.ChoiceColor;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public class CurseOfWizardry extends CardImpl<CurseOfWizardry> {

    public CurseOfWizardry(UUID ownerId) {
        super(ownerId, 104, "Curse of Wizardry", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}");
        this.expansionSetCode = "ROE";

        this.color.setBlack(true);

        // As Curse of Wizardry enters the battlefield, choose a color.
        this.addAbility(new EntersBattlefieldAbility(new CurseOfWizardryChooseColorEffect()));

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

class CurseOfWizardryChooseColorEffect extends OneShotEffect<CurseOfWizardryChooseColorEffect> {

    public CurseOfWizardryChooseColorEffect() {
        super(Constants.Outcome.Detriment);
    staticText = "As {this} enters the battlefield, choose a color";
    }

    public CurseOfWizardryChooseColorEffect(final CurseOfWizardryChooseColorEffect effect) {
    super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
    Player player = game.getPlayer(source.getControllerId());
    Permanent curseOfWizardry = game.getPermanent(source.getSourceId());
    if (player != null && curseOfWizardry != null) {
            ChoiceColor colorChoice = new ChoiceColor();
            if (player.choose(Constants.Outcome.Detriment, colorChoice, game)) {
        game.informPlayers(curseOfWizardry.getName() + ": " + player.getName() + " has chosen " + colorChoice.getChoice());
        game.getState().setValue(curseOfWizardry.getId() + "_color", colorChoice.getColor());
            }
    }
    return false;
    }

    @Override
    public CurseOfWizardryChooseColorEffect copy() {
    return new CurseOfWizardryChooseColorEffect(this);
    }
}

class CurseOfWizardryPlayerCastsSpellChosenColorTriggeredAbility extends TriggeredAbilityImpl<CurseOfWizardryPlayerCastsSpellChosenColorTriggeredAbility> {

    public CurseOfWizardryPlayerCastsSpellChosenColorTriggeredAbility() {
    super(Constants.Zone.BATTLEFIELD, new LoseLifeTargetEffect(1), false);
    }

    public CurseOfWizardryPlayerCastsSpellChosenColorTriggeredAbility(final CurseOfWizardryPlayerCastsSpellChosenColorTriggeredAbility ability) {
    super(ability);
    }

    @Override
    public CurseOfWizardryPlayerCastsSpellChosenColorTriggeredAbility copy() {
    return new CurseOfWizardryPlayerCastsSpellChosenColorTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent curseOfWizardry = game.getPermanent(getSourceId());
        if (curseOfWizardry != null) {
            ObjectColor chosenColor = (ObjectColor) game.getState().getValue(curseOfWizardry.getId() + "_color");
            if (chosenColor != null) {
                if (event.getType() == GameEvent.EventType.SPELL_CAST) {
                    Spell spell = game.getStack().getSpell(event.getTargetId());
                    if (spell != null && spell.getColor().shares(chosenColor)) {
                        this.getEffects().get(0).setTargetPointer(new FixedTarget(event.getPlayerId()));
                        return true;
                    }  
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
