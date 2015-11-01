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
package mage.sets.magic2012;

import java.util.UUID;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;
import mage.target.common.TargetCreatureOrPlayer;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Loki
 */
public class ChandraTheFirebrand extends CardImpl {

    public ChandraTheFirebrand(UUID ownerId) {
        super(ownerId, 124, "Chandra, the Firebrand", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{3}{R}");
        this.expansionSetCode = "M12";
        this.subtype.add("Chandra");

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(3));

        // +1: Chandra, the Firebrand deals 1 damage to target creature or player.
        LoyaltyAbility ability1 = new LoyaltyAbility(new DamageTargetEffect(1), 1);
        ability1.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(ability1);

        // -2: When you cast your next instant or sorcery spell this turn, copy that spell. You may choose new targets for the copy.
        Effect effect = new CreateDelayedTriggeredAbilityEffect(new ChandraTheFirebrandAbility());
        effect.setText("When you cast your next instant or sorcery spell this turn, copy that spell. You may choose new targets for the copy");
        this.addAbility(new LoyaltyAbility(effect, -2));

        // -6: Chandra, the Firebrand deals 6 damage to each of up to six target creatures and/or players
        LoyaltyAbility ability2 = new LoyaltyAbility(new DamageTargetEffect(6, true, "each of up to six target creatures and/or players"), -6);
        ability2.addTarget(new TargetCreatureOrPlayer(0, 6));
        this.addAbility(ability2);
    }

    public ChandraTheFirebrand(final ChandraTheFirebrand card) {
        super(card);
    }

    @Override
    public ChandraTheFirebrand copy() {
        return new ChandraTheFirebrand(this);
    }

}

class ChandraTheFirebrandAbility extends DelayedTriggeredAbility {

    ChandraTheFirebrandAbility() {
        super(new CopyTargetSpellEffect(), Duration.EndOfTurn);
    }

    ChandraTheFirebrandAbility(final ChandraTheFirebrandAbility ability) {
        super(ability);
    }

    @Override
    public ChandraTheFirebrandAbility copy() {
        return new ChandraTheFirebrandAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.getControllerId())) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null && (spell.getCardType().contains(CardType.INSTANT) || spell.getCardType().contains(CardType.SORCERY))) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getTargetId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When you cast your next instant or sorcery spell this turn, copy that spell. You may choose new targets for the copy.";
    }
}
