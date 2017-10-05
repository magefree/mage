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
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.constants.SubType;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagePlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author TheElk801
 */
public class WeatheredBodyguards extends CardImpl {

    public WeatheredBodyguards(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // As long as Weathered Bodyguards is untapped, all combat damage that would be dealt to you by unblocked creatures is dealt to Weathered Bodyguards instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new WeatheredBodyguardsEffect()));

        // Morph {3}{W}
        this.addAbility(new MorphAbility(this, new ManaCostsImpl("{3}{W}")));

    }

    public WeatheredBodyguards(final WeatheredBodyguards card) {
        super(card);
    }

    @Override
    public WeatheredBodyguards copy() {
        return new WeatheredBodyguards(this);
    }
}

class WeatheredBodyguardsEffect extends ReplacementEffectImpl {

    WeatheredBodyguardsEffect() {
        super(Duration.WhileOnBattlefield, Outcome.RedirectDamage);
        staticText = "As long as {this} is untapped, all combat damage that would be dealt to you by unblocked creatures is dealt to {this} instead";
    }

    WeatheredBodyguardsEffect(final WeatheredBodyguardsEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        DamagePlayerEvent damageEvent = (DamagePlayerEvent) event;
        Permanent damager = game.getPermanentOrLKIBattlefield(damageEvent.getSourceId());
        Permanent p = game.getPermanent(source.getSourceId());
        if (p != null && !p.isTapped() && damageEvent.isCombatDamage() && damager != null && damager.isAttacking() && !damager.isBlocked(game)) {
            p.damage(damageEvent.getAmount(), event.getSourceId(), game, damageEvent.isCombatDamage(), damageEvent.isPreventable());
            return true;
        }
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getPlayerId().equals(source.getControllerId());
    }

    @Override
    public WeatheredBodyguardsEffect copy() {
        return new WeatheredBodyguardsEffect(this);
    }
}
