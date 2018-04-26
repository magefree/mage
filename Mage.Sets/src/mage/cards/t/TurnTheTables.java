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
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectData;
import mage.abilities.effects.common.PreventDamageToControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetAttackingCreature;

/**
 *
 * @author LevelX2
 */
public class TurnTheTables extends CardImpl {

    public TurnTheTables(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{W}{W}");

        // All combat damage that would be dealt to you this turn is dealt to target attacking creature instead.
        this.getSpellAbility().addEffect(new TurnTheTablesEffect());
        this.getSpellAbility().addTarget(new TargetAttackingCreature());
    }

    public TurnTheTables(final TurnTheTables card) {
        super(card);
    }

    @Override
    public TurnTheTables copy() {
        return new TurnTheTables(this);
    }
}

class TurnTheTablesEffect extends PreventDamageToControllerEffect {

    public TurnTheTablesEffect() {
        super(Duration.EndOfTurn, true, false, Integer.MAX_VALUE);
        staticText = "All combat damage that would be dealt to you this turn is dealt to target attacking creature instead";
    }

    public TurnTheTablesEffect(final TurnTheTablesEffect effect) {
        super(effect);
    }

    @Override
    public TurnTheTablesEffect copy() {
        return new TurnTheTablesEffect(this);
    }

    @Override
    protected PreventionEffectData preventDamageAction(GameEvent event, Ability source, Game game) {
        PreventionEffectData preventionEffectData = super.preventDamageAction(event, source, game);
        int damage = preventionEffectData.getPreventedDamage();
        if (damage > 0) {
            Permanent attackingCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (attackingCreature != null) {
                attackingCreature.damage(damage, source.getSourceId(), game, false, true);
            }
        }
        return preventionEffectData;
    }

}
