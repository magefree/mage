/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/
package mage.sets.championsofkamigawa;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardControllerEffect;
import mage.cards.CardImpl;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;

public class GlimpseOfNature extends CardImpl<GlimpseOfNature> {

    public GlimpseOfNature (UUID ownerId) {
        super(ownerId, 210, "Glimpse of Nature", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{G}");
        this.expansionSetCode = "CHK";
        this.color.setGreen(true);

        // Whenever you cast a creature spell this turn, draw a card.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new GlimpseOfNatureTriggeredAbility()));
    }

    public GlimpseOfNature (final GlimpseOfNature card) {
        super(card);
    }

    @Override
    public GlimpseOfNature copy() {
        return new GlimpseOfNature(this);
    }

}

class GlimpseOfNatureTriggeredAbility extends DelayedTriggeredAbility<GlimpseOfNatureTriggeredAbility> {

    private final static FilterSpell filter = new FilterSpell();
    static {
            filter.add(new CardTypePredicate(CardType.CREATURE));
    }

    public GlimpseOfNatureTriggeredAbility() {
        super(new DrawCardControllerEffect(1), Constants.Duration.EndOfTurn, false);
    }

    public GlimpseOfNatureTriggeredAbility(GlimpseOfNatureTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST && event.getPlayerId().equals(this.getControllerId())) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null && filter.match(spell, game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public GlimpseOfNatureTriggeredAbility copy() {
        return new GlimpseOfNatureTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever you cast a creature spell this turn, " + modes.getText();
    }
}