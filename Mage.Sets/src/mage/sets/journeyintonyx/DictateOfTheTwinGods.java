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
package mage.sets.journeyintonyx;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import static mage.game.events.GameEvent.EventType.DAMAGE_CREATURE;
import static mage.game.events.GameEvent.EventType.DAMAGE_PLAYER;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class DictateOfTheTwinGods extends CardImpl<DictateOfTheTwinGods> {

    public DictateOfTheTwinGods(UUID ownerId) {
        super(ownerId, 93, "Dictate of the Twin Gods", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}{R}");
        this.expansionSetCode = "JOU";

        this.color.setRed(true);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // If a source would deal damage to a permanent or player, it deals double that damage to that permanent or player instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DictateOfTheTwinGodsEffect()));
        
    }

    public DictateOfTheTwinGods(final DictateOfTheTwinGods card) {
        super(card);
    }

    @Override
    public DictateOfTheTwinGods copy() {
        return new DictateOfTheTwinGods(this);
    }
}

class DictateOfTheTwinGodsEffect extends ReplacementEffectImpl<DictateOfTheTwinGodsEffect> {

    public DictateOfTheTwinGodsEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Damage);
        staticText = "If a source would deal damage to a permanent or player, that source deals double that damage to that permanent or player instead";
    }

    public DictateOfTheTwinGodsEffect(final DictateOfTheTwinGodsEffect effect) {
        super(effect);
    }

    @Override
    public DictateOfTheTwinGodsEffect copy() {
        return new DictateOfTheTwinGodsEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        switch (event.getType()) {
            case DAMAGE_PLAYER:
            case DAMAGE_CREATURE:
            case DAMAGE_PLANESWALKER:
                return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        DamageEvent damageEvent = (DamageEvent)event;
        if (damageEvent.getType() == EventType.DAMAGE_PLAYER) {
            Player targetPlayer = game.getPlayer(event.getTargetId());
            if (targetPlayer != null) {
                targetPlayer.damage(damageEvent.getAmount()*2, damageEvent.getSourceId(), game, damageEvent.isPreventable(), damageEvent.isCombatDamage(), event.getAppliedEffects());
                return true;
            }
        } else {
            Permanent targetPermanent = game.getPermanent(event.getTargetId());
            if (targetPermanent != null) {
                targetPermanent.damage(damageEvent.getAmount()*2, damageEvent.getSourceId(), game, damageEvent.isPreventable(), damageEvent.isCombatDamage(), event.getAppliedEffects());
                return true;
            }
        }
        return false;
    }
}
