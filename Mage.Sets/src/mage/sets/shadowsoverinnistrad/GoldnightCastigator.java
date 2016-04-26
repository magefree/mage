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
package mage.sets.shadowsoverinnistrad;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

/**
 *
 * @author fireshoes
 */
public class GoldnightCastigator extends CardImpl {

    public GoldnightCastigator(UUID ownerId) {
        super(ownerId, 162, "Goldnight Castigator", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        this.expansionSetCode = "SOI";
        this.subtype.add("Angel");
        this.power = new MageInt(4);
        this.toughness = new MageInt(9);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // If a source would deal damage to you, it deals double that damage to you instead.
        // If a source would deal damage to Goldnight Castigator, it deals double that damage to Goldkight Castigator instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GoldnightCastigatorDoubleDamageEffect()));
    }

    public GoldnightCastigator(final GoldnightCastigator card) {
        super(card);
    }

    @Override
    public GoldnightCastigator copy() {
        return new GoldnightCastigator(this);
    }
}

class GoldnightCastigatorDoubleDamageEffect extends ReplacementEffectImpl {

    public GoldnightCastigatorDoubleDamageEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Damage);
        staticText = "If a source would deal damage to you, it deals double that damage to you instead."
            + "<BR>If a source would deal damage to Goldnight Castigator, it deals double that damage to {this} instead.";
    }

    public GoldnightCastigatorDoubleDamageEffect(final GoldnightCastigatorDoubleDamageEffect effect) {
        super(effect);
    }

    @Override
    public GoldnightCastigatorDoubleDamageEffect copy() {
        return new GoldnightCastigatorDoubleDamageEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType().equals(EventType.DAMAGE_CREATURE) ||
                event.getType().equals(EventType.DAMAGE_PLAYER);
    }


    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        switch (event.getType()) {
            case DAMAGE_PLAYER:
                if (event.getTargetId().equals(source.getControllerId())) {
                    event.setAmount(event.getAmount() * 2);
                }
                break;
            case DAMAGE_CREATURE:
                Permanent permanent = game.getPermanent(event.getTargetId());
                if (permanent != null) {
                    if (permanent.getId().equals(source.getSourceId())) {
                        event.setAmount(event.getAmount() * 2);
                    }
                }
        }
        return false;
    }
}
