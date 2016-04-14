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
package mage.sets.battleforzendikar;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.keyword.AwakenAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public class EncirclingFissure extends CardImpl {

    public EncirclingFissure(UUID ownerId) {
        super(ownerId, 23, "Encircling Fissure", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{2}{W}");
        this.expansionSetCode = "BFZ";

        // Prevent all combat damage that would be dealt this turn by creatures target opponent controls.
        this.getSpellAbility().addEffect(new EncirclingFissurePreventEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());

        // Awaken 2 —{4}{W}</i>
        this.addAbility(new AwakenAbility(this, 2, "{4}{W}"));
    }

    public EncirclingFissure(final EncirclingFissure card) {
        super(card);
    }

    @Override
    public EncirclingFissure copy() {
        return new EncirclingFissure(this);
    }
}

class EncirclingFissurePreventEffect extends PreventionEffectImpl {

    public EncirclingFissurePreventEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, true, false);
        staticText = "Prevent all combat damage that would be dealt this turn by creatures target opponent controls";
    }

    public EncirclingFissurePreventEffect(final EncirclingFissurePreventEffect effect) {
        super(effect);
    }

    @Override
    public EncirclingFissurePreventEffect copy() {
        return new EncirclingFissurePreventEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game) && event instanceof DamageEvent && event.getAmount() > 0) {
            DamageEvent damageEvent = (DamageEvent) event;
            if (damageEvent.isCombatDamage()) {
                Permanent permanent = game.getPermanent(damageEvent.getSourceId());
                if (permanent != null
                        && permanent.getCardType().contains(CardType.CREATURE)
                        && permanent.getControllerId().equals(getTargetPointer().getFirst(game, source))) {
                    return true;
                }
            }
        }
        return false;
    }
}
