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
package mage.sets.vintagemasters;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfMainPhaseDelayedTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfMainPhaseDelayedTriggeredAbility.PhaseSelection;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AddManaToManaPoolTargetControllerEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.target.TargetSpell;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class ManaDrain extends CardImpl {

    public ManaDrain(UUID ownerId) {
        super(ownerId, 78, "Mana Drain", Rarity.MYTHIC, new CardType[]{CardType.INSTANT}, "{U}{U}");
        this.expansionSetCode = "VMA";

        
        // Counter target spell. At the beginning of your next main phase, add {X} to your mana pool, where X is that spell's converted mana cost.
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new ManaDrainCounterEffect());        
    }

    public ManaDrain(final ManaDrain card) {
        super(card);
    }

    @Override
    public ManaDrain copy() {
        return new ManaDrain(this);
    }
}

class ManaDrainCounterEffect extends OneShotEffect {

    public ManaDrainCounterEffect() {
        super(Outcome.Benefit);
        this.staticText = "Counter target spell. At the beginning of your next main phase, add {X} to your mana pool, where X is that spell's converted mana cost";
    }

    public ManaDrainCounterEffect(final ManaDrainCounterEffect effect) {
        super(effect);
    }

    @Override
    public ManaDrainCounterEffect copy() {
        return new ManaDrainCounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(getTargetPointer().getFirst(game, source));
        if (spell != null) {
            game.getStack().counter(getTargetPointer().getFirst(game, source), source.getSourceId(), game);
            // mana gets added also if counter is not successful
            int cmc = spell.getConvertedManaCost();
            Effect effect = new AddManaToManaPoolTargetControllerEffect(new Mana(0,0,0,0,0,cmc,0), "your");
            effect.setTargetPointer(new FixedTarget(source.getControllerId()));
            AtTheBeginOfMainPhaseDelayedTriggeredAbility delayedAbility =
                    new AtTheBeginOfMainPhaseDelayedTriggeredAbility(effect, false, TargetController.YOU, PhaseSelection.NEXT_MAIN);
            delayedAbility.setSourceId(source.getSourceId());
            delayedAbility.setControllerId(source.getControllerId());
            delayedAbility.setSourceObject(source.getSourceObject(game), game);
            game.addDelayedTriggeredAbility(delayedAbility);
            return true;
        }
        return false;
    }
}
