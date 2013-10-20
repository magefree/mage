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
package mage.sets.dragonsmaze;

import java.util.UUID;

import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfPreCombatMainPhaseTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ManaEffect;
import mage.cards.CardImpl;
import mage.choices.ChoiceColor;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public class PlasmCapture extends CardImpl<PlasmCapture> {

    public PlasmCapture(UUID ownerId) {
        super(ownerId, 91, "Plasm Capture", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{G}{G}{U}{U}");
        this.expansionSetCode = "DGM";

        this.color.setBlue(true);
        this.color.setGreen(true);

        // Counter target spell. At the beginning of your next precombat main phase, add X mana in any combination of colors to your mana pool, where X is that spell's converted mana cost.
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new PlasmCaptureCounterEffect());
    }

    public PlasmCapture(final PlasmCapture card) {
        super(card);
    }

    @Override
    public PlasmCapture copy() {
        return new PlasmCapture(this);
    }
}

class PlasmCaptureCounterEffect extends OneShotEffect<PlasmCaptureCounterEffect> {

    public PlasmCaptureCounterEffect() {
        super(Outcome.Benefit);
        this.staticText = "Counter target spell. At the beginning of your next precombat main phase, add X mana in any combination of colors to your mana pool, where X is that spell's converted mana cost";
    }

    public PlasmCaptureCounterEffect(final PlasmCaptureCounterEffect effect) {
        super(effect);
    }

    @Override
    public PlasmCaptureCounterEffect copy() {
        return new PlasmCaptureCounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = false;
        Spell spell = game.getStack().getSpell(getTargetPointer().getFirst(game, source));
        if (spell != null) {
            result = game.getStack().counter(getTargetPointer().getFirst(game, source), source.getSourceId(), game);
            int mana = spell.getManaCost().convertedManaCost();
            AtTheBeginOfPreCombatMainPhaseTriggeredAbility delayedAbility =
                    new AtTheBeginOfPreCombatMainPhaseTriggeredAbility(new PlasmCaptureManaEffect(mana), TargetController.YOU);
            delayedAbility.setSourceId(source.getSourceId());
            delayedAbility.setControllerId(source.getControllerId());
            game.addDelayedTriggeredAbility(delayedAbility);
        }
        return result;
    }
}

class PlasmCaptureManaEffect extends ManaEffect<PlasmCaptureManaEffect> {

    int amountOfMana;

    public PlasmCaptureManaEffect(int amountOfMana) {
        super();
        this.amountOfMana = amountOfMana;
        this.staticText = "add X mana in any combination of colors to your mana pool, where X is that spell's converted mana cost";
    }

    public PlasmCaptureManaEffect(final PlasmCaptureManaEffect effect) {
        super(effect);
        this.amountOfMana = effect.amountOfMana;
    }

    @Override
    public PlasmCaptureManaEffect copy() {
        return new PlasmCaptureManaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if(player != null){
            Mana mana = new Mana();
            for(int i = 0; i < amountOfMana; i++){
                ChoiceColor choiceColor = new ChoiceColor();
                while (!player.choose(Outcome.Benefit, choiceColor, game)) {
                    if (!player.isInGame()) {
                        return false;
                    }
                }

                if (choiceColor.getColor().isBlack()) {
                    mana.addBlack();
                } else if (choiceColor.getColor().isBlue()) {
                    mana.addBlue();
                } else if (choiceColor.getColor().isRed()) {
                    mana.addRed();
                } else if (choiceColor.getColor().isGreen()) {
                    mana.addGreen();
                } else if (choiceColor.getColor().isWhite()) {
                    mana.addWhite();
                }
            }


            if (mana != null) {
                player.getManaPool().addMana(mana, game, source);
                return true;
            }

        }
        return false;
    }
}
