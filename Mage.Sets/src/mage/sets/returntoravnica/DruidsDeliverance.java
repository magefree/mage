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
package mage.sets.returntoravnica;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.PopulateEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;

/**
 *
 * @author LevleX2
 */
public class DruidsDeliverance extends CardImpl<DruidsDeliverance> {

    public DruidsDeliverance(UUID ownerId) {
        super(ownerId, 123, "Druid's Deliverance", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{G}");
        this.expansionSetCode = "RTR";

        this.color.setGreen(true);

        // Prevent all combat damage that would be dealt to you this turn. Populate.
        // (Put a token onto the battlefield that's a copy of a creature token you control.)
        this.getSpellAbility().addEffect(new DruidsDeliverancePreventCombatDamageEffect());
        this.getSpellAbility().addEffect(new PopulateEffect());
    }

    public DruidsDeliverance(final DruidsDeliverance card) {
        super(card);
    }

    @Override
    public DruidsDeliverance copy() {
        return new DruidsDeliverance(this);
    }
}

class DruidsDeliverancePreventCombatDamageEffect extends PreventionEffectImpl<DruidsDeliverancePreventCombatDamageEffect> {

    public DruidsDeliverancePreventCombatDamageEffect() {
            super(Duration.EndOfTurn);
            staticText = "Prevent all combat damage that would be dealt to you this turn";
    }

    public DruidsDeliverancePreventCombatDamageEffect(final DruidsDeliverancePreventCombatDamageEffect effect) {
            super(effect);
    }

    @Override
    public DruidsDeliverancePreventCombatDamageEffect copy() {
            return new DruidsDeliverancePreventCombatDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
            return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
            GameEvent preventEvent = new GameEvent(GameEvent.EventType.PREVENT_DAMAGE, source.getFirstTarget(), source.getId(), source.getControllerId(), event.getAmount(), false);
            if (!game.replaceEvent(preventEvent)) {
                int damage = event.getAmount();
                event.setAmount(0);
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE, source.getFirstTarget(), source.getId(), source.getControllerId(), damage));
                return true;
            }
            return false;
        }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
            if (super.applies(event, source, game)) {
                DamageEvent damageEvent = (DamageEvent) event;
                if (event.getTargetId().equals(source.getControllerId()) && damageEvent.isCombatDamage()) {
                    return true;
                }
            }
            return false;
    }

}